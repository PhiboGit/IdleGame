package com.example.idlegame.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Resource.class}, exportSchema = false, version = 1)
public abstract class ResourceRoomDatabase  extends RoomDatabase {

    public abstract ResourceDao resourceDao();

    private static volatile ResourceRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ResourceRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ResourceRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ResourceRoomDatabase.class, "resource_database")
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
                ResourceDao dao = INSTANCE.resourceDao();
                dao.deleteAll();

                Resource resource1 = new Resource("Wood");
                Resource resource2 = new Resource("Stone");
                Resource resource3 = new Resource("Energy");


                resource1.increment = 1.0;
                resource2.increment = 0.0;


                dao.insert(resource1);
                dao.insert(resource2);
                dao.insert(resource3);
            });
        }
    };
}
