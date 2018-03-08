package com.discovr.discord.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import com.discovr.discord.model.Card
import com.discovr.discord.model.Tag

import io.reactivex.Single

@Dao
interface CardDao {

    @Query("SELECT * FROM card ORDER BY RANDOM()")
    fun findAll(): Single<List<Card>>

    @Query("SELECT * FROM card WHERE " +
            "tags NOT LIKE '%' || :discordName || '%' AND " +
            "(:isDrinkSet OR tags NOT LIKE '%' || :drinkName || '%') AND " +
            "(:isHardcoreSet OR tags NOT LIKE '%' || :hardcoreName || '%') " +
            "ORDER BY RANDOM()")
    fun findAllWithFlags(isDrinkSet: Int,
                         isHardcoreSet: Int,
                         discordName: String = Tag.DISCORD.name,
                         drinkName: String = Tag.DRINK.name,
                         hardcoreName: String = Tag.HARDCORE.name): Single<List<Card>>

    @Query("SELECT * FROM card WHERE id LIKE :id")
    fun findById(id: Int): Single<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(card: List<Card>): List<Long>

    @Update
    fun update(card: Card)

    @Delete
    fun delete(card: Card)
}
