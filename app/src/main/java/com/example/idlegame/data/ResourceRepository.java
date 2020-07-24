package com.example.idlegame.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ResourceRepository {
    private ResourceDao resourceDao;
    private LiveData<List<Resource>> allResources;

    public ResourceRepository(Application application) {
        ResourceRoomDatabase db = ResourceRoomDatabase.getDatabase(application);
        resourceDao = db.resourceDao();
        allResources = resourceDao.getAllResource();
    }

    public LiveData<List<Resource>> getAllResources() {
        return allResources;
    }

    public LiveData<Resource> getResourceByName(String name) {
        return resourceDao.getResource(name);
    }

    public void update(final Resource resource){
        ResourceRoomDatabase.databaseWriteExecutor.execute(() -> resourceDao.update(resource));
    }

    public void insert(final Resource resource) {
        ResourceRoomDatabase.databaseWriteExecutor.execute(() -> resourceDao.insert(resource));
    }
}
