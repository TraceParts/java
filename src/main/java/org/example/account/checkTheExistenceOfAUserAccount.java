package org.example.account;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class checkTheExistenceOfAUserAccount implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new checkTheExistenceOfAUserAccount()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "Email address linked to the account.")
    private String userEmail;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-account-checklogin-useremail
        ApiResults.showApiReturn(getCheckTheExistenceOfAUserAccount(RootApiUrl.ROOT_API_URL, userEmail, token));
    }

    public static HttpResponse<String> getCheckTheExistenceOfAUserAccount(String rootApiUrl, String userEmail, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Account/CheckLogin/" + URLEncoder.encode(userEmail, StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }
}
