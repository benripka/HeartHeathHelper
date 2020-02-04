package com.example.hearthealthhelper;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReadingViewModel extends AndroidViewModel {

    private ReadingRepository readingRepository;
    private LiveData<List<Reading>> allReadings;

    public ReadingViewModel(Application application) {
        super(application);
        readingRepository = new ReadingRepository(application);
        allReadings = readingRepository.getAllReadings();
    }

    public void insert(Reading reading) { readingRepository.insert(reading);}
    public void delete(Reading reading) { readingRepository.delete(reading);}
    public void update(Reading reading) { readingRepository.update(reading);}
    public void deleteAll() { readingRepository.deleteAll();}
    public LiveData<List<Reading>> getAllReadings () { return readingRepository.getAllReadings();}
}
