package com.example.myweathersport.data.network;

// Import des modèles de données pour récupérer la météo
import com.example.myweathersport.data.model.WeatherResponse;
import com.example.myweathersport.data.model.ForecastResponse;

// Import de Retrofit
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Interface qui définit les appels à l'API OpenWeather
public interface OpenWeatherService {

    // --- Méthode pour la météo actuelle ---
    // @GET : indique l'endpoint de l'API
    // Call<WeatherResponse> : Retrofit renverra un objet WeatherResponse
    // @Query : permet d'ajouter des paramètres dans l'URL
    @GET("data/2.5/weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("q") String city,      // ville recherchée (ex: "Paris")
            @Query("units") String units, // unité (metric = °C)
            @Query("appid") String apiKey // clé API OpenWeather
    );

    // --- Méthode pour les prévisions météo ---
    // L'API retourne plusieurs prévisions pour les prochains jours
    @GET("data/2.5/forecast")
    Call<ForecastResponse> getForecast(
            @Query("q") String city,
            @Query("units") String units,
            @Query("appid") String apiKey
    );
}
