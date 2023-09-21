package com.valesoft.exploradordefarmacias.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.valesoft.exploradordefarmacias.MainActivityViewModel;
import com.valesoft.exploradordefarmacias.R;
import com.valesoft.exploradordefarmacias.databinding.FragmentHomeBinding;
import com.valesoft.exploradordefarmacias.modelo.Farmacia;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel hvm;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hvm = new ViewModelProvider(this).get(HomeViewModel.class);

        MainActivityViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        hvm.getLMutable().observe(getViewLifecycleOwner(), new Observer<List<Farmacia>>() {
            @Override
            public void onChanged(List<Farmacia> farmacias) {
                RecyclerView rv = binding.rvFarmacias;
                GridLayoutManager glm = new GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false);
                rv.setLayoutManager(glm);

                FarmaciaAdapter fa = new FarmaciaAdapter(farmacias, getActivity(), getLayoutInflater());
                rv.setAdapter(fa);

                sharedViewModel.lista = farmacias;

                fa.setOnItemClickListener(new FarmaciaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Farmacia farmacia) {

                        HomeFragment f = new HomeFragment();
                        Bundle bundle = new Bundle();


                        bundle.putString("Nombre", farmacia.getNombre().toString());
                        bundle.putString("Direccion", farmacia.getDireccion().toString());
                        bundle.putString("Foto", farmacia.getUrl().toString());
                        bundle.putString("Horario", farmacia.getHorario().toString());

                        f.setArguments(bundle);

                        Navigation.findNavController(getView()).navigate(R.id.nav_detalle,bundle);
                    }
                });
            }
        });

        Places.initialize(requireContext(), "AIzaSyCEsY5tpslvIsfP785y2wV69NZ2jzR1stA");
        PlacesClient placesClient = Places.createClient(requireContext());

        hvm.armarLista(placesClient);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}