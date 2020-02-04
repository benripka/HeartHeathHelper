package com.example.hearthealthhelper;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class ReadingDao_Impl implements ReadingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfReading;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfReading;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfReading;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ReadingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReading = new EntityInsertionAdapter<Reading>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `reading_table`(`id`,`dateMeasured`,`systolicPressure`,`diastolicPressure`,`heartRate`,`comment`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Reading value) {
        stmt.bindLong(1, value.getId());
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getDateMeasured());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindLong(3, value.getSystolicPressure());
        stmt.bindLong(4, value.getDiastolicPressure());
        stmt.bindLong(5, value.getHeartRate());
        if (value.getComment() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getComment());
        }
      }
    };
    this.__deletionAdapterOfReading = new EntityDeletionOrUpdateAdapter<Reading>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `reading_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Reading value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfReading = new EntityDeletionOrUpdateAdapter<Reading>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `reading_table` SET `id` = ?,`dateMeasured` = ?,`systolicPressure` = ?,`diastolicPressure` = ?,`heartRate` = ?,`comment` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Reading value) {
        stmt.bindLong(1, value.getId());
        final Long _tmp;
        _tmp = Converters.dateToTimestamp(value.getDateMeasured());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindLong(2, _tmp);
        }
        stmt.bindLong(3, value.getSystolicPressure());
        stmt.bindLong(4, value.getDiastolicPressure());
        stmt.bindLong(5, value.getHeartRate());
        if (value.getComment() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getComment());
        }
        stmt.bindLong(7, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from reading_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Reading reading) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReading.insert(reading);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Reading reading) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfReading.handle(reading);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Reading update) {
    __db.beginTransaction();
    try {
      __updateAdapterOfReading.handle(update);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Reading>> getAllReadings() {
    final String _sql = "select * from reading_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Reading>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Reading> compute() {
        if (_observer == null) {
          _observer = new Observer("reading_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfDateMeasured = _cursor.getColumnIndexOrThrow("dateMeasured");
          final int _cursorIndexOfSystolicPressure = _cursor.getColumnIndexOrThrow("systolicPressure");
          final int _cursorIndexOfDiastolicPressure = _cursor.getColumnIndexOrThrow("diastolicPressure");
          final int _cursorIndexOfHeartRate = _cursor.getColumnIndexOrThrow("heartRate");
          final int _cursorIndexOfComment = _cursor.getColumnIndexOrThrow("comment");
          final List<Reading> _result = new ArrayList<Reading>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Reading _item;
            final Date _tmpDateMeasured;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateMeasured)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateMeasured);
            }
            _tmpDateMeasured = Converters.fromTimestamp(_tmp);
            final int _tmpSystolicPressure;
            _tmpSystolicPressure = _cursor.getInt(_cursorIndexOfSystolicPressure);
            final int _tmpDiastolicPressure;
            _tmpDiastolicPressure = _cursor.getInt(_cursorIndexOfDiastolicPressure);
            final int _tmpHeartRate;
            _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            final String _tmpComment;
            _tmpComment = _cursor.getString(_cursorIndexOfComment);
            _item = new Reading(_tmpDateMeasured,_tmpSystolicPressure,_tmpDiastolicPressure,_tmpHeartRate,_tmpComment);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
