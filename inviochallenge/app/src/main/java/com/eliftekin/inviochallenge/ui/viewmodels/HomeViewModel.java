package com.eliftekin.inviochallenge.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eliftekin.inviochallenge.models.PageInfo;
import com.eliftekin.inviochallenge.repo.DataRepository;

public class HomeViewModel extends ViewModel {
    private DataRepository dataRepository;
    private MutableLiveData<PageInfo> pageInfoMutable;
    private MutableLiveData<String> error;

    private boolean isLoading;

    public HomeViewModel() {
        dataRepository = new DataRepository();
        pageInfoMutable = new MutableLiveData<>();
        error = new MutableLiveData<>();
        isLoading = false;
    }

    public void setPageInfoMutable(PageInfo pageInfo){
        pageInfoMutable.setValue(pageInfo);
    }

    public LiveData<PageInfo> getPageInfoMutable(){
        return pageInfoMutable;
    }

    public LiveData<String> getErrorMessage(){
        return error;
    }

    public void fetchNextPage(){
        if (!isLoading){
            isLoading = true;
            int currentPage = pageInfoMutable.getValue().getCurrentPage();
            int totalPage = pageInfoMutable.getValue().getTotalPage();

            if (currentPage < totalPage){
                dataRepository.getData(++currentPage, new DataRepository.DataCallBack() {
                    @Override
                    public void onSuccess(PageInfo pageInfo) {
                        isLoading = false;
                        pageInfoMutable.setValue(pageInfo);
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        isLoading = false;
                        error.setValue(errorMessage);
                    }
                });
            }
        }
    }
}
