package com.valesoft.exploradordefarmacias.ui.gallery;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.valesoft.exploradordefarmacias.modelo.Farmacia;

import java.util.List;
import java.util.Locale;


public class GalleryViewModel extends AndroidViewModel {
    
    private Context context;
    private MutableLiveData<MapaActual> mMapa;
    private MutableLiveData<Location> mLocation;
    private FusedLocationProviderClient fused;
    public LatLng SANLUIS = new LatLng(-33.280576,-66.332482);
    public List<LatLng> ubicacionF;
    public String MapType = "MAP_TYPE_SATELLITE";

    public String Idioma = "es";


    public GalleryViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        fused = LocationServices.getFusedLocationProviderClient(context);
    }

    public LiveData<MapaActual> getMMapa(){
        if(mMapa == null){
            mMapa = new MutableLiveData<>();
        }
        return mMapa;
    }

    public void obtenerMapa(){
        MapaActual ma = new MapaActual();
        mMapa.setValue(ma);
    }

    int tipo = 0;
    public class MapaActual implements OnMapReadyCallback {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {

            switch (MapType) {
                case "MAP_TYPE_SATELLITE":
                    tipo = GoogleMap.MAP_TYPE_SATELLITE;
                    break;
                case "MAP_TYPE_NORMAL":
                    tipo = GoogleMap.MAP_TYPE_NORMAL;
                    break;
                case "MAP_TYPE_TERRAIN":
                    tipo = GoogleMap.MAP_TYPE_TERRAIN;
                    break;
                case "MAP_TYPE_HYBRID":
                    tipo = GoogleMap.MAP_TYPE_HYBRID;
                    break;
                case "MAP_TYPE_NONE":
                    tipo = GoogleMap.MAP_TYPE_NONE;
                    break;
                default:
                    tipo = GoogleMap.MAP_TYPE_SATELLITE;
            }

            googleMap.setMapType(tipo);
            googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("San Luis"));

            if(ubicacionF  != null){
                for (LatLng far : ubicacionF) {
                    if (far != null) {
                        googleMap.addMarker(new MarkerOptions().position(far).title(""));
                    }
                 }
            }

            CameraPosition campos = new CameraPosition.Builder()
                    .target(SANLUIS)
                    .zoom(19)
                    .bearing(45)
                    .tilt(70)
                    .build();

            CameraUpdate update = CameraUpdateFactory.newCameraPosition(campos);
            googleMap.animateCamera(update);
        }
    }

    public LiveData<Location> getMLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
        }
        return mLocation;
    }

    public void lecturaPermanente() {
        LocationRequest request = LocationRequest.create();//tiene informacion de cada cuanto tiempo quiero que haga lecturas y prioridad.
        request.setInterval(5000);
        request.setFastestInterval(5000);
        request.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

        LocationCallback callback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    mLocation.postValue(location);
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fused.requestLocationUpdates(request, callback, null);
    }

}