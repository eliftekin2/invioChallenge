package com.eliftekin.inviochallenge.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoritesEntity.class}, version = 1)
public abstract class FavoritesDB extends RoomDatabase {

    private static FavoritesDB instance;

    public abstract FavoritesDAO favoritesDAO();

    public static synchronized FavoritesDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), FavoritesDB.class, "favoritesEntity")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
