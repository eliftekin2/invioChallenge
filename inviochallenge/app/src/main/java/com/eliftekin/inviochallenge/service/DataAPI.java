package com.eliftekin.inviochallenge.service;

import com.eliftekin.inviochallenge.models.PageInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataAPI {
    @GET("usg-challenge/universities-at-turkey/page-{sayfa_no}.json")
    Call<PageInfo> getInfo(@Path("sayfa_no") int sayfa_no);
}
