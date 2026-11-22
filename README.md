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
