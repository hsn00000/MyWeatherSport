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

    // Variable pour mémoriser la ville actuellement recherchée
    private String currentCity = "";

    // Le constructeur est maintenant vide, il ne demande plus de clé API.
    public WeatherViewModel() {
        // On appelle le nouveau constructeur vide du Repository.
        repository = new WeatherRepository();
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
        this.currentCity = city; // On sauvegarde la ville pour une utilisation future (dans le ForecastFragment)
        repository.getCurrentWeather(city, currentWeather);
    }

    // La méthode pour les prévisions n'a plus besoin du nom de la ville en paramètre
    public void loadForecast() {
        if (!currentCity.isEmpty()) {
            repository.getForecast(currentCity, forecast);
        }
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