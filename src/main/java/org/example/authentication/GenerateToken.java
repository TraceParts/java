package org.example.authentication;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class GenerateToken implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new GenerateToken()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Tenant Unique ID provided in the email giving you access to our API")
    private String tenantUid;

    @CommandLine.Parameters(index = "1", description = "API key provided in the email giving you access to our API")
    private String apiKey;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/post_v2-requesttoken
        ApiResults.showApiReturn(postGenerateToken(RootApiUrl.ROOT_API_URL, tenantUid, apiKey));
    }

    public static HttpResponse<String> postGenerateToken(String rootApiUrl, String tenantUid, String apiKey) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/RequestToken"))
                .header("accept", "application/json")
                .header("content-type", "application/*+json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"tenantUid\":\"" + URLEncoder.encode(tenantUid, StandardCharsets.UTF_8) + "\",\"apiKey\":\"" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8) + "\"}"))
                .build();
        return ApiResults.getApiReturn(request);
    }
}
