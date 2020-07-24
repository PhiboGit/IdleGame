package com.example.idlegame.utilities;

import com.example.idlegame.data.Resource;

import java.util.ArrayList;
import java.util.List;

public class PeriodicTask implements Runnable {

    private DataCollector dataCollector;
    private List<Resource> resourceList;

    public PeriodicTask(DataCollector dataCollector, List<Resource> resourceList){
        this.dataCollector = dataCollector;
        this.resourceList = resourceList;
    }

    @Override
    public void run() {
        for (Resource resource :
                resourceList) {
            resource.value += resource.increment;
        }

        dataCollector.resources(resourceList);
    }


}
