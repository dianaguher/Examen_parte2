package com.example.examen_parte2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface LotDao {

    @Query("SELECT * from lot_table ORDER BY lot ASC")
    LiveData<List<Lot>> getAlphabetizedLots();

    @Insert
    void insert(Lot lot);

    @Update
    void update(Lot lot);

    @Delete
    void delete(Lot lot);

    @Query("DELETE FROM lot_table")
    void deleteAll();

   // @Query("UPDATE lot_table SET color = :_color WHERE id = :id")
   // void update(int id, String _color);

    //histories

    @Query("SELECT * from history_table ORDER BY start ASC")
    LiveData<List<History>> getAlphabetizedHistories();

    @Insert
    void insert(History history);

    @Delete
    void delete(History history);

    @Query("DELETE FROM history_table")
    void deleteAllHistories();

   // @Query("SELECT * FROM history_table WHERE startDate LIKE :search " + "OR endDate LIKE :search")
    //LiveData<List<History>> findHistoryByDate(String search);

    @Query("SELECT * FROM history_table WHERE lotId=:lotIdParam")
    LiveData<List<History>> getHistoriesByLotId(int lotIdParam);

    @Query("SELECT * FROM history_table WHERE start=:startDateParam")
    LiveData<List<History>> getHistoriesByDate(String startDateParam);
}
