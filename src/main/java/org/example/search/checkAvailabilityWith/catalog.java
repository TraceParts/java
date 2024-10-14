package org.example.search.checkAvailabilityWith;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class catalog implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new catalog()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Catalog label as you have in your own data.")
    private String catalog;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-catalog-availability
        ApiResults.showApiReturn(getCatalog(RootApiUrl.ROOT_API_URL, catalog, token));
    }

    public static HttpResponse<String> getCatalog(String rootApiUrl, String catalog, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Search/Catalog/Availability?catalog=" + URLEncoder.encode(catalog, StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
