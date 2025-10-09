package com.example.myweathersport.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Classe qui crée une instance Retrofit pour toute l'application
public class RetrofitClient {

    // URL de base de l'API OpenWeather
    private static final String BASE_URL = "https://api.openweathermap.org/";

    // Instance unique de Retrofit
    private static Retrofit retrofit;

    // Méthode pour récupérer le service OpenWeatherService
    public static OpenWeatherService getService() {
        // On crée Retrofit si ce n'est pas déjà fait
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // URL de base pour toutes les requêtes
                    .addConverterFactory(GsonConverterFactory.create()) // Convertit le JSON en objets Java
                    .build();
        }
        // On retourne l'interface générée par Retrofit
        return retrofit.create(OpenWeatherService.class);
    }
}
