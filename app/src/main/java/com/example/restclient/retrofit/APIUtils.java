package com.example.restclient.retrofit;

public class APIUtils implements Urls {

    private APIUtils(){}
    public static JsonApi getAlumnoService(){
        return RetrofitClient.getClient(URL).create(JsonApi.class);
    }
}
