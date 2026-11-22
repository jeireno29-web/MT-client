package com.mtclient;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/** Resolves the Fabric loader and profile JSON for an official Minecraft version. */
public final class FabricManager {
    private final HttpClient http = HttpClient.newHttpClient();

    public String getLatestLoader(String minecraftVersion) throws Exception {
        String base = "https://meta.fabricmc.net/v2/versions/loader/";
        String json = get(base + minecraftVersion);
        JsonArray loaders = JsonParser.parseString(json).getAsJsonArray();
        if (loaders.isEmpty()) throw new IllegalArgumentException("Fabric no disponible para " + minecraftVersion);
        return loaders.get(0).getAsJsonObject().getAsJsonObject("loader").get("version").getAsString();
    }

    public void prepareProfile(String minecraftVersion, Path root) throws Exception {
        String loader = getLatestLoader(minecraftVersion);
        Path profiles = root.resolve("profiles").resolve("fabric-" + minecraftVersion);
        Files.createDirectories(profiles);
        Files.writeString(profiles.resolve("fabric-loader.txt"), loader);
    }

    private String get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new IllegalStateException("Fabric metadata HTTP " + response.statusCode());
        return response.body();
    }
}
