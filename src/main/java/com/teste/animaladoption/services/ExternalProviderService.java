package com.teste.animaladoption.services;

import jakarta.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public interface ExternalProviderService {
    <T> T get(URI url, @Nullable ParameterizedTypeReference<T> body, @Nullable HttpEntity<Object> httpEntity);

    static URI builderUri(String url, @Nullable Map<String, String> queryParams, @Nullable Object... paths) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }

        if (paths != null) {
            for (Object path : paths) {
                builder = builder.pathSegment(path.toString());
            }
        }

        return builder.build().encode().toUri();
    }
}
