package com.example.hearthealthhelper;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class ReadingDatabase_Impl extends ReadingDatabase {
  private volatile ReadingDao _readingDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `reading_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dateMeasured` INTEGER, `systolicPressure` INTEGER NOT NULL, `diastolicPressure` INTEGER NOT NULL, `heartRate` INTEGER NOT NULL, `comment` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a22a378fd6fbf3f980fd31dfd6c45948\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `reading_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsReadingTable = new HashMap<String, TableInfo.Column>(6);
        _columnsReadingTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsReadingTable.put("dateMeasured", new TableInfo.Column("dateMeasured", "INTEGER", false, 0));
        _columnsReadingTable.put("systolicPressure", new TableInfo.Column("systolicPressure", "INTEGER", true, 0));
        _columnsReadingTable.put("diastolicPressure", new TableInfo.Column("diastolicPressure", "INTEGER", true, 0));
        _columnsReadingTable.put("heartRate", new TableInfo.Column("heartRate", "INTEGER", true, 0));
        _columnsReadingTable.put("comment", new TableInfo.Column("comment", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReadingTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReadingTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReadingTable = new TableInfo("reading_table", _columnsReadingTable, _foreignKeysReadingTable, _indicesReadingTable);
        final TableInfo _existingReadingTable = TableInfo.read(_db, "reading_table");
        if (! _infoReadingTable.equals(_existingReadingTable)) {
          throw new IllegalStateException("Migration didn't properly handle reading_table(com.example.hearthealthhelper.Reading).\n"
                  + " Expected:\n" + _infoReadingTable + "\n"
                  + " Found:\n" + _existingReadingTable);
        }
      }
    }, "a22a378fd6fbf3f980fd31dfd6c45948", "090a245c18efc26797c0c78453a63b1c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "reading_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `reading_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ReadingDao readingDao() {
    if (_readingDao != null) {
      return _readingDao;
    } else {
      synchronized(this) {
        if(_readingDao == null) {
          _readingDao = new ReadingDao_Impl(this);
        }
        return _readingDao;
      }
    }
  }
}
