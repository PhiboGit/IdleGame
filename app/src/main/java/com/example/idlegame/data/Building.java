package com.example.idlegame.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.idlegame.R;
import com.example.idlegame.utilities.MyConstant;

@Entity(tableName = "building_table")
public class Building {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "building_id")
    private int id;
    @ColumnInfo(name = "building_name")
    private String buildingName = "Holder-Name";
    @ColumnInfo(name = "building_image")
    private int buildingImage = R.drawable.ic_resource_apartment_24;
    @ColumnInfo(name = "building_count")
    private long buildingCount = 0;

    private double baseCostResource1;
    private double baseCostResource2;
    private double baseCostResource3;


    public Building(double baseCostResource1, double baseCostResource2, double baseCostResource3) {
        this.baseCostResource1 = baseCostResource1;
        this.baseCostResource2 = baseCostResource2;
        this.baseCostResource3 = baseCostResource3;
    }

    public void buyBuilding(){
        buildingCount++;
    }

    public double getProductionValue() {
        double baseProductionValue = 1.0;
        return buildingCount * baseProductionValue;
    }

    public double[] getBuildingCost() {
        double buildingOwned = buildingCount;
        double[] newCosts = new double[3];
        newCosts[0] = baseCostResource1 * Math.pow(MyConstant.COST_MULTIPLIER_LOW, buildingOwned);
        newCosts[1] = baseCostResource2 * Math.pow(MyConstant.COST_MULTIPLIER_MEDIUM, buildingOwned);
        newCosts[2] = baseCostResource3 * Math.pow(MyConstant.COST_MULTIPLIER_LOW, buildingOwned);
        return newCosts;
    }

    //Getter Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingImage() {
        return buildingImage;
    }

    public void setBuildingImage(int buildingImage) {
        this.buildingImage = buildingImage;
    }

    public long getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(long buildingCount) {
        this.buildingCount = buildingCount;
    }

    public double getBaseCostResource1() {
        return baseCostResource1;
    }

    public void setBaseCostResource1(double baseCostResource1) {
        this.baseCostResource1 = baseCostResource1;
    }

    public double getBaseCostResource2() {
        return baseCostResource2;
    }

    public void setBaseCostResource2(double baseCostResource2) {
        this.baseCostResource2 = baseCostResource2;
    }

    public double getBaseCostResource3() {
        return baseCostResource3;
    }

    public void setBaseCostResource3(double baseCostResource3) {
        this.baseCostResource3 = baseCostResource3;
    }
}
