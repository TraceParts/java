package org.example.dataIndexing;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class catalogContactDetails implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new catalogContactDetails()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "TraceParts code of the classification.")
    private String classificationCode;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v1-contact-catalog
        ApiResults.showApiReturn(getCatalogContactDetails(RootApiUrl.ROOT_API_URL, classificationCode, token));
    }

    public static HttpResponse<String> getCatalogContactDetails(String rootApiUrl, String classificationCode, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v1/Contact/Catalog?classificationCode=" + URLEncoder.encode(classificationCode, StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
