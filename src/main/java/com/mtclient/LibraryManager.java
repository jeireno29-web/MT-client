package com.mtclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/** Downloads the official libraries and classpath required by a Minecraft version. */
public final class LibraryManager {
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30)).build();

    public String buildClasspath(JsonObject versionInfo, Path root) throws Exception {
        String os = operatingSystem();
        StringBuilder classpath = new StringBuilder();

        JsonArray libraries = versionInfo.getAsJsonArray("libraries");
        for (var element : libraries) {
            JsonObject library = element.getAsJsonObject();
            if (!isAllowed(library, os)) continue;

            JsonObject downloads = library.getAsJsonObject("downloads");
            JsonObject artifact = downloads.has("artifact")
                    ? downloads.getAsJsonObject("artifact") : null;
            if (artifact == null) continue;

            String path = artifact.get("path").getAsString();
            Path target = root.resolve("libraries").resolve(path);
            download(artifact.get("url").getAsString(), target);
            appendJar(classpath, target);
        }

        return classpath.toString();
    }

    private boolean isAllowed(JsonObject library, String os) {
        if (!library.has("rules")) return true;
        boolean allowed = false;
        for (var ruleElement : library.getAsJsonArray("rules")) {
            JsonObject rule = ruleElement.getAsJsonObject();
            boolean applies = !rule.has("os") || rule.getAsJsonObject("os")
                    .get("name").getAsString().equals(os);
            if (!applies) continue;
            allowed = "allow".equals(rule.get("action").getAsString());
        }
        return allowed;
    }

    private String operatingSystem() {
        String name = System.getProperty("os.name").toLowerCase();
        if (name.contains("win")) return "windows";
        if (name.contains("mac")) return "osx";
        return "linux";
    }

    private void download(String url, Path target) throws Exception {
        if (Files.exists(target) && Files.size(target) > 0) return;
        Files.createDirectories(target.getParent());
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<Path> response = http.send(request, HttpResponse.BodyHandlers.ofFile(target));
        if (response.statusCode() >= 400) throw new IOException("Library download failed: " + response.statusCode());
    }

    private void appendJar(StringBuilder classpath, Path jar) {
        if (!classpath.isEmpty()) classpath.append(java.io.File.pathSeparatorChar);
        classpath.append(jar);
    }
}
