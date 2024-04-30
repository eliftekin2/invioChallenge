package com.eliftekin.inviochallenge.repo;

import static com.eliftekin.inviochallenge.service.RetrofitClient.getClient;

import com.eliftekin.inviochallenge.models.PageInfo;
import com.eliftekin.inviochallenge.service.DataAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private DataAPI dataAPI;
    private PageInfo pageInfo;

    public DataRepository() {
        dataAPI = getClient().create(DataAPI.class);
        pageInfo = new PageInfo();
    }

    public void getData(int sayfa_no, DataCallBack callBack){
        dataAPI.getInfo(sayfa_no).enqueue(new Callback<PageInfo>() {
            @Override
            public void onResponse(Call<PageInfo> call, Response<PageInfo> response) {
                if (response.isSuccessful()){
                    pageInfo = response.body();

                    if(callBack != null)
                        callBack.onSuccess(pageInfo);
                }
                else {
                    if(callBack != null)
                        callBack.onFailed("Hata mesajı: " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<PageInfo> call, Throwable t) {
                if(callBack != null)
                    callBack.onFailed("Hata mesajı: " + t);
            }
        });
    }

    public interface DataCallBack{
        void onSuccess(PageInfo pageInfo);
        void onFailed(String errorMessage);
    }
}
