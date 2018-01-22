package com.discovr.discord.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.discovr.discord.model.Card;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CardDao {

    // TODO add random
    @Query("SELECT * FROM card")
    Single<List<Card>> getAll();

    @Query("SELECT * FROM card WHERE id LIKE :id")
    Single<Card> findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Card> card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);
}
