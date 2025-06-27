package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.PasswordUtils;
import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private UsuarioRemote er;
    @EJB
    private JwtService jwtService;

    private static final String EMAIL = "email";
    private static final String BEARER = "bearer";

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario en el sistema", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al crear el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearUsuario(@Parameter(description = "Datos del usuario a crear", required = true) UsuarioDto usuario) {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Usuario nulo\"}")
                    .build();
        }
        try {
            String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
            usuario.setContrasenia(saltedHash);
            InstitucionDto institucion = new InstitucionDto();
            institucion.setId(1L); // Asignar una institución por defecto
            usuario.setIdInstitucion(institucion);
            this.er.crearUsuario(usuario);
            return Response.status(201).entity("{\"message\":\"Usuario creado correctamente\"}").build();
        } catch (ServiciosException e) {
            // Capturar específicamente las excepciones de validación y devolver el mensaje específico
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            // Para otros errores, devolver un mensaje genérico
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error al crear el usuario: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un usuario", description = "Modifica los datos de un usuario existente. Solo para administradores.", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado - Solo administradores pueden modificar usuarios", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al modificar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response modificarUsuario(
            @Parameter(description = "Datos del usuario a modificar", required = true) UsuarioDto usuario,
            @Parameter(description = "Token Bearer de autorización", required = true)
            @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Verificar que el token pertenece a un administrador
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String emailSolicitante = claims.getSubject();
            String perfilSolicitante = claims.get("perfil", String.class);

            if (!perfilSolicitante.equals("Administrador")) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"Solo los administradores pueden modificar usuarios\"}")
                        .build();
            }

            // Obtener el usuario actual de la base de datos
            UsuarioDto usuarioActual = er.obtenerUsuario(usuario.getId());
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }

            // Verificar que no se está intentando modificar a sí mismo
            if (usuarioActual.getEmail().equals(emailSolicitante)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"No puedes modificar tu propio usuario desde este endpoint. Usa el endpoint de modificación propia.\"}")
                        .build();
            }

            // Mantener la contraseña actual si no se proporciona una nueva
            if (usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty()) {
                usuario.setContrasenia(usuarioActual.getContrasenia());
            } else {
                // Validar el formato de la nueva contraseña
                if (!usuario.getContrasenia().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\":\"La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.\"}")
                            .build();
                }
                // Generar el hash de la nueva contraseña
                String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
                usuario.setContrasenia(saltedHash);
            }

            // Asegurar que los campos críticos estén presentes
            if (usuario.getIdPerfil() == null) {
                usuario.setIdPerfil(usuarioActual.getIdPerfil());
            }
            if (usuario.getIdInstitucion() == null) {
                usuario.setIdInstitucion(usuarioActual.getIdInstitucion());
            }
            if (usuario.getEstado() == null) {
                usuario.setEstado(usuarioActual.getEstado());
            }

            this.er.modificarUsuario(usuario);
            return Response.status(200)
                    .entity("{\"message\":\"Usuario modificado correctamente\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al modificar el usuario: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/modificar-propio-usuario")
    @Operation(summary = "Modificar el propio usuario", description = "Permite a un usuario modificar sus propios datos", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado para modificar este usuario", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al modificar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response modificarPropioUsuario(@Parameter(description = "Datos del usuario a modificar", required = true) UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String correoDelToken = claims.getSubject();

            if (!Objects.equals(correoDelToken, usuario.getEmail())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"No autorizado para modificar este usuario\"}")
                        .build();
            }

            UsuarioDto usuarioActual = er.obtenerUsuario(usuario.getId());
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }

            // Solo procesar la contraseña si se proporciona una nueva
            if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {
                if (!usuario.getContrasenia().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity("{\"error\":\"La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.\"}")
                            .build();
                }
                String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
                usuario.setContrasenia(saltedHash);
            } else {
                usuario.setContrasenia(usuarioActual.getContrasenia());
            }

            // Mantener campos que no deberían modificarse
            usuario.setNombreUsuario(usuarioActual.getNombreUsuario());
            usuario.setIdPerfil(usuarioActual.getIdPerfil());
            usuario.setEstado(usuarioActual.getEstado());
            usuario.setIdInstitucion(usuarioActual.getIdInstitucion());

            er.modificarUsuario(usuario);
            return Response.status(200)
                    .entity("{\"message\":\"Usuario modificado correctamente\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al modificar el usuario: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/inactivar")
    @Operation(
            summary = "Inactivar un usuario",
            description = "Permite inactivar un usuario en el sistema. Requiere permisos de Administrador.",
            tags = { "Usuarios" }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario inactivado correctamente",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Requiere ser Administrador para inactivar usuarios o no puedes inactivar a otro administrador",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    
    public Response inactivarUsuario(
            @Parameter(description = "Datos del usuario a inactivar", required = true)
            UsuarioDto usuario,
            @Parameter(description = "Token Bearer de autorización", required = true)
            @HeaderParam("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.substring(BEARER.length()).trim();
        Claims claims = jwtService.parseToken(token);
        String emailSolicitante = claims.getSubject();
        String perfilSolicitante = claims.get("perfil", String.class);

        if (!perfilSolicitante.equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\":\"Requiere ser Administrador para inactivar usuarios\"}").build();
        }

        UsuarioDto usuarioAInactivar = this.er.obtenerUsuarioPorCI(usuario.getCedula());
        if (usuarioAInactivar == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Usuario no encontrado\"}").build();
        }

        if (usuarioAInactivar.getEmail().equals(emailSolicitante)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\":\"No puedes inactivar tu propia cuenta\"}").build();
        }

        if (usuarioAInactivar.getIdPerfil().getNombrePerfil().equals("Administrador")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\":\"No puedes inactivar a otro administrador\"}").build();
        }

        this.er.eliminarUsuario(usuarioAInactivar);
        return Response.status(200).entity("{\"message\":\"Usuario inactivado correctamente\"}").build();
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar usuarios", description = "Filtra los usuarios según los criterios proporcionados", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios filtrada correctamente", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "500", description = "Error al filtrar los usuarios", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public List<UsuarioDto> filtrarUsuarios(@Parameter(description = "Nombre del usuario") @QueryParam("nombre") String nombre,
                                            @Parameter(description = "Apellido del usuario") @QueryParam("apellido") String apellido,
                                            @Parameter(description = "Nombre de usuario") @QueryParam("nombreUsuario") String nombreUsuario,
                                            @Parameter(description = "Email del usuario") @QueryParam("email") String email,
                                            @Parameter(description = "Tipo de perfil del usuario") @QueryParam("perfil") String tipoUsuario,
                                            @Parameter(description = "Estado del usuario") @QueryParam("estado") String estado) {

        Map<String, String> filtros = new HashMap<>();
        if (nombre != null) filtros.put("nombre", nombre);
        if (apellido != null) filtros.put("apellido", apellido);
        if (nombreUsuario != null) filtros.put("nombreUsuario", nombreUsuario);
        if (email != null) filtros.put(EMAIL, email);

        if (estado == null || estado.isEmpty()) {
            filtros.put("estado", "ACTIVO");
        } else if (!estado.equals("default")) {
            filtros.put("estado", estado);
        }
        if (tipoUsuario != null && !tipoUsuario.isEmpty() && !tipoUsuario.equals("default")) {
            filtros.put("tipoUsuario", tipoUsuario);
        }

        List<UsuarioDto> usuarios = this.er.obtenerUsuariosFiltrado(filtros);

        // Crear una nueva lista de usuarios sin la contraseña
        List<UsuarioDto> usuariosSinContrasenia = new ArrayList<>();
        for (UsuarioDto usuario : usuarios) {
            UsuarioDto usuarioSinContrasenia = new UsuarioDto();
            usuarioSinContrasenia.setId(usuario.getId());
            usuarioSinContrasenia.setUsuariosTelefonos(usuario.getUsuariosTelefonos());
            usuarioSinContrasenia.setNombre(usuario.getNombre());
            usuarioSinContrasenia.setCedula(usuario.getCedula());
            usuarioSinContrasenia.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioSinContrasenia.setApellido(usuario.getApellido());
            usuarioSinContrasenia.setNombreUsuario(usuario.getNombreUsuario());
            usuarioSinContrasenia.setEmail(usuario.getEmail());
            usuarioSinContrasenia.setEstado(usuario.getEstado());
            usuarioSinContrasenia.setIdPerfil(usuario.getIdPerfil());
            usuarioSinContrasenia.setIdInstitucion(usuario.getIdInstitucion());

            // No seteamos la contraseña para que no se incluya en la respuesta
            usuariosSinContrasenia.add(usuarioSinContrasenia);
        }

        return usuariosSinContrasenia;
    }


    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un usuario por ID", description = "Obtiene la información de un usuario específico por su ID", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public UsuarioDto buscarUsuario(@Parameter(description = "ID del usuario a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerUsuario(id);
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", content = @Content(schema = @Schema(implementation = UsuarioDto.class)))
    })
    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        List<UsuarioDto> usuarios = this.er.obtenerUsuarios();
        for (UsuarioDto usuario : usuarios) {
            usuario.setContrasenia(null); // Eliminar la contraseña de la respuesta
        }
        return usuarios;
    }

    @GET
    @Path("/obtenerUserEmail")
    @Operation(summary = "Buscar usuario por email", description = "Obtiene un usuario por su email", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response getUserByEmail(@Parameter(description = "Email del usuario a buscar", required = true) @QueryParam("email") String email) {
        UsuarioDto user = er.findUserByEmail(email);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión en el sistema, debes extraer el jwt para usarlo en swagger", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o cuenta inactiva", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error durante el login", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response login(@Parameter(description = "Datos de inicio de sesión", required = true) LoginRequest loginRequest) {
        if (loginRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Pedido de login nulo\"}").build();
        }

        UsuarioDto user = this.er.findUserByEmail(loginRequest.getEmail());

        if (user != null && user.getEstado().equals(Estados.ACTIVO)) {
            try {
                boolean isValid = PasswordUtils.verifyPassword(loginRequest.getPassword(), user.getContrasenia());

                if (!isValid) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Contraseña incorrecta\"}").build();
                }

                String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
                user.setContrasenia(null);// No enviar la contraseña en la respuesta
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
    @Operation(summary = "Iniciar sesión con Google", description = "Permite a un usuario iniciar sesión con Google", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión con Google exitoso", content = @Content(schema = @Schema(implementation = GoogleLoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Token de Google nulo", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Token de Google inválido", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al procesar el token de Google", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response googleLogin(@Parameter(description = "Token de Google para iniciar sesión", required = true) GoogleLoginRequest googleLoginRequest) {
        if (googleLoginRequest.getIdToken() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Token de Google nulo\"}").build();
        }
        try {
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

            UsuarioDto user = er.findUserByEmail(email);
            boolean userNeedsAdditionalInfo = false;

            if (user == null) {
                user = new UsuarioDto();
                user.setEmail(email);
                user.setNombre(name);
                userNeedsAdditionalInfo = true;
            } else if (!user.getEstado().equals(Estados.ACTIVO)) {
                return Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"Cuenta inactiva, por favor contacte al administrador\"}").build();
            }

            String perfilNombre = (user.getIdPerfil() != null) ? user.getIdPerfil().getNombrePerfil() : "Usuario";
            String token = jwtService.generateToken(email, perfilNombre);

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
    @Operation(summary = "Renovar token JWT", description = "Renueva el token JWT antes de su expiración", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token renovado exitosamente", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Falta el token de autorización", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "El token ha expirado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al procesar el token", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response renovarToken(@HeaderParam("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Falta el token de autorización.\"}")
                    .build();
        }

        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);

            if (claims.getExpiration().before(new Date())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"El token ha expirado.\"}")
                        .build();
            }

            String email = claims.get(EMAIL, String.class);
            String perfil = claims.get("perfil", String.class);

            String nuevoToken = jwtService.generateToken(email, perfil);

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
