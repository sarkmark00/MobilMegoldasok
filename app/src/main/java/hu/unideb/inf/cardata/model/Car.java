package hu.unideb.inf.cardata.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Car {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String licensePlate;
    public long lastOilChange;
    public int oilChangeIntervalDays;
}