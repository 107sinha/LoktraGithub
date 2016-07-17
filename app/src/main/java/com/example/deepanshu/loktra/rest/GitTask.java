package com.example.deepanshu.loktra.rest;

import com.example.deepanshu.loktra.model.Github;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by deepanshu on 14/7/16.
 */
public interface GitTask {
    @GET("/repos/rails/rails/commits")
    Call<List<Github>> getCommits();
}
