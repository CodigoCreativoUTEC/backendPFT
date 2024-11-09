package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.PasswordUtils;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;

import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private UsuarioRemote er;
    @EJB
    private JwtService jwtService;

    //defino variables static para repetidas
    private static final String EMAIL = "email";
    private static final String BEARER = "bearer";

    @POST
    @Path("/crear")
    public Response crearUsuario(UsuarioDto usuario) {
        try {
            // Generar el hash con el salt incluido
            String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());

            // Asignar el valor hash (con salt) al usuario
            usuario.setContrasenia(saltedHash);

            // Crear el usuario en el sistema
            this.er.crearUsuario(usuario);

            return Response.status(201).entity("{\"message\":\"Usuario creado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Error al crear el usuario\"}").build();
        }
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


    @PUT
    @Path("/modificar-propio-usuario")
    public Response modificarPropioUsuario(UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Extraer el token JWT del encabezado
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String correoDelToken = claims.getSubject();

            // Verificar si el correo del token coincide con el correo del objeto UsuarioDto
            if (!Objects.equals(correoDelToken, usuario.getEmail())) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"No autorizado para modificar este usuario\"}").build();
            }

            // Obtener el usuario actual desde la base de datos
            UsuarioDto usuarioActual = er.obtenerUsuario(usuario.getId());
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"Usuario no encontrado\"}").build();
            }


            // Verificar si se proporciona una nueva contraseña
            if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {

                // Validar la nueva contraseña
                if (!usuario.getContrasenia().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {

                    return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.\"}").build();
                }

                // Generar un nuevo salt y hash para la nueva contraseña
                String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
                usuario.setContrasenia(saltedHash);

            } else {
                // Mantener la contraseña actual si no se cambia
                usuario.setContrasenia(usuarioActual.getContrasenia());
            }

            // Proteger otros campos que no se pueden modificar
            usuario.setNombreUsuario(usuarioActual.getNombreUsuario());
            usuario.setIdPerfil(usuarioActual.getIdPerfil());
            usuario.setEstado(usuarioActual.getEstado());
            usuario.setIdInstitucion(usuarioActual.getIdInstitucion());

            // Proceder con la modificación
            er.modificarUsuario(usuario);


            return Response.status(200).entity("{\"message\":\"Usuario modificado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }





    @PUT
    @Path("/inactivar")
    public Response inactivarUsuario(UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(BEARER.length()).trim();
        Claims claims = jwtService.parseToken(token);
        String emailSolicitante = claims.getSubject();
        String perfilSolicitante = claims.get("perfil", String.class);

        if (!perfilSolicitante.equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"message\":\"Requiere ser Administrador para inactivar usuarios\"}").build();
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
    @Path("/filtrar")
    public List<UsuarioDto> filtrarUsuarios(@QueryParam("nombre") String nombre,
                                            @QueryParam("apellido") String apellido,
                                            @QueryParam("nombreUsuario") String nombreUsuario,
                                            @QueryParam("email") String email,
                                            @QueryParam("perfil") String tipoUsuario,
                                            @QueryParam("estado") String estado) {

        Map<String, String> filtros = new HashMap<>();
        if (nombre != null) filtros.put("nombre", nombre);
        if (apellido != null) filtros.put("apellido", apellido);
        if (nombreUsuario != null) filtros.put("nombreUsuario", nombreUsuario);
        if (email != null) filtros.put(EMAIL, email);

        // Si no se envía el filtro de estado, por defecto se buscan usuarios activos
        if (estado == null || estado.isEmpty()) {
            filtros.put("estado", "ACTIVO");
        } else if (!estado.equals("default")) {
            // Si el estado es "default", no agregamos ningún filtro de estado
            filtros.put("estado", estado);
        }
        // Lógica para el filtro de perfil (tipo de usuario)
        if (tipoUsuario != null && !tipoUsuario.isEmpty() && !tipoUsuario.equals("default")) {
            filtros.put("tipoUsuario", tipoUsuario);
        }

        return this.er.obtenerUsuariosFiltrado(filtros);
    }



    @GET
    @Path("/BuscarUsuarioPorCI")
    public UsuarioDto buscarUsuario(@QueryParam("ci") String ci){
        return this.er.obtenerUsuarioPorCI(ci);
    }

    @GET
    @Path("/seleccionar")
    public UsuarioDto buscarUsuario(@QueryParam("id") Long id){
        return this.er.obtenerUsuario(id);
    }

    @GET
    @Path("/ObtenerUsuarioPorEstado")
    public List<UsuarioDto> obtenerUsuarioPorEstado(@QueryParam("estado") Estados estado){
        return this.er.obtenerUsuariosPorEstado(estado);
    }

    @GET
    @Path("/listar")
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
    // In UsuarioResource.java
    public Response login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Pedido de login nulo\"}").build();
        }

        UsuarioDto user = this.er.findUserByEmail(loginRequest.getEmail());

        if (user != null && user.getEstado().equals(Estados.ACTIVO)) {
            try {
                // Verificar la contraseña usando el hash con el salt incluido
                boolean isValid = PasswordUtils.verifyPassword(loginRequest.getPassword(), user.getContrasenia());

                if (!isValid) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Contraseña incorrecta\"}").build();
                }

                // Generar el token
                String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
                LoginResponse loginResponse = new LoginResponse(token, user);

                return Response.ok(loginResponse).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error durante el login\"}").build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Credenciales incorrectas o cuenta inactiva\"}").build();
        }
    }



    @POST
    @Path("/google-login")
    public Response googleLogin(GoogleLoginRequest googleLoginRequest) {
        if (googleLoginRequest.getIdToken() == null) {
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
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
            }

            JsonWebToken.Payload payload = idToken.getPayload();
            String email = (String) payload.get(EMAIL);
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
                return Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Cuenta inactiva, por favor contacte al administrador\"}").build();
            }

            // Generar un JWT para este usuario si ya existe
            String perfilNombre = (user.getIdPerfil() != null) ? user.getIdPerfil().getNombrePerfil() : "Usuario";
            String token = jwtService.generateToken(email, perfilNombre);  // Generar un nuevo JWT válido para este usuario

            // Enviar la respuesta al cliente con el token y la indicación de si necesita completar más información
            GoogleLoginResponse loginResponse = new GoogleLoginResponse(token, userNeedsAdditionalInfo, user);
            return Response.ok(loginResponse).build();

        } catch (VerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al procesar el token de Google\"}").build();
        }
    }


    @POST
    @Path("/renovar-token")
    public Response renovarToken(@HeaderParam("Authorization") String authorizationHeader) {
        // Validar si el header contiene el token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Falta el token de autorización.\"}")
                    .build();
        }

        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);

            // Verificar si el token ha expirado
            if (claims.getExpiration().before(new Date())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"El token ha expirado.\"}")
                        .build();
            }

            // Obtener el email y perfil del token antiguo
            String email = claims.get(EMAIL, String.class);
            String perfil = claims.get("perfil", String.class);

            // Generar un nuevo token
            String nuevoToken = jwtService.generateToken(email, perfil);

            // Devolver el nuevo token en formato JSON
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", nuevoToken);

            return Response.ok(responseMap).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al procesar el token.\"}")
                    .build();
        }
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
