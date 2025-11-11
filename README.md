# Serviasociados - Sistema de Agendamiento para Taller

## Integrantes
Sebastian Medellin Quintero  
Alejandra Rivera Montero

## Descripción
Sistema de agendamiento completo para el taller Serviasociados, desarrollado como aplicación de escritorio en JavaFX. Permite la gestión integral de un taller mecánico, incluyendo autenticación de usuarios por roles, administración de clientes, vehículos, citas, mecánicos, servicios y historial de operaciones. La aplicación cuenta con una interfaz gráfica intuitiva y modular, diferenciada por roles de usuario (Administrador y Recepción), facilitando la operación diaria del taller.

## Tecnologías Utilizadas
- **Java 24**: Lenguaje de programación principal
- **JavaFX**: Framework para la interfaz gráfica de usuario (controles, FXML, gráficos)
- **MySQL**: Base de datos relacional para almacenamiento de datos
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JDBC**: API para conexión y operaciones con la base de datos MySQL


## Funcionalidades Implementadas
### Sistema de Autenticación
- Login seguro con validación de credenciales
- Diferenciación de roles: Administrador y Recepción
- Acceso restringido según permisos del usuario

### Gestión de Usuarios
- Creación, actualización, búsqueda y eliminación de usuarios
- Asignación de roles (Administrador/Recepción)
- Listado completo de usuarios del sistema

### Gestión de Clientes
- Registro y actualización de información de clientes
- Consulta y búsqueda de clientes
- Interfaz diferenciada para Administrador y Recepción

### Gestión de Vehículos
- Registro y administración de vehículos asociados a clientes
- Actualización de datos de vehículos
- Vinculación con citas y servicios

### Gestión de Citas
- Programación de citas para servicios
- Visualización de citas programadas
- Gestión de estados de citas (programada, en proceso, completada, etc.)

### Gestión de Mecánicos
- Administración de información de mecánicos
- Asignación de mecánicos a servicios y citas

### Gestión de Servicios Mecánicos
- Definición y administración de servicios ofrecidos
- Vinculación de servicios a citas y mecánicos

### Historial de Citas
- Consulta del historial completo de citas realizadas
- Seguimiento de servicios completados

### Interfaz Modular
- Menús diferenciados según rol del usuario
- Navegación intuitiva entre módulos
- Estilos CSS aplicados para una experiencia visual coherente

## Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/proyecto/serviasociados/
│   │   ├── Main.java                          # Punto de entrada de la aplicación
│   │   ├── config/
│   │   │   └── AppServices.java               # Servicios globales de la aplicación (instancias de modelos)
│   │   ├── controlador/                       # Controladores JavaFX (lógica de vistas)
│   │   │   ├── LoginController.java           # Controlador de login
│   │   │   ├── MenuAdministradorController.java  # Menú principal para Administrador
│   │   │   ├── MenuRecepcionController.java   # Menú principal para Recepción
│   │   │   ├── UsuarioAccesoController.java   # Gestión de usuarios
│   │   │   ├── ClienteAdministradorController.java  # Gestión de clientes (Admin)
│   │   │   ├── ClienteRecepcionController.java      # Gestión de clientes (Recepción)
│   │   │   ├── VehiculoAdministradorController.java # Gestión de vehículos (Admin)
│   │   │   ├── VehiculoRecepcionController.java     # Gestión de vehículos (Recepción)
│   │   │   ├── CitaController.java             # Gestión de citas
│   │   │   ├── CitasProgramadasController.java # Visualización de citas programadas
│   │   │   ├── HistorialCitasController.java   # Historial de citas
│   │   │   └── ServicioMecanicoController.java # Gestión de servicios mecánicos
│   │   ├── modelo/                            # Modelos de datos (POJOs)
│   │   │   ├── UsuarioAccesoModelo.java       # Modelo de usuario
│   │   │   ├── ClienteModelo.java             # Modelo de cliente
│   │   │   ├── VehiculoModelo.java            # Modelo de vehículo
│   │   │   ├── CitaModelo.java                # Modelo de cita
│   │   │   ├── MecanicoModelo.java            # Modelo de mecánico
│   │   │   ├── ServicioModelo.java            # Modelo de servicio
│   │   │   └── EstadoModelo.java              # Modelo de estados
│   │   └── services/
│   │       └── ConexionBDD.java               # Servicio de conexión a base de datos MySQL
│   └── resources/
│       ├── vista/                             # Archivos FXML (interfaces de usuario)
│       │   ├── LoginVista.fxml                # Vista de login
│       │   ├── MenuAdministradorVista.fxml    # Vista menú administrador
│       │   ├── MenuRecepcionVista.fxml        # Vista menú recepción
│       │   ├── UsuarioAccesoVista.fxml        # Vista gestión usuarios
│       │   ├── ClienteAdministradorVista.fxml # Vista clientes (Admin)
│       │   ├── ClienteRecepcionVista.fxml     # Vista clientes (Recepción)
│       │   ├── VehiculoAdministradorVista.fxml # Vista vehículos (Admin)
│       │   ├── VehiculoRecepcionVista.fxml    # Vista vehículos (Recepción)
│       │   ├── CitaVista.fxml                 # Vista gestión citas
│       │   ├── CitasProgramadasVista.fxml     # Vista citas programadas
│       │   ├── HistorialCitasVista.fxml       # Vista historial citas
│       │   └── ServiciosMecanicosVista.fxml   # Vista servicios mecánicos
│       ├── imagenes/                          # Recursos gráficos (logos, íconos)
│       └── estilos.css                        # Estilos CSS para la interfaz
└── test/                                      # Directorio para pruebas unitarias
```

## Estado del Desarrollo
Proyecto completamente terminado y funcional. Todas las funcionalidades planificadas han sido implementadas, incluyendo autenticación, gestión completa de entidades (usuarios, clientes, vehículos, citas, mecánicos, servicios) y módulos de historial. La aplicación está lista para uso en producción con una interfaz gráfica completa y conexión a base de datos MySQL.

