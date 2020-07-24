package com.example.idlegame.utilities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.idlegame.data.Building;
import com.example.idlegame.data.BuildingRepository;
import com.example.idlegame.data.Resource;
import com.example.idlegame.data.ResourceRepository;

import java.util.List;

public class BuildingViewModel extends AndroidViewModel {

    private BuildingRepository buildingRepository;
    private LiveData<List<Building>> mAllBuilding;

    private ResourceRepository resourceRepository;
    private LiveData<List<Resource>> allResources;

    private List<Resource> resourceList;

    public BuildingViewModel(Application application){
        super(application);
        buildingRepository = new BuildingRepository(application);
        mAllBuilding = buildingRepository.getAllBuilding();

        resourceRepository = new ResourceRepository(application);
        allResources = resourceRepository.getAllResources();
    }

    public LiveData<List<Building>> getAllBuilding(){
        return mAllBuilding;
    }

    public LiveData<Building> getBuildingByName(String name){
        return buildingRepository.getBuildingByName(name);
    }

    public void update(Building building){
        buildingRepository.update(building);
    }

    public void insert(Building building){
        buildingRepository.insert(building);
    }

    public LiveData<List<Resource>> getAllResources(){
        return  allResources;
    }

    public LiveData<Resource> getResourceByName(String name){
        return resourceRepository.getResourceByName(name);
    }

    public void updateResource(Resource resource){
        resourceRepository.update(resource);
    }
}
