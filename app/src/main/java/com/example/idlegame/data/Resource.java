package com.example.idlegame.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.idlegame.utilities.NumberConverter;

@Entity(tableName = "resource_table")
public class Resource {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "value")
    public double value;
    @ColumnInfo(name = "increment")
    public double increment;


    public Resource(String name) {
        this.name = name;
    }

    public String getValue(){
        return NumberConverter.toString(value);
    }

}
