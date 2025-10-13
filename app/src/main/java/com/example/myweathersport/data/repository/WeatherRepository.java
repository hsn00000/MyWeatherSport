package com.example.myweathersport.data.repository;

import androidx.lifecycle.MutableLiveData;
import com.example.myweathersport.data.model.ForecastResponse;
import com.example.myweathersport.data.model.WeatherResponse;
import com.example.myweathersport.data.network.OpenWeatherService;
import com.example.myweathersport.data.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final OpenWeatherService service;
    private final String apiKey;

    // Le constructeur est maintenant vide !
    public WeatherRepository() {
        this.service = RetrofitClient.getService();
        // Il récupère la clé API sécurisée tout seul
        // Stock la clé ici
        this.apiKey = "7013e2ba6fad8cc1b0058b65eb59e5b1";
    }

    // --- Méthode pour récupérer la météo actuelle ---
    public void getCurrentWeather(String city, MutableLiveData<WeatherResponse> liveData) {
        // ... le reste de la méthode ne change pas
        service.getCurrentWeather(city, "metric", apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    // --- Méthode pour récupérer les prévisions ---
    public void getForecast(String city, MutableLiveData<ForecastResponse> liveData) {
        // ... le reste de la méthode ne change pas
        service.getForecast(city, "metric", apiKey).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful()) {
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