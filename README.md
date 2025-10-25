# Serviasociados - Sistema de Gestión de Taller

## Integrantes  
Sebastian Medellin Quintero  
Alejandra Rivera Montero  
Jhoan Andres Serna Castro

## Descripción
Aplicación de escritorio desarrollada en JavaFX para la gestión de un taller de servicios asociados. Permite la administración de usuarios, clientes y operaciones básicas del taller mediante una interfaz gráfica intuitiva.

## Tecnologías Utilizadas
- **Java 24**: Lenguaje de programación principal
- **JavaFX**: Framework para la interfaz gráfica de usuario
- **MySQL**: Base de datos relacional
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JDBC**: Conexión a base de datos

## Funcionalidades Implementadas
- **Sistema de Autenticación**: Login con validación de usuarios por roles (Administrador/Recepción)
- **Gestión de Usuarios**: Crear, actualizar, buscar y listar usuarios del sistema
- **Gestión de Clientes**: Registro, actualización y consulta de información de clientes
- **Interfaz Modular**: Menús diferenciados según el rol del usuario

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/proyecto/serviasociados/
│   │   ├── Main.java                    # Punto de entrada de la aplicación
│   │   ├── config/
│   │   │   └── AppServices.java         # Servicios de la aplicación
│   │   ├── controlador/                 # Controladores JavaFX
│   │   │   ├── LoginController.java
│   │   │   ├── MenuAdministradorController.java
│   │   │   ├── MenuRecepcionController.java
│   │   │   ├── UsuariosAccesoController.java
│   │   │   └── ClientesController.java
│   │   ├── modelo/                      # Modelos de datos
│   │   │   ├── UsuarioAccesoModelo.java
│   │   │   ├── ClientesModelo.java
│   │   │   ├── CitaModelo.java
│   │   │   ├── MecanicosModelo.java
│   │   │   ├── ServicioModelo.java
│   │   │   └── VehiculosModelo.java
│   │   └── services/
│   │       └── ConexionBDD.java         # Conexión a base de datos
│   └── resources/
│       ├── vista/                       # Archivos FXML
│       └── imagenes/                    # Recursos gráficos
└── test/                                # Pruebas unitarias
```


## Estado del Desarrollo
Proyecto en fase de desarrollo con funcionalidades básicas implementadas. Incluye sistema de login funcional, gestión de usuarios y clientes, con base preparada para expansión a módulos de citas, vehículos y mecánicos.

