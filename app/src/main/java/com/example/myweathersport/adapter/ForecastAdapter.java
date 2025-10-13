package com.example.myweathersport.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myweathersport.R;
import com.example.myweathersport.data.model.ForecastResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        private ImageView ivWeatherIcon;
        private TextView tvDay;
        private TextView tvHour;
        private TextView tvTemp;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWeatherIcon = itemView.findViewById(R.id.ivWeatherIcon);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvTemp = itemView.findViewById(R.id.tvTemp);
        }

        public void bind(ForecastResponse.ForecastItem item) {
            // Température
            tvTemp.setText(String.format(Locale.FRENCH, "%d°C", (int) item.getMain().getTemp()));

            // Icône météo
            String weatherCondition = item.getWeather().isEmpty() ? "" : item.getWeather().get(0).getMain();
            ivWeatherIcon.setImageResource(getWeatherIconResId(weatherCondition));

            // Logique pour la date et la couleur
            try {
                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date forecastDate = apiFormat.parse(item.getDt_txt());

                // Formater le jour (ex: "Lundi")
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.FRENCH);
                String dayString = dayFormat.format(forecastDate);
                tvDay.setText(dayString.substring(0, 1).toUpperCase() + dayString.substring(1));

                // Formater l'heure (ex: "15:00")
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                tvHour.setText(hourFormat.format(forecastDate));

                // Griser les heures passées
                if (forecastDate.before(new Date())) {
                    int grayColor = Color.GRAY;
                    tvDay.setTextColor(grayColor);
                    tvHour.setTextColor(grayColor);
                    tvTemp.setTextColor(grayColor);
                    ivWeatherIcon.setAlpha(0.5f); // Rend l'icône semi-transparente
                } else {
                    int blackColor = ContextCompat.getColor(itemView.getContext(), android.R.color.black);
                    tvDay.setTextColor(blackColor);
                    tvHour.setTextColor(blackColor);
                    tvTemp.setTextColor(blackColor);
                    ivWeatherIcon.setAlpha(1.0f); // Rétablit l'opacité
                }
            } catch (ParseException e) {
                tvDay.setText(item.getDt_txt());
                tvHour.setText("");
            }
        }

        private int getWeatherIconResId(String condition) {
            if (condition.equalsIgnoreCase("Clear")) return R.drawable.ic_sun;
            if (condition.equalsIgnoreCase("Clouds")) return R.drawable.ic_cloud;
            if (condition.equalsIgnoreCase("Rain")) return R.drawable.ic_rain;
            if (condition.equalsIgnoreCase("Snow")) return R.drawable.ic_snow;
            return R.drawable.ic_cloud; // Icône par défaut
        }
    }
}