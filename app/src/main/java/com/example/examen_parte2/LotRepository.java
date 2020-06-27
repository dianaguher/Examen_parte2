package com.example.examen_parte2;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


class LotRepository {

    private LotDao mLotDao;
    private LiveData<List<Lot>> mAllLots;
    private LiveData<List<History>> mAllHistories;

    LotRepository(Application application) {
        LotRoomDatabase db = LotRoomDatabase.getDatabase(application);
        mLotDao = db.lotDao();
        mAllLots = mLotDao.getAlphabetizedLots();
        mAllHistories = mLotDao.getAlphabetizedHistories();
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

    //histories
    LiveData<List<History>> getAllHistories() { return mAllHistories; }

    void insert(History history) { new LotRepository.insertHAsyncTask(mLotDao).execute(history); }

    void delete(History history){ new LotRepository.deleteHAsyncTask(mLotDao).execute(history); }

    void deleteAllHistories(){ new LotRepository.deleteAllHAsyncTask(mLotDao).execute(); }

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

  //********************* HISTORIES *****************************
  private static class insertHAsyncTask extends AsyncTask<History, Void, Void> {

      private LotDao mAsyncTaskDao;

      insertHAsyncTask(LotDao dao) {
          mAsyncTaskDao = dao;
      }

      @Override
      protected Void doInBackground(final History... params) {
          mAsyncTaskDao.insert(params[0]);
          return null;
      }
  }

   public static class deleteHAsyncTask extends AsyncTask<History, Void, Void> {

        private LotDao lotDao;

        deleteHAsyncTask(LotDao dao) {
            lotDao = dao;
        }

        @Override
        protected Void doInBackground(final History... params) {

            lotDao.delete(params[0]);
            return null;
        }
    }

     public static class deleteAllHAsyncTask extends AsyncTask<Void, Void, Void> {

        private LotDao lotDao;

        deleteAllHAsyncTask(LotDao dao) {
            lotDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            lotDao.deleteAllHistories();
            return null;
        }
    }
}
