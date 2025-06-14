package hu.unideb.inf.cardata;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.Manifest;
import hu.unideb.inf.cardata.model.Car;
import hu.unideb.inf.cardata.util.NotificationHelper;
import hu.unideb.inf.cardata.viewmodel.CarViewModel;

public class MainActivity extends AppCompatActivity {
    private CarViewModel viewModel;
    private CarAdapter adapter;
    private Car selectedCar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CarAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CarViewModel.class);
        viewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                adapter.setCars(cars);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }


        EditText nameInput = findViewById(R.id.editTextName);
        EditText plateInput = findViewById(R.id.editTextPlate);
        EditText intervalInput = findViewById(R.id.editTextInterval);
        Button addButton = findViewById(R.id.buttonAdd);

        adapter.setOnItemClickListener(car -> {
            selectedCar = car;
            nameInput.setText(car.name);
            plateInput.setText(car.licensePlate);
            intervalInput.setText(String.valueOf(car.oilChangeIntervalDays));
        });

        addButton.setOnClickListener(v -> {
            if (selectedCar == null) {
                Car car = new Car();
                car.name = nameInput.getText().toString();
                car.licensePlate = plateInput.getText().toString();
                car.lastOilChange = System.currentTimeMillis();
                car.oilChangeIntervalDays = Integer.parseInt(intervalInput.getText().toString());

                viewModel.insert(car);
                NotificationHelper.scheduleOilChangeReminder(MainActivity.this, car);
            } else {
                selectedCar.name = nameInput.getText().toString();
                selectedCar.licensePlate = plateInput.getText().toString();
                selectedCar.oilChangeIntervalDays = Integer.parseInt(intervalInput.getText().toString());

                viewModel.update(selectedCar);
                selectedCar = null;
            }

            nameInput.setText("");
            plateInput.setText("");
            intervalInput.setText("");
        });
    }
}