package com.mtclient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.Desktop;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.function.Consumer;

/**
 * Implementa el flujo OAuth Device Code de Microsoft.
 * Registrar una aplicación Azure para obtener un client_id privado/público.
 */
public final class MicrosoftAuth {
    private static final String DEVICE_CODE_URL =
            "https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode";

    public void authenticate(Consumer<String> status) {
        String clientId = System.getenv("MT_CLIENT_AZURE_CLIENT_ID");
        if (clientId == null || clientId.isBlank()) {
            status.accept("Define MT_CLIENT_AZURE_CLIENT_ID antes de iniciar sesión");
            return;
        }

        try {
            String body = "client_id=" + clientId + "&scope=" +
                    java.net.URLEncoder.encode("XboxLive.signin offline_access", StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder(URI.create(DEVICE_CODE_URL))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!json.has("verification_uri") || !json.has("user_code") || !json.has("device_code")) {
                status.accept("Microsoft no devolvió un código de autorización");
                return;
            }

            String verificationUri = json.get("verification_uri").getAsString();
            String userCode = json.get("user_code").getAsString();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI.create(verificationUri));
            }
            status.accept("Autoriza el código " + userCode + " en el navegador");

        } catch (Exception error) {
            status.accept("Error de autenticación: " + error.getMessage());
        }
    }
}
