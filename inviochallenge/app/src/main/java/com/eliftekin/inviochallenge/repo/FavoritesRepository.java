package com.eliftekin.inviochallenge.repo;

import android.content.Context;

import com.eliftekin.inviochallenge.database.FavoritesDAO;
import com.eliftekin.inviochallenge.database.FavoritesDB;
import com.eliftekin.inviochallenge.database.FavoritesEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesRepository {
    private FavoritesDAO favoritesDAO;

    private ExecutorService executor;

    public FavoritesRepository(Context context){
        FavoritesDB favoritesDB = FavoritesDB.getInstance(context);
        favoritesDAO = favoritesDB.favoritesDAO();

        executor = Executors.newSingleThreadExecutor();
    }

    public void insert(FavoritesEntity favorites){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                favoritesDAO.addFavorite(favorites);
            }
        });
    }

    public void delete(String name){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                favoritesDAO.removeFromDB(name);
            }
        });
    }

    public List<FavoritesEntity> getFavorites(){
        return favoritesDAO.getFavorites();
    }

}
