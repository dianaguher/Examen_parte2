package com.example.examen_parte2;


import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

@Database(entities = {Lot.class, History.class}, version = 5, exportSchema = false)
public abstract class LotRoomDatabase extends RoomDatabase {

    public abstract LotDao lotDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile LotRoomDatabase INSTANCE;

    static LotRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LotRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LotRoomDatabase.class, "lots_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            // super.onCreate(db);
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LotDao mDao;

        PopulateDbAsync(LotRoomDatabase db) {
            mDao = db.lotDao();
        }


        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();
            mDao.deleteAllHistories();

            Lot lot = new Lot("Lot 1","4","25/06/2020","photo","video");
            mDao.insert(lot);
            lot = new Lot("Lot 2","4","25/06/2020","photo","video");
            mDao.insert(lot);
            lot = new Lot("Lot 3","4","25/06/2020","photo","video");
            mDao.insert(lot);
            lot = new Lot("Lot 4","4","25/06/2020","photo","video");
            mDao.insert(lot);
            lot = new Lot("Lot 5","4","25/06/2020","photo","video");
            mDao.insert(lot);
            return null;
        }
    }

}
