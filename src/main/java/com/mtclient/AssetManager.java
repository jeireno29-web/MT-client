package com.mtclient;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Downloads and extracts launcher runtime metadata exposed by Mojang. */
public final class AssetManager {
    private final HttpClient http = HttpClient.newHttpClient();

    public void downloadAssets(JsonObject versionInfo, Path root) throws Exception {
        JsonObject assets = versionInfo.getAsJsonObject("assetIndex");
        Path indexFile = root.resolve("assets").resolve("indexes")
                .resolve(assets.get("id").getAsString() + ".json");
        download(assets.get("url").getAsString(), indexFile);

        JsonObject index = com.google.gson.JsonParser.parseString(Files.readString(indexFile))
                .getAsJsonObject();
        for (var entry : index.getAsJsonObject("objects").entrySet()) {
            JsonObject metadata = entry.getValue().getAsJsonObject();
            String hash = metadata.get("hash").getAsString();
            String prefix = hash.substring(0, 2);
            Path target = root.resolve("assets").resolve("objects").resolve(prefix)
                    .resolve(hash);
            download("https://resources.download.minecraft.net/" + prefix + "/" + hash, target);
        }
    }

    private void download(String url, Path target) throws Exception {
        if (Files.exists(target) && Files.size(target) > 0) return;
        Files.createDirectories(target.getParent());
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<Path> response = http.send(request, HttpResponse.BodyHandlers.ofFile(target));
        if (response.statusCode() >= 400) throw new IOException("Asset download failed: " + response.statusCode());
    }
}
