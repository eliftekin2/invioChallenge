package com.eliftekin.inviochallenge.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eliftekin.inviochallenge.database.FavoritesEntity;
import com.eliftekin.inviochallenge.repo.FavoritesRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesViewModel extends AndroidViewModel {

    private MutableLiveData<List<FavoritesEntity>> favoritesLiveData;
    private MutableLiveData<Boolean> isFavoritesEmpty;

    private FavoritesRepository repository;
    ExecutorService executor;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        favoritesLiveData = new MutableLiveData<>();
        isFavoritesEmpty= new MutableLiveData<>(true);

        repository = new FavoritesRepository(application);
        executor = Executors.newSingleThreadExecutor();

        setFavoritesLiveData();
    }

    private void setFavoritesLiveData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                favoritesLiveData.postValue(repository.getFavorites());

                List<FavoritesEntity> favoritesEntityList = repository.getFavorites();

                if(favoritesEntityList.isEmpty())
                    isFavoritesEmpty.postValue(true);
                else
                    isFavoritesEmpty.postValue(false);
            }
        });
    }

    public LiveData<List<FavoritesEntity>> getFavoritesList(){

        return favoritesLiveData;
    }

    public LiveData<Boolean> getIsFavoritesEmpty(){
        return isFavoritesEmpty;
    }
}
