package com.gdb.tests.services;

import com.gdb.apis.integrations.HttpAadharApiService;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HttpAadharApiServiceTest {

    @Test
    void testCheckIfAadharIsValid_ValidResponse() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        HttpAadharApiService service = new HttpAadharApiService();

        // Using reflection to inject the mock client
        var field = HttpAadharApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfAadharIsValid("123456789012");
        assertTrue(result);
    }

    @Test
    void testCheckIfAadharIsValid_InvalidResponse() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.statusCode()).thenReturn(404);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        HttpAadharApiService service = new HttpAadharApiService();

        var field = HttpAadharApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfAadharIsValid("123456789012");
        assertFalse(result);
    }

    @Test
    void testCheckIfAadharIsValid_InterruptedException() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Test interrupt"));

        HttpAadharApiService service = new HttpAadharApiService();

        var field = HttpAadharApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfAadharIsValid("123456789012");
        assertFalse(result);
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @Test
    void testCheckIfAadharIsValid_OtherException() throws Exception {
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Network error"));

        HttpAadharApiService service = new HttpAadharApiService();

        var field = HttpAadharApiService.class.getDeclaredField("httpClient");
        field.setAccessible(true);
        field.set(service, mockClient);

        boolean result = service.checkIfAadharIsValid("123456789012");
        assertFalse(result);
    }
}
