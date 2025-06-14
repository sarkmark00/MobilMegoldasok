package hu.unideb.inf.cardata.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hu.unideb.inf.cardata.data.AppDatabase;
import hu.unideb.inf.cardata.data.CarDao;
import hu.unideb.inf.cardata.model.Car;

public class CarRepository {
    private CarDao carDao;
    private LiveData<List<Car>> allCars;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CarRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        carDao = db.carDao();
        allCars = carDao.getAllCars();
    }

    public LiveData<List<Car>> getAllCars() {
        return allCars;
    }

    public void insert(Car car) {
        executorService.execute(() -> carDao.insert(car));
    }

    public void update(Car car) {
        executorService.execute(() -> carDao.update(car));
    }

}
