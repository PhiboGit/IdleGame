package com.example.idlegame.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BuildingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Building building);

    @Query("DELETE FROM building_table")
    void deleteAll();

    @Delete
    void deleteBuilding(Building building);

    @Query("SELECT * from building_table WHERE building_name LIKE :name LIMIT 1")
    LiveData<Building> getBuilding(String name);

    @Query("SELECT * from building_table")
    LiveData<List<Building>> getAllBuilding();

    @Update
    void update(Building... buildings);
}
