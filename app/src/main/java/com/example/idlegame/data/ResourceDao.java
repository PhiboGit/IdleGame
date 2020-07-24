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
public interface ResourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Resource resource);

    @Query("DELETE FROM resource_table")
    void deleteAll();

    @Delete
    void deleteResource(Resource resource);

    @Query("SELECT * from resource_table WHERE name LIKE :name LIMIT 1")
    LiveData<Resource> getResource(String name);

    @Query("SELECT * from resource_table")
    LiveData<List<Resource>> getAllResource();

    @Update
    void update(Resource... resources);
}
