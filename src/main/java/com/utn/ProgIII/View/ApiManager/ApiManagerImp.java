package com.utn.ProgIII.View.ApiManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.ProgIII.dto.LoginResponseDTO;
import jakarta.validation.constraints.Null;

public class ApiManagerImp implements  ApiManager{
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String BaseUrl = "http://localhost:8080/";
    private String authToken;

    public ApiManagerImp() {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
        this.authToken = "";
    }
    private void setToken(String token){
        this.authToken = token;
    }

    private HttpRequest.Builder createRequestBuilder(String path, @Null String id, Map<String,String> queryParams) {

        String uri = id == null || id.isEmpty() ? this.BaseUrl + path : this.BaseUrl + path + "/" + id;

        if(queryParams != null)
            uri = addQueryParams(uri, queryParams);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri));

        // Agregar headers si hay
        if (authToken != null && !authToken.isEmpty()) {
            builder.header("Authorization", "Bearer " + authToken);
        }

        return builder;
    }

    private <T> T sendRequest(HttpRequest request, Class<T> responseType) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            if(responseType == Void.class || response.body().isBlank())
                return null;

            T object = objectMapper.readValue(response.body(), responseType);

            if(object instanceof LoginResponseDTO)
                setToken(((LoginResponseDTO) object).token());

            return object;
        } else {
            throw new IOException("Error HTTP " + response.statusCode() + ": " + response.body());
        }
    }

    public <T> T Get(String path,  String id, Class<T> responseType)
            throws IOException, InterruptedException {
        return this.Get(path,id,responseType,null);
    }

    public <T> T Get(String path,  String id, Class<T> responseType,  Map<String,String> queryParams)
            throws IOException, InterruptedException {
        HttpRequest request = createRequestBuilder(path,  id, queryParams)
                .GET()
                .build();

        return sendRequest(request, responseType);
    }

    public <T> T Post(String path, Object requestBody, Class<T> responseType)
            throws IOException, InterruptedException {
        return this.Post(path,requestBody,responseType,null);
    }

    public <T> T Post(String path, Object requestBody, Class<T> responseType,  Map<String,String> queryParams)
            throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = createRequestBuilder(path,  null, queryParams)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        return sendRequest(request, responseType);
    }

    public <T> T Put(String path, Object requestBody, String id,  Class<T> responseType)
            throws IOException, InterruptedException {
        return Put(path,requestBody,id,responseType,null);
    }

    public <T> T Put(String path, Object requestBody, String id,  Class<T> responseType, Map<String,String> queryParams)
            throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = createRequestBuilder(path,  id, queryParams)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();

        return sendRequest(request, responseType);
    }

    public <T> T Delete(String path, String id, Class<T> responseType) throws IOException, InterruptedException {
        return Delete(path,id,responseType,null);
    }

    @Override
    public <T> T Patch(String path, String id, Object requestBody, Class<T> responseType) throws IOException, InterruptedException {
        return Patch(path,id,requestBody,responseType,null);
    }

    @Override
    public <T> T Delete(String path, String id, Class<T> responseType, Map<String,String> queryParams) throws IOException, InterruptedException {
        HttpRequest request = createRequestBuilder(path, id, queryParams)
                .DELETE()
                .build();

        return sendRequest(request, responseType);
    }

    @Override
    public <T> T Patch(String path, String id, Object requestBody, Class<T> responseType, Map<String, String> queryParams) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = createRequestBuilder(path, id, queryParams)
                .header("Content-Type", "application/json")
                .method("PATCH", BodyPublishers.ofString(json))
                .build();

        return sendRequest(request, responseType);
    }

    private String addQueryParams(String url, Map<String,String> queryParams){
        String paramsString = queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        return url + "?" + paramsString;
    }

}