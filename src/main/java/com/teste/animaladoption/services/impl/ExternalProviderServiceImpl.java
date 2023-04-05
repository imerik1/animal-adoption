package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.services.ExternalProviderService;
import jakarta.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Transactional
public class ExternalProviderServiceImpl implements ExternalProviderService {
    private final RestTemplate restTemplate;

    public ExternalProviderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T get(URI uri, @Nullable ParameterizedTypeReference<T> body, @Nullable HttpEntity<Object> httpEntity) {
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, body).getBody();
    }
}
