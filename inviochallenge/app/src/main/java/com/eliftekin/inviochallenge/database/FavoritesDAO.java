package com.eliftekin.inviochallenge.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritesDAO {

    @Insert
    void addFavorite(FavoritesEntity favorites);

    @Query("SELECT * FROM favoritesentity ORDER BY name ASC")
    List<FavoritesEntity> getFavorites();

    @Query("DELETE FROM FavoritesEntity where name = :name")
    void removeFromDB(String name);


}
