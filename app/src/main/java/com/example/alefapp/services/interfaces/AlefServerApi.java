package com.example.alefapp.services.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface AlefServerApi {
    @GET("{list}")
    public Call<List<String>> getURLsList(@Path("list") String urlsList );
}
