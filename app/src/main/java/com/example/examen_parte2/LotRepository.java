package com.example.examen_parte2;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


class LotRepository {

    private LotDao mLotDao;
    private LiveData<List<Lot>> mAllLots;

    LotRepository(Application application) {
        LotRoomDatabase db = LotRoomDatabase.getDatabase(application);
        mLotDao = db.lotDao();
        mAllLots = mLotDao.getAlphabetizedWords();
    }

    LiveData<List<Lot>> getAllWords() {
        return mAllLots;
    }

    void insert(Lot lot) {
        new insertAsyncTask(mLotDao).execute(lot);
    }

    void update(Lot lot){
        new updateAsyncTask(mLotDao).execute(lot);
    }

    void delete(Lot lot){ new deleteAsyncTask(mLotDao).execute(lot); }

    void deleteAll(){ new deleteAllAsyncTask(mLotDao).execute(); }

    private static class insertAsyncTask extends AsyncTask<Lot, Void, Void> {

        private LotDao mAsyncTaskDao;

        insertAsyncTask(LotDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Lot... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public static class updateAsyncTask extends AsyncTask<Lot, Void, Void> {

        private LotDao lotDao;

        updateAsyncTask(LotDao dao) {
            lotDao = dao;
        }

        @Override
        protected Void doInBackground(final Lot... params) {
            //for update
            lotDao.update(params[0]);
            return null;
        }
    }

    public static class deleteAsyncTask extends AsyncTask<Lot, Void, Void> {

        private LotDao lotDao;

        deleteAsyncTask(LotDao dao) {
            lotDao = dao;
        }

        @Override
        protected Void doInBackground(final Lot... params) {

            lotDao.delete(params[0]);
            return null;
        }
    }

    public static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private LotDao lotDao;

        deleteAllAsyncTask(LotDao dao) {
            lotDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            lotDao.deleteAll();
            return null;
        }
    }

}
