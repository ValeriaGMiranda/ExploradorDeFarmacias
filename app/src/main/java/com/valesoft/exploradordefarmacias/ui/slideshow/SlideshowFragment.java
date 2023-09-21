package com.valesoft.exploradordefarmacias.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.valesoft.exploradordefarmacias.MainActivityViewModel;
import com.valesoft.exploradordefarmacias.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        MainActivityViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.radioButton1.setChecked(sharedViewModel.tipoMapa == "MAP_TYPE_NORMAL");
        binding.radioButton2.setChecked(sharedViewModel.tipoMapa == "MAP_TYPE_SATELLITE");
        binding.radioButton3.setChecked(sharedViewModel.tipoMapa == "MAP_TYPE_TERRAIN");
        binding.radioButton4.setChecked(sharedViewModel.tipoMapa == "MAP_TYPE_HYBRID");
        binding.radioButton5.setChecked(sharedViewModel.tipoMapa == "MAP_TYPE_NONE");
    
        binding.radioButtonA.setChecked(sharedViewModel.idioma == "es");
        binding.radioButtonB.setChecked(sharedViewModel.idioma == "en");

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "";
                String b = "";

                if(binding.radioButton1.isChecked()) a = "MAP_TYPE_NORMAL";
                if(binding.radioButton2.isChecked()) a = "MAP_TYPE_SATELLITE";
                if(binding.radioButton3.isChecked()) a = "MAP_TYPE_TERRAIN";
                if(binding.radioButton4.isChecked()) a = "MAP_TYPE_HYBRID";
                if(binding.radioButton5.isChecked()) a = "MAP_TYPE_NONE";

                if(binding.radioButtonA.isChecked()) b = "es";
                if(binding.radioButtonB.isChecked()) b = "en";


                sharedViewModel.idioma= b;
                sharedViewModel.tipoMapa = a;

                Toast.makeText(getContext(),"Cambios Realizados", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}