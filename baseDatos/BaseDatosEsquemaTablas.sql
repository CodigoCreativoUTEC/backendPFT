create table TIPOS_INTERVENCIONES
(
    ID_TIPO     NUMBER       default "PFT"."ISEQ$$_71732".nextval generated as identity
		constraint PK_TIPOSINTERVENCIONES
			primary key,
    NOMBRE_TIPO VARCHAR2(30)                  not null
        constraint UK_NOMBRETIPOINTERVENCIONES
            unique,
    ESTADO      VARCHAR2(20) default 'ACTIVO' not null
)
/

create table TIPOS_EQUIPOS
(
    ID_TIPO     NUMBER       default "PFT"."ISEQ$$_71736".nextval generated as identity
		constraint PK_TIPO
			primary key,
    NOMBRE_TIPO VARCHAR2(30)                  not null
        constraint UK_NOMBRETIPOSEQUIPOS
            unique,
    ESTADO      VARCHAR2(20) default 'ACTIVO' not null
)
/

create table PAISES
(
    ID_PAIS NUMBER generated as identity
        constraint PK_PAISES
            primary key,
    NOMBRE  VARCHAR2(50) not null
)
/

create table PROVEEDORES_EQUIPOS
(
    ID_PROVEEDOR NUMBER       default "PFT"."ISEQ$$_71745".nextval generated as identity
		constraint PK_PROVEEDORES
			primary key,
    NOMBRE       VARCHAR2(30)                  not null,
    ESTADO       VARCHAR2(20) default 'ACTIVO' not null,
    ID_PAIS      NUMBER
        constraint FK_PROVEEDORES_PAISES
            references PAISES
)
/

create table MARCAS_MODELO
(
    ID_MARCA NUMBER       default "PFT"."ISEQ$$_71755".nextval generated as identity
		constraint PK_MARCASMODELOS
			primary key,
    NOMBRE   VARCHAR2(30)                  not null,
    ESTADO   VARCHAR2(20) default 'ACTIVO' not null
)
/

create table MODELOS_EQUIPOS
(
    ID_MODELO NUMBER       default "PFT"."ISEQ$$_71751".nextval generated as identity
		constraint PK_MODELOSEQUIPOS
			primary key,
    ID_MARCA  NUMBER                        not null
        constraint FK_MODELOSEQUIPOS_MARCASMODELOS
            references MARCAS_MODELO,
    NOMBRE    VARCHAR2(50)                  not null
        constraint UK_MODELOSEQUIPOS
            unique,
    ESTADO    VARCHAR2(20) default 'ACTIVO' not null
)
/

create table OPERACIONES
(
    ID_OPERACION     NUMBER generated as identity
        constraint PK_OPERACIONES
            primary key,
    NOMBRE_OPERACION VARCHAR2(30) not null
        constraint UK_NOMBREOPERACIONES
            unique
)
/

create table INSTITUCIONES
(
    ID_INSTITUCION NUMBER generated as identity
        constraint PK_INSTITUCIONES
            primary key,
    NOMBRE         VARCHAR2(40) not null
)
/

create table USUARIOS
(
    ID_USUARIO       NUMBER       default "PFT"."ISEQ$$_71724".nextval generated as identity
		constraint PK_USUARIOS
			primary key,
    CEDULA           VARCHAR2(8)                   not null
        constraint CEDULA
            unique,
    NOMBRE           VARCHAR2(50)                  not null,
    APELLIDO         VARCHAR2(50)                  not null,
    ID_PERFIL        NUMBER                        not null,
    ID_INSTITUCION   NUMBER                        not null
        constraint FK_USUARIOS_INSTITUCIONES
            references INSTITUCIONES,
    EMAIL            VARCHAR2(255)                 not null
        constraint EMAIL
            unique,
    NOMBRE_USUARIO   VARCHAR2(255)                 not null,
    CONTRASENIA      VARCHAR2(255)                 not null,
    ESTADO           VARCHAR2(20) default 'ACTIVO' not null,
    FECHA_NACIMIENTO DATE
)
/

create table USUARIOS_TELEFONOS
(
    ID_TELEFONO NUMBER generated as identity
        constraint PK_TELEFONO
            primary key,
    ID_USUARIO  NUMBER
        constraint FK_USUARIOSTELEFONOS_USUARIOS
            references USUARIOS,
    NUMERO      VARCHAR2(20) not null
)
/

create table AUDITORIAS
(
    ID_AUDITORIA NUMBER generated as identity
        constraint PK_AUDITORIAS
            primary key,
    FECHA_HORA   DATE   not null,
    ID_USUARIO   NUMBER not null
        constraint FK_AUDITORIAS_USUARIO
            references USUARIOS,
    ID_OPERACION NUMBER not null
        constraint FK_AUDITORIAS_OPERACION
            references OPERACIONES
)
/

create table UBICACIONES
(
    ID_UBICACION   NUMBER       default "PFT"."ISEQ$$_71781".nextval generated as identity
		constraint PK_UBICACIONES
			primary key,
    NOMBRE         VARCHAR2(30)                  not null,
    SECTOR         VARCHAR2(30),
    PISO           NUMBER,
    NUMERO         NUMBER,
    CAMA           NUMBER,
    ID_INSTITUCION NUMBER
        constraint FK_UBICACIONES_INSTITUCIONES
            references INSTITUCIONES,
    ESTADO         VARCHAR2(20) default 'ACTIVO' not null
)
/

create table EQUIPOS
(
    ID_EQUIPO         NUMBER       default "PFT"."ISEQ$$_71740".nextval generated as identity
		constraint PK_EQUIPOS
			primary key,
    ID_INTERNO        VARCHAR2(50)  not null
        constraint UK_IDINTERNOEQUIPOS
            unique,
    ID_UBICACION      NUMBER        not null
        constraint FK_EQUIPOS_UBICACIONES
            references UBICACIONES,
    NRO_SERIE         VARCHAR2(100) not null
        constraint UK_NROSERIEEQUIPOS
            unique,
    NOMBRE            VARCHAR2(100) not null,
    ID_TIPO           NUMBER        not null
        constraint FK_EQUIPOS_TIPOSEQUIPOS
            references TIPOS_EQUIPOS,
    GARANTIA          VARCHAR2(20),
    ESTADO            VARCHAR2(20) default 'ACTIVO',
    ID_PROVEEDOR      NUMBER
        constraint FK_EQUIPOS_PROVEEDORES
            references PROVEEDORES_EQUIPOS,
    ID_PAIS           NUMBER
        constraint FK_EQUIPOS_PAISES
            references PAISES,
    ID_MODELO         NUMBER
        constraint FK_EQUIPOS_MODELOSEQUIPOS
            references MODELOS_EQUIPOS,
    IMAGEN            VARCHAR2(255),
    FECHA_ADQUISICION DATE
)
/

create table INTERVENCIONES
(
    ID_INTERVENCION NUMBER generated as identity
        constraint PK_INTERVENCIONES
            primary key,
    ID_USUARIO      NUMBER        not null
        constraint FK_INTERVENCIONES_USUARIOS
            references USUARIOS,
    ID_TIPO         NUMBER        not null
        constraint FK_INTERVENCIONES_TIPOSINTERVENCIONES
            references TIPOS_INTERVENCIONES,
    ID_EQUIPO       NUMBER        not null
        constraint FK_INTERVENCIONES_EQUIPOS
            references EQUIPOS,
    MOTIVO          VARCHAR2(255) not null,
    FECHA_HORA      DATE          not null,
    COMENTARIOS     VARCHAR2(255)
)
/

create table BAJA_EQUIPOS
(
    ID_BAJA     NUMBER       default "PFT"."ISEQ$$_71784".nextval generated as identity
		constraint PK_BAJAEQUIPOS
			primary key,
    ID_USUARIO  NUMBER                        not null
        constraint FK_BAJAEQUIPOS_USUARIOS
            references USUARIOS,
    ID_EQUIPO   NUMBER                        not null
        constraint FK_BAJAEQUIPOS_EQUIPOS
            references EQUIPOS,
    RAZON       VARCHAR2(30)                  not null,
    FECHA       DATE                          not null,
    ESTADO      VARCHAR2(20) default 'ACTIVO' not null,
    COMENTARIOS VARCHAR2(100)
)
/

create table EQUIPOS_UBICACIONES
(
    ID_MOVIMIENTO NUMBER generated as identity
        constraint PK_EQUIPOSUBICACIONES
            primary key,
    ID_EQUIPO     NUMBER
        constraint FK_EQUB_EQUIPOS
            references EQUIPOS,
    ID_UBICACION  NUMBER
        constraint FK_EQUB_UBICACIONES
            references UBICACIONES,
    FECHA         DATE,
    USUARIO       NUMBER
        constraint FK_EQUB_USUARIOS
            references USUARIOS,
    OBSERVACIONES VARCHAR2(1000)
)
/

create table BAJA_UBICACIONES
(
    ID_BAJA      NUMBER(38) generated as identity
        constraint PK_BAJA_UBICACIONES
            primary key,
    ID_USUARIO   NUMBER(38)
        constraint FK_BAJA_UBICACIONES_USUARIOS
            references USUARIOS,
    ID_UBICACION NUMBER(38)
        constraint FK_BAJA_UBICACIONES_UBICACIONES
            references UBICACIONES,
    RAZON        VARCHAR2(255),
    COMENTARIO   VARCHAR2(255),
    FECHA        DATE not null
)
/

create table FUNCIONALIDADES
(
    ID_FUNCIONALIDAD     NUMBER(38) generated as identity
        constraint PK_FUNCIONALIDADES
            primary key,
    NOMBRE_FUNCIONALIDAD VARCHAR2(255),
    ESTADO               VARCHAR2(255),
    RUTA                 VARCHAR2(100)
)
/

create table FUNCIONALIDADES_PERFILES
(
    ID_FUNCIONALIDAD NUMBER(38) not null
        constraint FK_FUNCIONALIDADES_PERFILES_ON_ID_FUNCIONALIDAD
            references FUNCIONALIDADES,
    ID_PERFIL        NUMBER(38) not null,
    constraint PK_FUNCIONALIDADES_PERFILES
        primary key (ID_FUNCIONALIDAD, ID_PERFIL)
)
/

create table PERFILES
(
    ID_PERFIL     NUMBER(38) generated as identity
        constraint PK_PERFILES
            primary key,
    NOMBRE_PERFIL VARCHAR2(20) not null,
    ESTADO        VARCHAR2(20) not null
)
/

