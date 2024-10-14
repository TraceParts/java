package org.example.dataIndexing;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class listOfCatalogs implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new listOfCatalogs()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Language of the labels.")
    private String cultureInfo;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-cataloglist
        ApiResults.showApiReturn(getListOfCatalogs(RootApiUrl.ROOT_API_URL, cultureInfo, token));
    }

    public static HttpResponse<String> getListOfCatalogs(String rootApiUrl, String cultureInfo, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Search/CatalogList?cultureInfo=" + URLEncoder.encode(cultureInfo, StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
