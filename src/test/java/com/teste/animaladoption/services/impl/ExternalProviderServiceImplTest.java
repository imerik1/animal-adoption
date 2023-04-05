package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.services.ExternalProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExternalProviderServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    private ExternalProviderService externalProviderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        externalProviderService = new ExternalProviderServiceImpl(restTemplate);
    }

    @Test
    void testGet() {
        URI uri = URI.create("http://example.com");
        ParameterizedTypeReference<String> body = new ParameterizedTypeReference<String>() {
        };
        HttpEntity<Object> httpEntity = new HttpEntity<>(null);

        String responseBody = "response";

        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(body))).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        String result = externalProviderService.get(uri, body, httpEntity);

        assertEquals(responseBody, result);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(body));
    }
}
