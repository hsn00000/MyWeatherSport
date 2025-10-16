package com.example.myweathersport;

import com.example.myweathersport.data.model.Sport;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour le modèle Sport.
 */
public class SportTest {

    @Test
    public void sportModel_isCorrectlyConstructed() {
        String sportName = "Tennis";
        int sportImageResId = 12345; // Un ID de ressource factice

        Sport sport = new Sport(sportName, sportImageResId);

        // Vérifie que les getters retournent les valeurs passées au constructeur
        assertEquals(sportName, sport.getName());
        assertEquals(sportImageResId, sport.getImageResId());
    }
}