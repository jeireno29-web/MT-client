# MT-client

Multiplatform Minecraft launcher and mod manager for Windows, Linux and macOS.

## Planned features

- Microsoft accounts and offline/no-premium profiles
- Minecraft versions from 1.19.4 onward
- Fabric loader support
- Profile and instance management
- Mod search and installation through Modrinth
- Built-in HUD, FPS, CPS, coordinates and zoom support

## Development setup

Requirements:

- Node.js 20+
- Rust stable
- Tauri system dependencies for your operating system

```bash
npm install
npm run tauri dev
```

## Status

Initial development scaffold. Authentication and the Minecraft download/launch pipeline are not implemented yet.
# MT Client

MT Client es un launcher de escritorio Java multiplataforma con una interfaz original en azul y negro. Su organización toma como referencia patrones comunes de launchers de Minecraft, sin copiar archivos, recursos gráficos ni código propietario de Feather Client.

## Compilar

Requiere JDK 17 o superior y Maven:

```bash
mvn clean package
java -jar target/mt-client-0.1.0-SNAPSHOT.jar
```

## Variables de entorno

- `MT_CLIENT_AZURE_CLIENT_ID`: ID de aplicación Azure para autenticación Microsoft.
- `MT_CLIENT_JAVA_HOME`: instalación Java opcional para iniciar Minecraft.

## Estado

La interfaz incluye las secciones Jugar, Perfiles y Ajustes, con tema oscuro azul/negro. La descarga de dependencias, autenticación de Minecraft y ejecución aún requieren completar componentes indicados en el código antes de usarlo como launcher de producción. No publiques credenciales ni tokens en el repositorio.
Lanzador de Minecraft moderno basado en Electron y preparado para Fabric.

## Desarrollo

```bash
npm install
npm start
```

## Compilación

```bash
npm run build
```

## Estado

Incluye interfaz, navegación y perfiles locales. La autenticación Microsoft y el motor de lanzamiento requieren integración OAuth y las APIs oficiales.
