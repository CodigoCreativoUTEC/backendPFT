package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;
import com.google.auth.oauth2.IdToken;

import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private UsuarioRemote er;
    @EJB
    private JwtService jwtService;

    @POST
    @Path("/crear")
    public Response crearUsuario(UsuarioDto usuario) {
        this.er.crearUsuario(usuario);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarUsuario(UsuarioDto usuario) {
        try {
            this.er.modificarUsuario(usuario);
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    //TODO: Se debe crear un enpoint que verifique mi propio usuario y permita modificar sus propios datos

    @PUT
    @Path("/Inactivar")
    public Response inactivarUsuario(UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = jwtService.parseToken(token);
        String emailSolicitante = claims.getSubject();
        String perfilSolicitante = claims.get("perfil", String.class);

        if (!perfilSolicitante.equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"No tienes permisos para inactivar usuarios\"}").build();
        }

        UsuarioDto usuarioAInactivar = this.er.obtenerUsuarioPorCI(usuario.getCedula());

        if (usuarioAInactivar == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuario no encontrado\"}").build();
        }

        if (usuarioAInactivar.getEmail().equals(emailSolicitante)) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"No puedes inactivar tu propia cuenta\"}").build();
        }

        if (usuarioAInactivar.getIdPerfil().getNombrePerfil().equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"No puedes inactivar a otro administrador\"}").build();
        }

        this.er.eliminarUsuario(usuarioAInactivar);
        return Response.status(200).entity("{\"message\":\"Usuario inactivado correctamente\"}").build();
    }


    @GET
    @Path("/BuscarUsuarioPorCI")
    public UsuarioDto buscarEquipo(@QueryParam("ci") String ci){
        return this.er.obtenerUsuarioPorCI(ci);
    }

    @GET
    @Path("/BuscarUsuarioPorId")
    public UsuarioDto buscarEquipo(@QueryParam("id") Long id){
        return this.er.obtenerUsuario(id);
    }

    @GET
    @Path("/ObtenerUsuarioPorEstado")
    public List<UsuarioDto> obtenerUsuarioPorEstado(@QueryParam("estado") Estados estado){
        return this.er.obtenerUsuariosPorEstado(estado);
    }

    @GET
    @Path("/ListarTodosLosUsuarios")
    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        return this.er.obtenerUsuarios();
    }

    @GET
    @Path("/obtenerUserEmail")
    public Response getUserByEmail(@QueryParam("email") String email) {
        UsuarioDto user = er.findUserByEmail(email);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            System.out.println("Request null");
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Pedido de login nulo\"}").build();
        }
        System.out.println(loginRequest.toString());
        System.out.println("Login request: " + loginRequest.email + " " + loginRequest.password);
        System.out.println("Login request: " + loginRequest.email + " " + loginRequest.getPassword());
        UsuarioDto user = this.er.login(loginRequest.email, loginRequest.getPassword());

        if (user != null) {
            String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
            System.out.println("Token generado: " + token);
            user = user.setContrasenia(null);
            LoginResponse loginResponse = new LoginResponse(token, user);
            System.out.println("Ingreso correcto");
            System.out.println(Response.ok(loginResponse).build());
            return Response.ok(loginResponse).build();
        } else {
            System.out.println("login unautorized invalid credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Datos de acceso incorrectos\"}").build();
        }
    }

    @POST
    @Path("/google-login")
    public Response googleLogin(GoogleLoginRequest googleLoginRequest) {
        System.out.println(googleLoginRequest.toString());
        if (googleLoginRequest.getIdToken() == null) {
            System.out.println("Token de Google nulo");
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Token de Google nulo\"}").build();
        }
        try {
            // Validar el idToken con Google
            String idTokenString = googleLoginRequest.getIdToken();
            TokenVerifier tokenVerifier = TokenVerifier.newBuilder()
                    .setAudience("103181333646-gp6uip6g6k1rg6p52tsidphj3gt22qut.apps.googleusercontent.com")
                    .build();

            JsonWebSignature idToken = tokenVerifier.verify(idTokenString);
            if (idToken == null) {
                System.out.println("Token de Google inválido");
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
            }

            JsonWebToken.Payload payload = idToken.getPayload();
            String email = (String) payload.get("email");
            String name = (String) payload.get("name");

            // Verificar si el usuario ya existe en el sistema
            UsuarioDto user = er.findUserByEmail(email);
            boolean userNeedsAdditionalInfo = false;

            if (user == null) {
                // El usuario no existe, pedir más información para completar el registro
                user = new UsuarioDto();
                user.setEmail(email);
                user.setNombre(name);
                userNeedsAdditionalInfo = true;
            } else if (!user.getEstado().equals(Estados.ACTIVO)) {
                // Si el usuario no está activo, devolver un error
                System.out.println("Cuenta inactiva, por favor contacte al administrador");
                return Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Cuenta inactiva, por favor contacte al administrador\"}").build();
            }

            // Generar un JWT para este usuario si ya existe
            String perfilNombre = (user.getIdPerfil() != null) ? user.getIdPerfil().getNombrePerfil() : "Usuario";
            String token = jwtService.generateToken(user.getEmail(), perfilNombre);  // Incluir ID

            // Enviar la respuesta al cliente con el token y la indicación de si necesita completar más información
            GoogleLoginResponse loginResponse = new GoogleLoginResponse(token, userNeedsAdditionalInfo, user);
            return Response.ok(loginResponse).build();

        } catch (VerificationException e) {
            System.out.println("Token de Google inválido");
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
        } catch (Exception e) {
            System.out.println("Error al procesar el token de Google");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al procesar el token de Google\"}").build();
        }
    }

    @POST
    @Path("/renovar-token")
    public Response renovarToken(@HeaderParam("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = jwtService.parseToken(token);

        // Obtener el email y perfil del token antiguo
        String email = claims.get("email", String.class);
        String perfil = claims.get("perfil", String.class);

        // Generar un nuevo token
        String nuevoToken = jwtService.generateToken(email, perfil);

        return Response.ok("{\"token\": \"" + nuevoToken + "\"}").build();
    }

    public static class LoginRequest {
    @JsonbProperty("email")
    private String email;

    @JsonbProperty("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


    public static class LoginResponse {
        private String token;
        private UsuarioDto user;

        public LoginResponse(String token, UsuarioDto user) {
            this.token = token;
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UsuarioDto getUser() {
            return user;
        }

        public void setUser(UsuarioDto user) {
            this.user = user;
        }
    }

    public static class GoogleLoginRequest {
        private String idToken;

        // Getters y setters
        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }


    public static class GoogleLoginResponse {
        private String token;
        private boolean userNeedsAdditionalInfo;
        private UsuarioDto user; // Añade este campo

        // Constructor
        public GoogleLoginResponse(String token, boolean userNeedsAdditionalInfo, UsuarioDto user) {
            this.token = token;
            this.userNeedsAdditionalInfo = userNeedsAdditionalInfo;
            this.user = user;
        }

        // Getters y setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isUserNeedsAdditionalInfo() {
            return userNeedsAdditionalInfo;
        }

        public void setUserNeedsAdditionalInfo(boolean userNeedsAdditionalInfo) {
            this.userNeedsAdditionalInfo = userNeedsAdditionalInfo;
        }

        public UsuarioDto getUser() {
            return user;
        }

        public void setUser(UsuarioDto user) {
            this.user = user;
        }
    }
}
