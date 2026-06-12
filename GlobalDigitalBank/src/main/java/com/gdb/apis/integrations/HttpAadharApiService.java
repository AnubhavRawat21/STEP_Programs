package com.gdb.apis.integrations;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HttpAadharApiService
 * Implementation of IAadharApiService that calls the external Aadhar API.
 */
public class HttpAadharApiService implements IAadharApiService {
    private static final Logger logger = LogManager.getLogger(HttpAadharApiService.class);
    private final HttpClient httpClient;
    private final String API_URL = "https://aadhar-service.vercel.app/api/v1/verify/";

    public HttpAadharApiService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public boolean checkIfAadharIsValid(String aadharNumber) {
        logger.info("Validating Aadhar number with external service: {}", aadharNumber);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + aadharNumber))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // the API returns 200 OK for a valid Aadhar
            boolean isValid = response.statusCode() == 200;
            logger.info("Aadhar validation result for {}: {}", aadharNumber, isValid);
            return isValid;
        } catch (InterruptedException e) {
            logger.error("Interrupted while calling Aadhar API: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            logger.error("Error calling Aadhar API: {}", e.getMessage());
            return false;
        }
    }
}
