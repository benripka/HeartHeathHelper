package com.example.hearthealthhelper;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Reading.class}, version = 1, exportSchema = false)
public abstract class ReadingDatabase extends RoomDatabase {

    private static ReadingDatabase instance;

    public abstract ReadingDao readingDao();

    public static synchronized ReadingDatabase GetInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ReadingDatabase.class, "reading_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
