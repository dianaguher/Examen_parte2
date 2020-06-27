package com.example.examen_parte2;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class LotViewModel extends AndroidViewModel {

    private LiveData<List<History>> mAllHistories;
    private LotRepository mRepository;
    private LiveData<List<Lot>> mAllLots;

    public LotViewModel(Application application) {
        super(application);
        mRepository = new LotRepository(application);
        mAllLots = mRepository.getAllWords();
        mAllHistories = mRepository.getAllHistories();
    }

    LiveData<List<Lot>> getAllWords() {
        return mAllLots;
    }


    void insert(Lot lot) {
        mRepository.insert(lot);
    }

    void update(Lot lot){ mRepository.update(lot); }

    void delete(Lot lot){ mRepository.delete(lot);}

    void deleteAll(){ mRepository.deleteAll();}

    //histories
    LiveData<List<History>> getAllHistories() {
        return mAllHistories;
    }

    LiveData<List<History>> getHistoriesByDate() {
        return mAllHistories;
    }

    void insert(History history) {
        mRepository.insert(history);
    }

    void delete(History history){ mRepository.delete(history); }

    void deleteAllHistories(){ mRepository.deleteAllHistories(); }
}
