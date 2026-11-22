# MT Client

Launcher Java multiplataforma escrito con Swing y Maven. La rama contiene el cliente web original y la base Java de escritorio.

## Compilar

Requiere JDK 17 o superior:

```bash
mvn clean package
java -jar target/mt-client-0.1.0-SNAPSHOT.jar
```

## Variables opcionales

- `MT_CLIENT_AZURE_CLIENT_ID`: ID de la aplicación Azure utilizada para el flujo Microsoft Device Code.
- `MT_CLIENT_JAVA_HOME`: directorio JDK/JRE que debe usar el launcher.

## Estado funcional

La descarga y organización de versiones, librerías y assets está preparada. Antes de distribuir una versión pública se deben completar:

1. Registro de una aplicación Microsoft pública.
2. Flujo Xbox Live → XSTS → Minecraft Services.
3. Validación de posesión de Minecraft.
4. Selección de Java compatible con cada versión.
5. Resolución completa de `arguments` y bibliotecas nativas.
6. Instalación real de Fabric y mods.
7. Empaquetado con jpackage para Windows, macOS y Linux.

No publiques credenciales, secretos de Azure ni tokens dentro del repositorio.
