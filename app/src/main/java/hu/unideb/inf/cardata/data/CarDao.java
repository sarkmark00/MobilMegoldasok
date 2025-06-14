package hu.unideb.inf.cardata.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import hu.unideb.inf.cardata.model.Car;
import java.util.List;

@Dao
public interface CarDao {
    @Insert
    void insert(hu.unideb.inf.cardata.model.Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

    @Query("SELECT * FROM Car")
    LiveData<List<Car>> getAllCars();
}