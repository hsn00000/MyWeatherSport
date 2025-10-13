package com.example.myweathersport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myweathersport.R;
import com.example.myweathersport.data.model.ForecastResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastResponse.ForecastItem> forecastItems = new ArrayList<>();

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastResponse.ForecastItem item = forecastItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return forecastItems.size();
    }

    public void setForecastItems(List<ForecastResponse.ForecastItem> items) {
        this.forecastItems = items;
        notifyDataSetChanged();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTemp;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTemp = itemView.findViewById(R.id.tvTemp);
        }

        public void bind(ForecastResponse.ForecastItem item) {
            tvDate.setText(item.getDt_txt());
            tvTemp.setText(String.format(Locale.FRENCH, "%d°C", (int) item.getMain().getTemp()));
        }
    }
}