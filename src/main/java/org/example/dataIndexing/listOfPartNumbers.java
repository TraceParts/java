package org.example.dataIndexing;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class listOfPartNumbers implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new listOfPartNumbers()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "TraceParts code of the product family.")
    private String partFamilyCode;

    @CommandLine.Option(names = {"--returnYourOwnCodes"}, description = "If available, your own codes (i.e.: SKU, internal_code, Part_ID) are returned.")
    private Boolean returnYourOwnCodes;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v1-search-partnumberlist
        ApiResults.showApiReturn(getListOfPartNumbers(RootApiUrl.ROOT_API_URL, partFamilyCode, token, returnYourOwnCodes));
    }

    public static HttpResponse<String> getListOfPartNumbers(String rootApiUrl, String partFamilyCode, String token, Boolean returnYourOwnCodes) {
        String returnYourOwnCodesString = "";
        // As the default value is false, if returnYourOwnCodes is false it is just ignored
        if (returnYourOwnCodes != null) {
            returnYourOwnCodesString = "&removeChar=" + (returnYourOwnCodes ? "true" : "false");
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v1/Search/PartNumberList?partFamilyCode=" + URLEncoder.encode(partFamilyCode, StandardCharsets.UTF_8) + returnYourOwnCodesString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
