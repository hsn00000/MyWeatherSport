package com.example.myweathersport;

import com.example.myweathersport.data.model.Sport;
import com.example.myweathersport.viewmodel.WeatherViewModel;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Tests unitaires pour le WeatherViewModel.
 */
public class WeatherViewModelTest {

    private WeatherViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new WeatherViewModel();
    }

    @Test
    public void recommendSports_withRain_returnsIndoorSports() {
        List<Sport> sports = viewModel.recommendSports("Rain", 10.0, 5.0);
        assertNotNull(sports);
        assertEquals(2, sports.size());
        assertEquals("Boxe", sports.get(0).getName());
        assertEquals("Ping-pong", sports.get(1).getName());
    }

    @Test
    public void recommendSports_withSnow_returnsSki() {
        List<Sport> sports = viewModel.recommendSports("Snow", -2.0, 3.0);
        assertNotNull(sports);
        assertEquals(1, sports.size());
        assertEquals("Ski", sports.get(0).getName());
    }

    @Test
    public void recommendSports_withHighWind_returnsWindSports() {
        List<Sport> sports = viewModel.recommendSports("Clear", 20.0, 10.0);
        assertNotNull(sports);
        assertEquals(2, sports.size());
        assertEquals("Voile", sports.get(0).getName());
        assertEquals("Kitesurf", sports.get(1).getName());
    }

    @Test
    public void recommendSports_withHotWeather_returnsSummerSports() {
        List<Sport> sports = viewModel.recommendSports("Clear", 28.0, 4.0);
        assertNotNull(sports);
        assertEquals(2, sports.size());
        assertEquals("Natation", sports.get(0).getName());
        assertEquals("Running", sports.get(1).getName());
    }

    @Test
    public void recommendSports_withMildWeather_returnsOutdoorSports() {
        List<Sport> sports = viewModel.recommendSports("Clouds", 18.0, 3.0);
        assertNotNull(sports);
        assertEquals(2, sports.size());
        assertEquals("Football", sports.get(0).getName());
        assertEquals("Tennis", sports.get(1).getName());
    }

    @Test
    public void recommendSports_withColdWeather_returnsIndoorSport() {
        List<Sport> sports = viewModel.recommendSports("Clear", 5.0, 2.0);
        assertNotNull(sports);
        assertEquals(1, sports.size());
        assertEquals("Musculation", sports.get(0).getName());
    }
}