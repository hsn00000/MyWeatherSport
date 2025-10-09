package com.example.myweathersport.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myweathersport.R;
import com.example.myweathersport.data.model.Sport;
import com.example.myweathersport.data.model.WeatherResponse;
import com.example.myweathersport.data.model.ForecastResponse;
import com.example.myweathersport.data.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository repository;
    private MutableLiveData<WeatherResponse> currentWeather = new MutableLiveData<>();
    private MutableLiveData<ForecastResponse> forecast = new MutableLiveData<>();

    // Le constructeur reçoit la clé API pour le Repository
    public WeatherViewModel(String apiKey) {
        repository = new WeatherRepository(apiKey);
    }

    // LiveData observable pour la météo actuelle
    public LiveData<WeatherResponse> getCurrentWeather() {
        return currentWeather;
    }

    // LiveData observable pour les prévisions
    public LiveData<ForecastResponse> getForecast() {
        return forecast;
    }

    // Méthodes pour appeler le Repository
    public void loadCurrentWeather(String city) {
        repository.getCurrentWeather(city, currentWeather);
    }

    public void loadForecast(String city) {
        repository.getForecast(city, forecast);
    }

    // --- Logique pour recommander un sport selon la météo ---
    public List<Sport> recommendSports(String weatherMain, double temp, double windSpeed) {
        List<Sport> sports = new ArrayList<>();

        if(weatherMain.contains("Rain")) {
            sports.add(new Sport("Boxe", R.drawable.boxe));
            sports.add(new Sport("Ping-pong", R.drawable.pingpong));
        }
        else if(weatherMain.contains("Snow")) {
            sports.add(new Sport("Ski", R.drawable.ski));
        }
        else if(windSpeed >= 8) { // Vent fort
            sports.add(new Sport("Voile", R.drawable.voile));
            sports.add(new Sport("Kitesurf", R.drawable.kitesurf));
        }
        else if(temp >= 25) {
            sports.add(new Sport("Natation", R.drawable.natation));
            sports.add(new Sport("Running", R.drawable.running));
        }
        else if(temp >= 15) {
            sports.add(new Sport("Football", R.drawable.foot));
            sports.add(new Sport("Tennis", R.drawable.tennis));
        }
        else { // Temps froid ou autre
            sports.add(new Sport("Musculation", R.drawable.muscu));
        }

        return sports;
    }
}
