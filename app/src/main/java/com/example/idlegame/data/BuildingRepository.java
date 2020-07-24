package com.example.idlegame.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BuildingRepository {
    private BuildingDao mBuildingDao;
    private LiveData<List<Building>> mAllBuildings;

    public BuildingRepository(Application application) {
        BuildingRoomDatabase db = BuildingRoomDatabase.getDatabase(application);
        mBuildingDao = db.buildingDao();
        mAllBuildings = mBuildingDao.getAllBuilding();
    }

    public LiveData<List<Building>> getAllBuilding() {
        return mAllBuildings;
    }

    public LiveData<Building> getBuildingByName(String name) {
        return mBuildingDao.getBuilding(name);
    }

    public void update(final Building building){
        BuildingRoomDatabase.databaseWriteExecutor.execute(() -> mBuildingDao.update(building));
    }

    public void insert(final Building building) {
        BuildingRoomDatabase.databaseWriteExecutor.execute(() -> mBuildingDao.insert(building));
    }
}
