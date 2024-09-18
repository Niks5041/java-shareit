package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.RequestDto;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllRequestsByUserId(Integer userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllRequestsByOtherUsers() {
        return get("");
    }

    public ResponseEntity<Object> addNewRequest(RequestDto requestDto, Integer requestId) {
        return post("", requestId, requestDto);
    }

    public ResponseEntity<Object> getInfoOfRequestById(Integer requestId, Integer itemId) {
        return get("/" + requestId, itemId);
    }
}
