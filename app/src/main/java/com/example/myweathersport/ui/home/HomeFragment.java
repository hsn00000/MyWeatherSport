package com.example.myweathersport.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.bumptech.glide.Glide;
import com.example.myweathersport.R;
import com.example.myweathersport.data.model.Sport;
import com.example.myweathersport.viewmodel.WeatherViewModel;
import com.google.android.material.card.MaterialCardView; // <-- NOUVEL IMPORT

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private WeatherViewModel viewModel;

    // Déclaration des vues
    private EditText etCityName;
    private Button btnSearch, btnGoToForecast;
    private TextView tvCityName, tvTemperature, tvWeatherDescription, tvSportName;
    private ImageView ivSport, ivCurrentWeatherIcon;
    private MaterialCardView weatherCard, sportCard; // <-- DÉCLARATION DES CARTES

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        // Initialisation des vues
        etCityName = view.findViewById(R.id.etCityName);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnGoToForecast = view.findViewById(R.id.btnGoToForecast);
        tvCityName = view.findViewById(R.id.tvCityName);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvWeatherDescription = view.findViewById(R.id.tvWeatherDescription);
        ivSport = view.findViewById(R.id.ivSport);
        tvSportName = view.findViewById(R.id.tvSportName);
        ivCurrentWeatherIcon = view.findViewById(R.id.ivCurrentWeatherIcon);
        weatherCard = view.findViewById(R.id.weatherCard); // <-- INITIALISATION
        sportCard = view.findViewById(R.id.sportCard);     // <-- INITIALISATION

        // Logique des boutons
        btnSearch.setOnClickListener(v -> {
            String city = etCityName.getText().toString();
            if (!city.trim().isEmpty()) {
                viewModel.loadCurrentWeather(city);
            } else {
                Toast.makeText(getContext(), "Veuillez entrer une ville", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToForecast.setOnClickListener(v -> {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_homeFragment_to_forecastFragment);
        });

        setupObservers();
    }

    private void setupObservers() {
        viewModel.getCurrentWeather().observe(getViewLifecycleOwner(), weatherResponse -> {
            if (weatherResponse != null) {
                // Rendre les cartes visibles après la recherche
                weatherCard.setVisibility(View.VISIBLE);
                sportCard.setVisibility(View.VISIBLE);

                tvCityName.setText(weatherResponse.getName());
                tvTemperature.setText(String.format(Locale.FRENCH, "%d°C", (int) weatherResponse.getMain().getTemp()));

                String description = weatherResponse.getWeather().get(0).getDescription();
                tvWeatherDescription.setText(description.substring(0, 1).toUpperCase() + description.substring(1));

                String weatherCondition = weatherResponse.getWeather().get(0).getMain();
                ivCurrentWeatherIcon.setImageResource(getWeatherIconResId(weatherCondition));

                List<Sport> recommendedSports = viewModel.recommendSports(
                        weatherCondition,
                        weatherResponse.getMain().getTemp(),
                        weatherResponse.getWind().getSpeed()
                );

                if (!recommendedSports.isEmpty()) {
                    Sport sport = recommendedSports.get(0);
                    tvSportName.setText(sport.getName());
                    Glide.with(this).load(sport.getImageResId()).into(ivSport);
                    btnGoToForecast.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(getContext(), "Ville non trouvée ou erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getWeatherIconResId(String condition) {
        if (condition.equalsIgnoreCase("Clear")) return R.drawable.ic_sun;
        if (condition.equalsIgnoreCase("Clouds")) return R.drawable.ic_cloud;
        if (condition.equalsIgnoreCase("Rain")) return R.drawable.ic_rain;
        if (condition.equalsIgnoreCase("Snow")) return R.drawable.ic_snow;
        return R.drawable.ic_cloud; // Icône par défaut
    }
}