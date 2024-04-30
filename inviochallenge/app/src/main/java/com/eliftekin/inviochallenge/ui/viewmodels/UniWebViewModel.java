package com.eliftekin.inviochallenge.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UniWebViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading;

    public UniWebViewModel() {
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading.setValue(isLoading);
    }

}
