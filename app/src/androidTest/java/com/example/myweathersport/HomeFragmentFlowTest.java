package com.example.myweathersport;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// Imports statiques d'Espresso pour rendre le code plus lisible
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test instrumenté (UI Test) pour le flux utilisateur principal de l'application.
 * Ce test s'exécute sur un appareil ou un émulateur Android.
 *
 * Scénario testé :
 * 1. L'utilisateur ouvre l'application (HomeFragment).
 * 2. Il saisit le nom d'une ville ("Paris").
 * 3. Il clique sur "Rechercher".
 * 4. Il vérifie que les informations météo et sport s'affichent.
 * 5. Il clique sur "Voir les prévisions".
 * 6. Il vérifie qu'il a bien navigué vers l'écran des prévisions (ForecastFragment).
 */
@RunWith(AndroidJUnit4.class) // Runner JUnit4 standard pour les tests Android
@LargeTest // Indique qu'il s'agit d'un test d'interface utilisateur complet
public class HomeFragmentFlowTest {

    /**
     * Cette méthode est exécutée avant CHAQUE test (annoté @Test).
     * Son but est de mettre l'application dans un état de départ connu.
     * Ici, nous lançons l'activité principale de l'application.
     */
    @Before
    public void launchActivity() {
        // Lance MainActivity,
        // qui est le point d'entrée et héberge le NavHostFragment.
        ActivityScenario.launch(MainActivity.class);
    }

    /**
     * Le test principal qui vérifie le flux de recherche et de navigation.
     */
    @Test
    public void searchCity_and_navigateToForecast_Test() {
        // Définit la donnée de test
        String cityToTest = "Paris";

        // --- Étape 1 : Saisir le nom de la ville ---
        // On trouve la vue par son ID (R.id.etCityName)
        // On simule l'action de taper le texte "Paris"
        // On ferme le clavier pour ne pas masquer d'autres vues
        onView(withId(R.id.etCityName))
                .perform(typeText(cityToTest), closeSoftKeyboard());

        // --- Étape 2 : Cliquer sur le bouton "Rechercher" ---
        // On trouve le bouton par son ID (R.id.btnSearch)
        // On simule un clic
        onView(withId(R.id.btnSearch)).perform(click());

        // --- Étape 3 : Gérer l'attente de l'appel réseau ---
        // C'est la partie la plus délicate. Le ViewModel lance un appel réseau
        //
        // qui est asynchrone. Le test doit attendre la réponse.

        // ATTENTION : Thread.sleep() est une MAUVAISE PRATIQUE.
        // Le test est "aveugle" pendant 3 secondes et peut échouer
        // si l'API ou le réseau sont lents.
        // La bonne pratique est d'utiliser "Espresso Idling Resources"
        // pour que le test attende la fin de l'appel réseau.
        try {
            Thread.sleep(3000); // Pause de 3 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- Étape 4 : Vérifier que les résultats se sont affichés ---
        // On vérifie que le TextView (R.id.tvCityName)
        // contient bien le texte "Paris"
        onView(withId(R.id.tvCityName))
                .check(matches(withText(cityToTest)));

        // --- Étape 5 : Vérifier que les cartes sont devenues visibles ---
        // (Elles sont 'gone' au début)
        onView(withId(R.id.weatherCard)).check(matches(isDisplayed()));
        onView(withId(R.id.sportCard)).check(matches(isDisplayed()));

        // --- Étape 6 : Vérifier que le bouton de navigation est apparu ---
        // (Il est 'gone' au début)
        onView(withId(R.id.btnGoToForecast))
                .check(matches(isDisplayed()));

        // --- Étape 7 : Cliquer sur le bouton "Voir les prévisions" ---
        // On simule le clic pour naviguer vers le fragment suivant
        onView(withId(R.id.btnGoToForecast)).perform(click());

        // --- Étape 8 : Vérifier que la navigation a réussi ---
        // On vérifie qu'on est bien sur le ForecastFragment
        // en s'assurant que son RecyclerView (R.id.rvForecast) est affiché.
        onView(withId(R.id.rvForecast))
                .check(matches(isDisplayed()));
    }
}