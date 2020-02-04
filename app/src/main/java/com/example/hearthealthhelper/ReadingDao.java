package com.example.hearthealthhelper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReadingDao {
    @Insert
    void insert(Reading reading);

    @Delete
    void delete(Reading reading);

    @Update
    void update(Reading update);

    @Query("delete from reading_table")
    void deleteAll();

    @Query("select * from reading_table")
    LiveData<List<Reading>> getAllReadings();
}
