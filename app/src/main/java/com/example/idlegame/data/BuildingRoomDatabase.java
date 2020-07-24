package com.example.idlegame.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Building.class}, exportSchema = false, version = 1)
public abstract class BuildingRoomDatabase extends RoomDatabase {

    public abstract BuildingDao buildingDao();

    private static volatile BuildingRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BuildingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BuildingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BuildingRoomDatabase.class, "building_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //prepopulate Database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                BuildingDao dao = INSTANCE.buildingDao();
                dao.deleteAll();

                Building building1 = new Building(1.0,0.0,0.0);
                dao.insert(building1);
                Building building2 = new Building(5.0,0.0,0.0);
                dao.insert(building2);
                Building building3 = new Building(20.0,0.0,0.0);
                dao.insert(building3);
                Building building4 = new Building(100.0,0.0,0.0);
                dao.insert(building4);
            });
        }
    };
}
