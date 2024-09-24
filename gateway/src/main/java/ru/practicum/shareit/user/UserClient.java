package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserUpdateRequest;


@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> addNewUser(UserCreateRequest userCreateRequest) {
        return post("", userCreateRequest);
    }

    public ResponseEntity<Object> updateUser(Integer userId, UserUpdateRequest userUpdateRequest) {
        return patchWithBody("/" + userId, userUpdateRequest);
    }

    public ResponseEntity<Object> getUserById(Integer userId) {
        return get("/" + userId, null, null);
    }

    public void deleteUserById(Integer userId) {
        delete("/" + userId);
    }
}
