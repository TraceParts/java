package org.example.dataIndexing;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class listOfCategories implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new listOfCategories()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Language of the labels.")
    private String cultureInfo;
    @CommandLine.Parameters(index = "2", description = "TraceParts code of the classification (to use in combination with partNumber).")
    private String classificationCode;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-categorylist
        ApiResults.showApiReturn(getListOfCategories(RootApiUrl.ROOT_API_URL, classificationCode, cultureInfo, token));
    }

    public static HttpResponse<String> getListOfCategories(String rootApiUrl, String classificationCode, String cultureInfo, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Search/CategoryList?classificationCode=" + URLEncoder.encode(classificationCode, StandardCharsets.UTF_8) + "&cultureInfo=" + URLEncoder.encode(cultureInfo, StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
