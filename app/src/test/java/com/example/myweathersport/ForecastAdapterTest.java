package com.example.myweathersport;

import com.example.myweathersport.adapter.ForecastAdapter;
import com.example.myweathersport.data.model.ForecastResponse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests unitaires pour le ForecastAdapter.
 */
public class ForecastAdapterTest {

    private ForecastAdapter adapter;

    @Before
    public void setUp() {
        adapter = new ForecastAdapter();
    }

    @Test
    public void getItemCount_returnsZero_forNewAdapter() {
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void getItemCount_returnsCorrectSize_afterSettingItems() {
        List<ForecastResponse.ForecastItem> items = new ArrayList<>();
        items.add(new ForecastResponse.ForecastItem()); // Ajoute un faux item
        items.add(new ForecastResponse.ForecastItem()); // Ajoute un deuxième faux item

        adapter.setForecastItems(items);

        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void setForecastItems_updatesDataCorrectly() {
        List<ForecastResponse.ForecastItem> initialItems = new ArrayList<>();
        initialItems.add(new ForecastResponse.ForecastItem());
        adapter.setForecastItems(initialItems);

        // Vérifie la taille initiale
        assertEquals(1, adapter.getItemCount());

        List<ForecastResponse.ForecastItem> newItems = new ArrayList<>();
        newItems.add(new ForecastResponse.ForecastItem());
        newItems.add(new ForecastResponse.ForecastItem());
        newItems.add(new ForecastResponse.ForecastItem());
        adapter.setForecastItems(newItems);

        // Vérifie que la taille a été mise à jour
        assertEquals(3, adapter.getItemCount());
    }
}