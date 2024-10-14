package org.example.cadFileDelivery;

import org.example.utils.ApiResults;
import org.example.utils.RootApiUrl;
import picocli.CommandLine;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class getCadFileUrl implements Runnable {
    public static void main(String[] args) {
        new CommandLine(new getCadFileUrl()).execute(args);
    }

    @CommandLine.Parameters(index = "0", description = "Token generated with the Tenant Unique ID and the API key")
    private String token;

    @CommandLine.Parameters(index = "1", description = "ID of the request provided by the cadRequest end point")
    private Integer cadRequestId;

    @CommandLine.Option(names = {"-l", "--loop", "--loopRequest"}, defaultValue = "false", description = "Loop the request until it as a definitive answer. This can take a while")
    private Boolean loopRequest;

    @Override
    public void run() {
        // documentation : https://developers.traceparts.com/v2/reference/get_v2-product-cadfileurl
        ApiResults.showApiReturn(
                loopRequest
                        ? loopedGetGetCadFileUrl(RootApiUrl.ROOT_API_URL, cadRequestId, token)
                        : getGetCadFileUrl(RootApiUrl.ROOT_API_URL, cadRequestId, token)
        );
    }

    public static HttpResponse<String> getGetCadFileUrl(String rootApiUrl, Integer cadRequestId, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Product/cadFileUrl?cadRequestId=" + URLEncoder.encode(String.valueOf(cadRequestId), StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return ApiResults.getApiReturn(request);
    }

    public static HttpResponse<String> loopedGetGetCadFileUrl(String rootApiUrl, Integer cadRequestId, String token) {
        System.out.println("📘 Warning! Any tries will be recorded in the Production data.");
        int timeout = 10; // in minutes
        int interval = 2; // in seconds

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootApiUrl + "v2/Product/cadFileUrl?cadRequestId=" + URLEncoder.encode(String.valueOf(cadRequestId), StandardCharsets.UTF_8)))
                .header("accept", "application/json")
                .header("authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> lastResponse = null;
        String resultMessage = "Timeout reached (" + timeout + " minutes with " + interval + " seconds interval). Your model couldn't be generated.";
        int nbrOfIterations = timeout * 60 / interval;
        for (int i = 1; i < nbrOfIterations; i++) {
            System.out.println("Request " + (i + 1) + "/" + nbrOfIterations);
            lastResponse = ApiResults.getApiReturn(request);
            // code 204 means wait
            if (lastResponse.statusCode() == 204) {
                try {
                    Thread.sleep(interval * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                // TimeUnit.SECONDS.sleep(seconds) can also be used here with modification
                // You could also use https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ScheduledExecutorService.html
            } else {
                if (lastResponse.statusCode() >= 200 && lastResponse.statusCode() <= 299) {
                    resultMessage = "Success, results arrived !";
                } else {
                    resultMessage = "An error occurred. Please refer to the status code to know what append.";
                }
                break;
            }
        }
        System.out.println(resultMessage);
        return lastResponse;
    }
}
