package com.example.alefapp.services;

import com.example.alefapp.services.interfaces.AlefServerApi;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final String BASE_URL = "http://dev-tasks.alef.im/task-m-001/";
    private static NetworkService networkService;
    private Retrofit retrofit;


    public NetworkService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if(networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public void requestURLsList(Callback<List<String>> callback){
        AlefServerApi serverApi = retrofit.create(AlefServerApi.class);
        serverApi.getURLsList("list.php").enqueue(callback);
    }
}
