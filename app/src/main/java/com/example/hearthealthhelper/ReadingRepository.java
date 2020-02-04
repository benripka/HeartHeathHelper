package com.example.hearthealthhelper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReadingRepository {
    private ReadingDao readingDao;
    private LiveData<List<Reading>> allReadings;

    public ReadingRepository(Application application) {
        ReadingDatabase readingDatabase = ReadingDatabase.GetInstance(application);
        this.readingDao = readingDatabase.readingDao();
        this.allReadings = readingDao.getAllReadings();
    }

    public void insert(Reading reading) { new InsertReadingAsyncTask(readingDao).execute(reading); }
    public void delete(Reading reading) {new DeleteReadingAsyncTask(readingDao).execute(reading); }
    public void  update(Reading reading) { new UpdateReadingAsyncTask(readingDao).execute(reading); }
    public void deleteAll() { new DeleteAllReadingsAsyncTask(readingDao).execute();}
    public LiveData<List<Reading>> getAllReadings() { return allReadings; }

    private static class InsertReadingAsyncTask extends AsyncTask<Reading, Void, Void> {

        private ReadingDao readingDao;

        private InsertReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.insert(readings[0]);
            return null;
        }
    }

    private static class DeleteReadingAsyncTask extends AsyncTask<Reading, Void, Void> {
        private ReadingDao readingDao;

        public DeleteReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.delete(readings[0]);
            return null;
        }
    }

    private static class UpdateReadingAsyncTask extends  AsyncTask<Reading, Void, Void> {
        private ReadingDao readingDao;

        public UpdateReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.update(readings[0]);
            return null;
        }
    }

    private static class DeleteAllReadingsAsyncTask extends  AsyncTask<Void, Void, Void> {
        private ReadingDao readingDao;

        public DeleteAllReadingsAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            readingDao.deleteAll();
            return null;
        }
    }
}
