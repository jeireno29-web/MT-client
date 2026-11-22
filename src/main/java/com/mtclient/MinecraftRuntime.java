package com.mtclient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Descarga una versión desde Mojang y construye el comando Java básico.
 */
public final class MinecraftRuntime {
    private static final String VERSION_MANIFEST =
            "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

    public void launch(String version, String loader, int memory) throws Exception {
        Path root = runtimeRoot();
        Files.createDirectories(root);

        String manifestJson = httpGet(VERSION_MANIFEST);
        JsonObject manifest = JsonParser.parseString(manifestJson).getAsJsonObject();
        String url = manifest.getAsJsonArray("versions").asList().stream()
                .map(element -> element.getAsJsonObject())
                .filter(item -> item.get("id").getAsString().equals(version))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Versión no encontrada: " + version))
                .get("url").getAsString();

        String versionJson = httpGet(url);
        JsonObject versionInfo = JsonParser.parseString(versionJson).getAsJsonObject();
        JsonObject downloads = versionInfo.getAsJsonObject("downloads");
        JsonObject client = downloads.getAsJsonObject("client");

        Path versionsDir = root.resolve("versions").resolve(version);
        Files.createDirectories(versionsDir);
        Path jar = versionsDir.resolve(version + ".jar");
        download(client.get("url").getAsString(), jar);

        List<String> command = new ArrayList<>();
        command.add(javaExecutable());
        command.add("-Xmx" + memory + "G");
        command.add("-Xms" + Math.min(2, memory) + "G");
        command.add("-cp");
        command.add(jar.toString());
        command.add(mainClass(versionInfo));
        command.add("--username");
        command.add("MTPlayer");
        command.add("--version");
        command.add(version);
        command.add("--gameDir");
        command.add(root.toString());
        command.add("--assetsDir");
        command.add(root.resolve("assets").toString());

        ProcessBuilder process = new ProcessBuilder(command);
        process.directory(root.toFile());
        process.redirectErrorStream(true);
        process.start();
    }

    private String mainClass(JsonObject versionInfo) {
        return versionInfo.get("mainClass").getAsString();
    }

    private Path runtimeRoot() {
        String home = System.getProperty("user.home");
        if (isWindows()) return Path.of(home, "AppData", "Roaming", ".mtclient");
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return Path.of(home, "Library", "Application Support", "mtclient");
        }
        return Path.of(home, ".mtclient");
    }

    private String javaExecutable() {
        if (isWindows()) return "javaw";
        return "java";
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private String httpGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new IllegalStateException("HTTP " + response.statusCode());
        return response.body();
    }

    private void download(String url, Path target) throws Exception {
        if (Files.exists(target) && Files.size(target) > 0) return;
        Files.createDirectories(target.getParent());
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofFile(target));
    }
}
