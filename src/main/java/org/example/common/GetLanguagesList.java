package org.example.common;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetLanguagesList implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new GetLanguagesList()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-supportedlanguages
        ApiResults.showApiReturn(getGetLanguageList(RootApiUrl.ROOT_API_URL, token));
    }

    public static HttpResponse<String> getGetLanguageList(String rootApiUrl, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/SupportedLanguages"))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
