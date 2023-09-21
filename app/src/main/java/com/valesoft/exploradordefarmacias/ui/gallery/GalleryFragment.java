package com.valesoft.exploradordefarmacias.ui.gallery;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.valesoft.exploradordefarmacias.MainActivityViewModel;
import com.valesoft.exploradordefarmacias.R;
import com.valesoft.exploradordefarmacias.databinding.FragmentGalleryBinding;
import com.valesoft.exploradordefarmacias.modelo.Farmacia;
import com.valesoft.exploradordefarmacias.ui.home.HomeViewModel;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryViewModel gvm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gvm = new ViewModelProvider(this).get(GalleryViewModel.class);
        MainActivityViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gvm.getMMapa().observe(getViewLifecycleOwner(), new Observer<GalleryViewModel.MapaActual>() {
            @Override
            public void onChanged(GalleryViewModel.MapaActual mapaActual) {
                SupportMapFragment smf = (SupportMapFragment)  getChildFragmentManager().findFragmentById(R.id.mp);
                smf.getMapAsync(mapaActual);
            }
        });

        gvm.getMLocation().observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                //binding.tvMostrar.setText(location.getLatitude()+" "+ location.getLongitude());
                gvm.SANLUIS = new LatLng(location.getLatitude(), location.getLongitude());

                for (Farmacia far : sharedViewModel.lista) {
                    if(far.getLatlon() != null){
                        gvm.ubicacionF.add(new LatLng(far.getLatlon().latitude,far.getLatlon().longitude));
                    }

                }
                gvm.MapType = sharedViewModel.tipoMapa;
                gvm.Idioma = sharedViewModel.idioma;
                gvm.obtenerMapa();
            }
        });

        gvm.lecturaPermanente();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}