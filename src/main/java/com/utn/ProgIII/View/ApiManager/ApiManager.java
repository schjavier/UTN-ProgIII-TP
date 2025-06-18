package com.utn.ProgIII.View.ApiManager;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.IOException;
import java.util.Map;

public interface ApiManager {

    public <T> T Get(String path, String id, Class<T> responseType) throws IOException, InterruptedException;
    public <T> T Post(String path, Object requestBody, Class<T> responseType) throws IOException, InterruptedException;
    public <T> T Put(String path, Object requestBody, String id, Class<T> responseType) throws IOException, InterruptedException;
    public <T> T Delete(String path, String id, Class<T> responseType) throws IOException, InterruptedException;


    public <T> T Get(String path, String id, Class<T> responseType,Map<String,String> queryParams) throws IOException, InterruptedException;
    public <T> T Post(String path, Object requestBody, Class<T> responseType, Map<String,String> queryParams) throws IOException, InterruptedException;
    public <T> T Put(String path, Object requestBody, String id, Class<T> responseType, Map<String,String> queryParams) throws IOException, InterruptedException;
    public <T> T Delete(String path, String id, Class<T> responseType, Map<String,String> queryParams) throws IOException, InterruptedException;

}
