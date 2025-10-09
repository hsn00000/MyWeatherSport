// Repository → utilise le service réseau pour récupérer les données et les fournir au ViewModel.


package com.example.myweathersport.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.myweathersport.data.model.WeatherResponse;
import com.example.myweathersport.data.model.ForecastResponse;
import com.example.myweathersport.data.network.OpenWeatherService;
import com.example.myweathersport.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Repository pour récupérer les données météo
public class WeatherRepository {

    private OpenWeatherService service; // Interface Retrofit pour l'API
    private String apiKey; // Ta clé API OpenWeather

    public WeatherRepository(String apiKey) {
        this.service = RetrofitClient.getService(); // On récupère l'instance Retrofit
        this.apiKey = apiKey;
    }

    // --- Méthode pour récupérer la météo actuelle ---
    public void getCurrentWeather(String city, MutableLiveData<WeatherResponse> liveData) {
        service.getCurrentWeather(city,"metric",apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body()); // Met à jour le LiveData avec les données
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                liveData.postValue(null); // En cas d'erreur, on poste null
            }
        });
    }

    // --- Méthode pour récupérer les prévisions ---
    public void getForecast(String city, MutableLiveData<ForecastResponse> liveData) {
        service.getForecast(city,"metric",apiKey).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
