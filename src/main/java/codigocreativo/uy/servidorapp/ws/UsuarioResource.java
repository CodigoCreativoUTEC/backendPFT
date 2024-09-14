package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;

import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"Falta el token de autorización\"}").build();
    }

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
        UsuarioDto user = this.er.login(loginRequest.getUsuario(), loginRequest.getPassword());

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
        if (googleLoginRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Pedido de login nulo\"}").build();
        }

        UsuarioDto user = this.er.findUserByEmail(googleLoginRequest.getEmail());
        boolean userNeedsAdditionalInfo = false;

        if (user == null) {
            user = new UsuarioDto();
            user.setEmail(googleLoginRequest.getEmail());
            user.setNombre(googleLoginRequest.getName());
            userNeedsAdditionalInfo = true;
        } else if (!user.getEstado().equals(Estados.ACTIVO)) {
            return Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Cuenta inactiva, por favor contacte al administrador\"}").build();
        }
        user = user.setContrasenia(null);
        String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
        GoogleLoginResponse loginResponse = new GoogleLoginResponse(token, userNeedsAdditionalInfo, user);
        return Response.ok(loginResponse).build();
    }


    public static class LoginRequest {
        private String usuario;
        private String password;

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
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
        private String email;
        private String name;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
