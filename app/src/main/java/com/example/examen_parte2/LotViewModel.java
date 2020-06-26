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

    private LotRepository mRepository;
    private LiveData<List<Lot>> mAllLots;

    public LotViewModel(Application application) {
        super(application);
        mRepository = new LotRepository(application);
        mAllLots = mRepository.getAllWords();
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
}
