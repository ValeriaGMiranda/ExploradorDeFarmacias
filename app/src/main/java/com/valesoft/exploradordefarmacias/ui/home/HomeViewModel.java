package com.valesoft.exploradordefarmacias.ui.home;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.valesoft.exploradordefarmacias.modelo.Farmacia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<List<Farmacia>> listaMutable;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Farmacia>> getLMutable() {
        if (listaMutable == null) {
            listaMutable = new MutableLiveData<>();
        }
        return listaMutable;
    }


    public void armarLista(PlacesClient placesClient) {

        List<Farmacia> lista = new ArrayList<>();

        List placeFields = Arrays.asList(Place.Field.ID,Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHOTO_METADATAS,Place.Field.LAT_LNG);


        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields)
                .build();

        if (ActivityCompat.checkSelfPermission(this.getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        placesClient.findCurrentPlace(request).addOnSuccessListener((response) -> {
            for (PlaceLikelihood place : response.getPlaceLikelihoods()) {
                String placeId = place.getPlace().getId();

                if (place.getPlace().getTypes() != null && place.getPlace().getTypes().contains(Place.Type.PHARMACY)) {
                    obtenerHorarioApertura(placeId, placesClient, lista, place.getPlace().getName().toString(),  place.getPlace().getAddress().toString());
                }
            }

            listaMutable.setValue(lista);

        }).addOnFailureListener((exception) -> {
            Log.e("MapsActivity", "Place not found: " + exception.getMessage());
        });

    }


    private void obtenerHorarioApertura(String placeId, PlacesClient placesClient, List<Farmacia> lista, String nombre, String direccion) {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.OPENING_HOURS);

        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Obtener el horario de apertura si está disponible
            String horarioApertura = (place.getOpeningHours() != null) ? place.getOpeningHours().toString() : "Horario no disponible";
            lista.add(new Farmacia(nombre, direccion, "fasfsa",horarioApertura,place.getLatLng()));
            listaMutable.setValue(lista);
        }).addOnFailureListener((exception) -> {
            Log.e("MapsActivity", "Error al obtener el horario de apertura: " + exception.getMessage());
        });
    }

}