package com.valesoft.exploradordefarmacias.ui.salir;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valesoft.exploradordefarmacias.Dialogo;
import com.valesoft.exploradordefarmacias.R;
import com.valesoft.exploradordefarmacias.databinding.FragmentSalirBinding;

public class Salir extends Fragment {

    private FragmentSalirBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSalirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.bCerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogo.mostrarDialogo(getActivity());
            }
        });

        return root;
    }

}