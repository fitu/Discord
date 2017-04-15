package com.discovr.discord.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.discovr.discord.model.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM card")
    List<Card> getAll();

    @Query("SELECT * FROM card WHERE id LIKE :id")
    Card findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Card> card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);
}
