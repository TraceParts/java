package org.example.search.checkAvailabilityWith;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class partNumber implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new partNumber()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Part Number as you have in your own data.")
    private String partNumber;
    @CommandLine.Parameters(index = "2", description = "Catalog label as you have in your own data.")
    private String catalog;

    @CommandLine.Option(names = {"--removeChar"}, defaultValue = "false", description = "The following characters are not evaluating (\" \", \".\", \"-\", \"/\", \"+\").")
    private Boolean removeChar;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-partnumber-availability
        ApiResults.showApiReturn(getPartNumber(removeChar, RootApiUrl.ROOT_API_URL, partNumber, catalog, token));

    }

    public static HttpResponse<String> getPartNumber(Boolean removeChar, String rootApiUrl, String partNumber, String catalog, String token) {
        String removeCharString = "";
        // As the default value is false, if returnYourOwnCodes is false it is just ignored
        if (removeChar != null) {
            removeCharString = "&removeChar=" + (removeChar ? "true" : "false");
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Search/PartNumber/Availability?partNumber=" + URLEncoder.encode(partNumber, StandardCharsets.UTF_8) + "&catalog=" + URLEncoder.encode(catalog, StandardCharsets.UTF_8) + removeCharString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
