package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiResults {
    public static HttpResponse<String> getApiReturn(HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static void showApiReturn(HttpResponse<String> response) {
        String body = response.body();

        String bodyString = "null";
        if (body != null && !body.isEmpty() && !body.trim().isEmpty()) {
            // This prevents errors linked to an empty body
            bodyString = body;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString("{\"statusCode\":" + response.statusCode() + ",\"body\":" + bodyString + "}");
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}
