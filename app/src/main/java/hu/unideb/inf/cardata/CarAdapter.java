package hu.unideb.inf.cardata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hu.unideb.inf.cardata.model.Car;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> cars;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return cars == null ? 0 : cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, plateView, lastChangeView;

        CarViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.textViewName);
            plateView = itemView.findViewById(R.id.textViewPlate);
            lastChangeView = itemView.findViewById(R.id.textViewLastChange);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(cars.get(getAdapterPosition()));
                }
            });
        }

        void bind(Car car) {
            nameView.setText(car.name);
            plateView.setText(car.licensePlate);

            if (car.lastOilChange > 0) {
                Instant instant = Instant.ofEpochMilli(car.lastOilChange);
                LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                lastChangeView.setText("Utolsó olajcsere: " + formatted);
            } else {
                lastChangeView.setText("Utolsó olajcsere: nincs adat");
            }
        }

    }
}
