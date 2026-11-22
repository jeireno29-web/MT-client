# MT Client

MVP visual de un launcher moderno para Minecraft.

## Qué incluye

- Interfaz adaptable para escritorio y móvil
- Selección de versión y cargador (Vanilla/Fabric)
- Perfiles persistidos en `localStorage`
- Ajustes de RAM y pantalla completa
- Estados visuales de inicio y navegación

## Ejecutar

No requiere instalación ni dependencias:

```bash
python3 -m http.server 8080
```

Después abre `http://localhost:8080`.

## Alcance actual

Esta versión es un prototipo web. No descarga Java, Minecraft, Fabric ni ejecuta procesos locales. Para convertirla en launcher real se necesita integración con el sistema operativo, una librería de autenticación Microsoft y una implementación de descarga y lanzamiento de instalaciones.
