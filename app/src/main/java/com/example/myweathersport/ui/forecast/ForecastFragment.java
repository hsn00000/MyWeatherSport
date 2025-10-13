package com.example.myweathersport.ui.forecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweathersport.R;
import com.example.myweathersport.adapter.ForecastAdapter;
import com.example.myweathersport.viewmodel.WeatherViewModel;

public class ForecastFragment extends Fragment {

    private WeatherViewModel viewModel;
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // On utilise requireActivity() pour partager le même ViewModel que HomeFragment
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        recyclerView = view.findViewById(R.id.rvForecast);
        adapter = new ForecastAdapter();
        recyclerView.setAdapter(adapter);

        setupObservers();

        // On demande au ViewModel de charger les prévisions pour la ville déjà recherchée
        viewModel.loadForecast();
    }

    private void setupObservers() {
        viewModel.getForecast().observe(getViewLifecycleOwner(), forecastResponse -> {
            if (forecastResponse != null && forecastResponse.getList() != null) {
                adapter.setForecastItems(forecastResponse.getList());
            } else {
                Toast.makeText(getContext(), "Erreur de prévisions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}