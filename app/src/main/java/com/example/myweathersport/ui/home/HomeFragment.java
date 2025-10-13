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

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private WeatherViewModel viewModel;

    // Déclaration des vues de l'interface
    private EditText etCityName;
    private Button btnSearch;
    private Button btnGoToForecast;
    private TextView tvCityName, tvTemperature, tvWeatherDescription, tvSportName;
    private ImageView ivSport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Crée la vue du fragment à partir du fichier XML
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lie le ViewModel au cycle de vie de ce fragment
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Initialise toutes les vues en utilisant view.findViewById()
        etCityName = view.findViewById(R.id.etCityName);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnGoToForecast = view.findViewById(R.id.btnGoToForecast);
        tvCityName = view.findViewById(R.id.tvCityName);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvWeatherDescription = view.findViewById(R.id.tvWeatherDescription);
        ivSport = view.findViewById(R.id.ivSport);
        tvSportName = view.findViewById(R.id.tvSportName);

        // Définit l'action du clic sur le bouton "Rechercher"
        btnSearch.setOnClickListener(v -> {
            String city = etCityName.getText().toString();
            if (!city.trim().isEmpty()) {
                viewModel.loadCurrentWeather(city); // Demande la météo au ViewModel
            } else {
                Toast.makeText(getContext(), "Veuillez entrer une ville", Toast.LENGTH_SHORT).show();
            }
        });

        // Définit l'action du clic sur le bouton "Voir les prévisions"
        btnGoToForecast.setOnClickListener(v -> {
            // Utilise le NavController pour naviguer vers ForecastFragment
            // L'action est définie dans votre fichier nav_graph.xml
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_HomeFragment_to_ForecastFragment);
        });

        // Met en place les observateurs qui attendent les données du ViewModel
        setupObservers();
    }

    private void setupObservers() {
        // Cet observateur s'active dès que les données de la météo actuelle arrivent
        viewModel.getCurrentWeather().observe(getViewLifecycleOwner(), weatherResponse -> {
            if (weatherResponse != null) {
                // Met à jour l'interface avec les nouvelles données
                tvCityName.setText(weatherResponse.getName());
                tvTemperature.setText(String.format(Locale.FRENCH, "%d°C", (int) weatherResponse.getMain().getTemp()));

                String description = weatherResponse.getWeather().get(0).getDescription();
                tvWeatherDescription.setText(description.substring(0, 1).toUpperCase() + description.substring(1));

                // Appelle la logique du ViewModel pour obtenir la recommandation de sport
                List<Sport> recommendedSports = viewModel.recommendSports(
                        weatherResponse.getWeather().get(0).getMain(),
                        weatherResponse.getMain().getTemp(),
                        weatherResponse.getWind().getSpeed()
                );

                // Affiche le premier sport de la liste
                if (!recommendedSports.isEmpty()) {
                    Sport sport = recommendedSports.get(0);
                    tvSportName.setText(sport.getName());

                    // Utilise Glide pour charger l'image du sport
                    Glide.with(this).load(sport.getImageResId()).into(ivSport);

                    // Affiche le bouton pour voir les prévisions
                    btnGoToForecast.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(getContext(), "Ville non trouvée ou erreur réseau", Toast.LENGTH_SHORT).show();
            }
        });
    }
}