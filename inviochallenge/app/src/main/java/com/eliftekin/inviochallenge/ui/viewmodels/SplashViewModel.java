package com.eliftekin.inviochallenge.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eliftekin.inviochallenge.models.PageInfo;
import com.eliftekin.inviochallenge.repo.DataRepository;

public class SplashViewModel extends ViewModel {
    private DataRepository dataRepository;

    private MutableLiveData<PageInfo> pageInfoLiveData;
    private MutableLiveData<String> error;

    public SplashViewModel() {
        dataRepository = new DataRepository();
        pageInfoLiveData = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public LiveData<PageInfo> getPageInfoLiveData(){
        return pageInfoLiveData;
    }

    public LiveData<String> getErrorMessage(){
        return error;
    }

    public void fetchData(int sayfa_no){
        dataRepository.getData(sayfa_no, new DataRepository.DataCallBack() {
            @Override
            public void onSuccess(PageInfo pageInfo) {
                pageInfoLiveData.setValue(pageInfo);
            }

            @Override
            public void onFailed(String errorMessage) {
                error.setValue(errorMessage);
            }
        });
    }

}
