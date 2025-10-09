package com.example.myweathersport.data.model;

import java.util.List;

public class ForecastResponse {
    private List<ForecastItem> list;

    public List<ForecastItem> getList() {
        return list;
    }

    public static class ForecastItem {
        private String dt_txt;
        private WeatherResponse.Main main;
        private List<WeatherResponse.Weather> weather;
        private WeatherResponse.Wind wind;

        public String getDt_txt() {
            return dt_txt;
        }

        public WeatherResponse.Main getMain() {
            return main;
        }

        public List<WeatherResponse.Weather> getWeather() {
            return weather;
        }

        public WeatherResponse.Wind getWind() {
            return wind;
        }
    }
}
