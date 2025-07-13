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
    private static final String PERFIL = "perfil";
    private static final String ADMINISTRADOR = "Administrador";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";

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
            // Debug: mostrar la contraseña antes de validar
            System.out.println("DEBUG - Contraseña recibida: " + usuario.getContrasenia());
            
            // Validar la contraseña antes de hashearla
            er.validarContrasenia(usuario.getContrasenia());
            
            String saltedHash = PasswordUtils.generateSaltedHash(usuario.getContrasenia());
            usuario.setContrasenia(saltedHash);
            InstitucionDto institucion = new InstitucionDto();
            institucion.setId(1L); // Asignar una institución por defecto
            usuario.setIdInstitucion(institucion);
            this.er.crearUsuario(usuario);
            return Response.status(201).entity("{\"message\":\"Usuario creado correctamente\"}").build();
        } catch (ServiciosException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
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
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al modificar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response modificarUsuario(
            @Parameter(description = "Datos del usuario a modificar", required = true) UsuarioDto usuario,
            @Parameter(description = "Token Bearer de autorización", required = true)
            @HeaderParam("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String emailSolicitante = claims.getSubject();
            
            // Validar permisos de administrador
            er.validarModificacionPorAdministrador(emailSolicitante, usuario.getId());
            
            // Obtener el usuario actual
            UsuarioDto usuarioActual = er.obtenerUsuario(usuario.getId());
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }
            
            // Validar contraseña si se proporciona una nueva
            if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {
                er.validarContrasenia(usuario.getContrasenia());
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
        } catch (ServiciosException e) {
            if (e.getMessage().contains("No autorizado") || e.getMessage().contains("no tiene permisos") || e.getMessage().contains("propio usuario") || e.getMessage().contains("Solo los administradores pueden modificar usuarios") || e.getMessage().contains("No puedes modificar tu propio usuario desde este endpoint. Usa el endpoint de modificación propia.")) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            } else if (e.getMessage().contains("no encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            }
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
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Error al modificar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response modificarPropioUsuario(@Parameter(description = "Datos del usuario a modificar", required = true) UsuarioDto usuario, @HeaderParam("Authorization") String authorizationHeader) {
        try {
            // Validar que el header de autorización no sea nulo
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Token de autorización requerido\"}")
                        .build();
            }

            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String correoDelToken = claims.getSubject();

            // Obtener el usuario actual por email del token
            UsuarioDto usuarioActual = er.findUserByEmail(correoDelToken);
            if (usuarioActual == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }

            // Establecer el ID del usuario actual en el DTO recibido
            usuario.setId(usuarioActual.getId());

            // Solo procesar la contraseña si se proporciona una nueva
            if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {
                er.validarContrasenia(usuario.getContrasenia());
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
        } catch (ServiciosException e) {
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("no autorizado") || msg.contains("no puede modificar") || msg.contains("autorizado")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"No autorizado para modificar este usuario\"}")
                        .build();
            } else if (msg.contains("no encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            }
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
            description = "Permite inactivar un usuario en el sistema. Solo Administradores y Aux administrativos pueden inactivar usuarios de otros roles.",
            tags = { "Usuarios" }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario inactivado correctamente",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error al inactivar el usuario",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No autorizado",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @SecurityRequirement(name = "BearerAuth")
    public Response inactivarUsuario(
            @Parameter(description = "ID del usuario a inactivar", required = true)
            @QueryParam("id") Long idUsuarioAInactivar,
            @Parameter(description = "Token Bearer de autorización", required = true)
            @HeaderParam("Authorization") String authorizationHeader
    ) {
        try {
            // Validar que el ID del usuario no sea null
            if (idUsuarioAInactivar == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"El ID del usuario es requerido\"}")
                        .build();
            }

            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String emailSolicitante = claims.getSubject();
            String perfilSolicitante = claims.get(PERFIL, String.class);

            // Verificar que el usuario solicitante tiene permisos (Administrador o Aux administrativo)
            if (!ADMINISTRADOR.equals(perfilSolicitante) && !"Aux administrativo".equals(perfilSolicitante)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"Requiere ser Administrador o Aux administrativo para inactivar usuarios\"}")
                        .build();
            }

            // Obtener el usuario solicitante
            UsuarioDto usuarioSolicitante = er.findUserByEmail(emailSolicitante);
            if (usuarioSolicitante == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario solicitante no encontrado\"}")
                        .build();
            }

            // Obtener el usuario a inactivar por ID
            UsuarioDto usuarioAInactivar = er.obtenerUsuario(idUsuarioAInactivar);
            if (usuarioAInactivar == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario a inactivar no encontrado\"}")
                        .build();
            }

            // Verificar que no se está inactivando a sí mismo
            if (usuarioSolicitante.getId().equals(idUsuarioAInactivar)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"No puedes inactivar tu propia cuenta\"}")
                        .build();
            }

            String perfilUsuarioAInactivar = usuarioAInactivar.getIdPerfil().getNombrePerfil();

            // Verificar que no se está inactivando a otro administrador o aux administrativo
            if (ADMINISTRADOR.equals(perfilUsuarioAInactivar) || "Aux administrativo".equals(perfilUsuarioAInactivar)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"No puedes inactivar a otro Administrador o Aux administrativo\"}")
                        .build();
            }

            // Inactivar el usuario
            er.inactivarUsuario(emailSolicitante, usuarioAInactivar.getCedula());
            return Response.status(200).entity("{\"message\":\"Usuario inactivado correctamente\"}").build();
        } catch (ServiciosException e) {
            if (e.getMessage().contains("No autorizado") || e.getMessage().contains("no tiene permisos")) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            } else if (e.getMessage().contains("no encontrado")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"" + e.getMessage() + "\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al inactivar el usuario: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar usuarios", description = "Filtra los usuarios según los criterios proporcionados. Por defecto muestra todos los usuarios.", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios filtrada correctamente", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "500", description = "Error al filtrar los usuarios", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response filtrarUsuarios(@Parameter(description = "Nombre del usuario") @QueryParam("nombre") String nombre,
                                            @Parameter(description = "Apellido del usuario") @QueryParam("apellido") String apellido,
                                            @Parameter(description = "Nombre de usuario") @QueryParam("nombreUsuario") String nombreUsuario,
                                            @Parameter(description = "Email del usuario") @QueryParam("email") String email,
                                            @Parameter(description = "Tipo de perfil del usuario") @QueryParam("perfil") String tipoUsuario,
                                            @Parameter(description = "Estado del usuario") @QueryParam("estado") String estado) {

        try {
            Map<String, String> filtros = new HashMap<>();
            if (nombre != null && !nombre.trim().isEmpty()) filtros.put("nombre", nombre);
            if (apellido != null && !apellido.trim().isEmpty()) filtros.put("apellido", apellido);
            if (nombreUsuario != null && !nombreUsuario.trim().isEmpty()) filtros.put("nombreUsuario", nombreUsuario);
            if (email != null && !email.trim().isEmpty()) filtros.put(EMAIL, email);

            // Solo agregar filtro de estado si se especifica explícitamente
            if (estado != null && !estado.trim().isEmpty() && !estado.equals("default")) {
                filtros.put("estado", estado);
            }
            
            if (tipoUsuario != null && !tipoUsuario.trim().isEmpty() && !tipoUsuario.equals("default")) {
                filtros.put("tipoUsuario", tipoUsuario);
            }

            List<UsuarioDto> usuarios = er.obtenerUsuariosFiltrado(filtros);
            
            // Eliminar contraseñas de todos los usuarios antes de enviar la respuesta
            for (UsuarioDto usuario : usuarios) {
                usuario.setContrasenia(null);
            }
            
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al filtrar usuarios: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un usuario por ID", description = "Obtiene la información de un usuario específico por su ID", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "400", description = "Error al buscar el usuario", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response buscarUsuario(@Parameter(description = "ID del usuario a buscar", required = true) @QueryParam("id") Long id) {
        try {
            UsuarioDto usuario = this.er.obtenerUsuario(id);
            if (usuario != null) {
                usuario.setContrasenia(null); // No enviar contraseña en la respuesta
                return Response.ok(usuario).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Error al buscar el usuario: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", content = @Content(schema = @Schema(implementation = UsuarioDto.class)))
    })
    public Response obtenerTodosLosUsuarios() {
        try {
            List<UsuarioDto> usuarios = er.obtenerUsuariosSinContrasenia();
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al obtener usuarios: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/obtenerUserEmail")
    @Operation(summary = "Buscar usuario por email", description = "Obtiene un usuario por su email", tags = { "Usuarios" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = UsuarioDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response getUserByEmail(@Parameter(description = "Email del usuario a buscar", required = true) @QueryParam("email") String email) {
        try {
            UsuarioDto user = er.findUserByEmail(email);
            if (user != null) {
                user.setContrasenia(null); // No enviar contraseña en la respuesta
                return Response.ok(user).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Usuario no encontrado\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error al buscar el usuario: " + e.getMessage() + "\"}")
                    .build();
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
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Solicitud nula\"}").build();
        }

        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Email es obligatorio\"}").build();
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Contraseña es obligatoria\"}").build();
        }

        try {
            UsuarioDto user = er.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Credenciales incorrectas\"}").build();
            }

            if (!user.getEstado().equals(Estados.ACTIVO)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Credenciales incorrectas o cuenta inactiva\"}").build();
            }

            user.setContrasenia(null);
            String token = jwtService.generateToken(user.getEmail(), user.getIdPerfil().getNombrePerfil());
            LoginResponse response = new LoginResponse(token, user);
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error durante el login: " + e.getMessage() + "\"}").build();
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
        if (googleLoginRequest == null || googleLoginRequest.getIdToken() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Token de Google nulo\"}").build();
        }

        try {
            // Verificar el token de Google
            TokenVerifier verifier = TokenVerifier.newBuilder()
                    .setAudience("103181333646-gp6uip6g6k1rg6p52tsidphj3gt22qut.apps.googleusercontent.com")
                    .build();

            JsonWebToken token = verifier.verify(googleLoginRequest.getIdToken());
            String email = token.getPayload().get("email").toString();

            // Buscar o crear usuario
            UsuarioDto user = er.findUserByEmail(email);
            boolean userNeedsAdditionalInfo = false;

            if (user == null) {
                // Usuario nuevo, necesitará información adicional
                userNeedsAdditionalInfo = true;
                user = new UsuarioDto();
                user.setEmail(email);
                user.setEstado(Estados.SIN_VALIDAR);
            }

            String jwtToken = jwtService.generateToken(user.getEmail(), user.getIdPerfil() != null ? user.getIdPerfil().getNombrePerfil() : "Usuario");
            GoogleLoginResponse response = new GoogleLoginResponse(jwtToken, userNeedsAdditionalInfo, user);
            return Response.ok(response).build();
        } catch (VerificationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Token de Google inválido\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al procesar el token de Google: " + e.getMessage() + "\"}").build();
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
        if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith("bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Falta el token de autorización.\"}").build();
        }

        try {
            String token = authorizationHeader.substring(BEARER.length()).trim();
            Claims claims = jwtService.parseToken(token);
            String email = claims.getSubject();
            String perfil = claims.get(PERFIL, String.class);

            // Generar nuevo token
            String newToken = jwtService.generateToken(email, perfil);
            return Response.ok("{\"token\":\"" + newToken + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"El token ha expirado o es inválido\"}").build();
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
        private UsuarioDto user;

        public GoogleLoginResponse(String token, boolean userNeedsAdditionalInfo, UsuarioDto user) {
            this.token = token;
            this.userNeedsAdditionalInfo = userNeedsAdditionalInfo;
            this.user = user;
        }

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
