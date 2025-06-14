package hu.unideb.inf.cardata.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import hu.unideb.inf.cardata.model.Car;
import hu.unideb.inf.cardata.repository.CarRepository;

public class CarViewModel extends AndroidViewModel {
    private CarRepository repository;
    private LiveData<List<Car>> allCars;

    public CarViewModel(@NonNull Application application) {
        super(application);
        repository = new CarRepository(application);
        allCars = repository.getAllCars();
    }

    public LiveData<List<Car>> getAllCars() {
        return allCars;
    }

    public void insert(Car car) {
        repository.insert(car);
    }

    public void update(Car car) {
        repository.update(car);
    }

}