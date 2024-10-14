package org.example.dataIndexing;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class listOfProductsAndCategories implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new listOfProductsAndCategories()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "TraceParts code of the classification (to use in combination with partNumber).")
    private String classificationCode;

    @CommandLine.Option(names = {"--partFamilyCode"}, description = "TraceParts code of the product family.")
    private String partFamilyCode;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-search-productandcategorylist
        ApiResults.showApiReturn(getListOfProductsAndCategories(RootApiUrl.ROOT_API_URL, classificationCode, token, partFamilyCode));
    }

    public static HttpResponse<String> getListOfProductsAndCategories(String rootApiUrl, String classificationCode, String token, String partFamilyCode) {
        String partFamilyCodeString = "";
        // As partFamilyCodeString is optional, if it is null it is just ignored
        if (partFamilyCode != null) {
            partFamilyCodeString = "&partFamilyCode=" + URLEncoder.encode(partFamilyCode, StandardCharsets.UTF_8);
        }

        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Search/ProductAndCategoryList?classificationCode=" + URLEncoder.encode(classificationCode, StandardCharsets.UTF_8) + partFamilyCodeString))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
