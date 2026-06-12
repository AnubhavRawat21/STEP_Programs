package com.gdb.tests.services;

import com.gdb.apis.integrations.HttpCompanyApiService;
import com.gdb.apis.integrations.ICompanyApiService;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpCompanyApiServiceTest {

    @Test
    void testCheckIfCompanyRegistrationIsValid_ValidResponse() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        ICompanyApiService service = new HttpCompanyApiService();

        var field = HttpCompanyApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfCompanyRegistrationIsValid("U45200HR2023PTC789012");
        assertTrue(result);
    }

    @Test
    void testCheckIfCompanyRegistrationIsValid_InvalidResponse() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(404);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        HttpCompanyApiService service = new HttpCompanyApiService();

        var field = HttpCompanyApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfCompanyRegistrationIsValid("U45200HR2023PTC789012");
        assertFalse(result);
    }

    @Test
    void testCheckIfCompanyRegistrationIsValid_InterruptedException() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Test interrupt"));

        HttpCompanyApiService service = new HttpCompanyApiService();

        var field = HttpCompanyApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfCompanyRegistrationIsValid("U45200HR2023PTC789012");
        assertFalse(result);
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    void testCheckIfCompanyRegistrationIsValid_OtherException() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Network error"));

        HttpCompanyApiService service = new HttpCompanyApiService();

        var field = HttpCompanyApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfCompanyRegistrationIsValid("U45200HR2023PTC789012");
        assertFalse(result);
    }
}