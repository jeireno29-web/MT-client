package com.mtclient.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/** Microsoft account session returned after the Xbox Live chain. */
public record MinecraftSession(String accessToken, String username, String uuid) {
    public static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30)).build();

    public static MinecraftSession authenticateDeviceCode(String microsoftDeviceCode) throws Exception {
        throw new UnsupportedOperationException(
                "Device-code polling must exchange Microsoft, Xbox Live, XSTS, and Minecraft tokens.");
    }

    static String postJson(String url, JsonObject body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new IOException("HTTP " + response.statusCode() + " from " + url);
        }
        return response.body();
    }
}
