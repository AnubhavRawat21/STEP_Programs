package com.gdb.apis.integrations;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpCompanyApiService implements ICompanyApiService {
    private static final Logger logger = LogManager.getLogger(HttpCompanyApiService.class);
    private final HttpClient httpClient;
    private final String API_URL = "https://company-crv-service.vercel.app/api/v1/company/verify/";

    public HttpCompanyApiService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public boolean checkIfCompanyRegistrationIsValid(String companyRegistrationNumber) {
        logger.info("Validating company registration with external service: {}", companyRegistrationNumber);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + companyRegistrationNumber))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            boolean isValid = response.statusCode() == 200;
            logger.info("Company validation result for {}: {}", companyRegistrationNumber, isValid);
            return isValid;
        } catch (InterruptedException e) {
            logger.error("Interrupted while calling Company API: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            logger.error("Error calling Company API: {}", e.getMessage());
            return false;
        }
    }
}