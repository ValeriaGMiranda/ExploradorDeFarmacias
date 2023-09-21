package com.valesoft.exploradordefarmacias.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valesoft.exploradordefarmacias.R;
import com.valesoft.exploradordefarmacias.databinding.FragmentDetalleFarmaciaBinding;
import com.valesoft.exploradordefarmacias.databinding.FragmentHomeBinding;

public class DetalleFarmaciaFragment extends Fragment {

    private DetalleFarmaciaViewModel mViewModel;
    private FragmentDetalleFarmaciaBinding binding;

    public static DetalleFarmaciaFragment newInstance() {
        return new DetalleFarmaciaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleFarmaciaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        binding.tvNombreD.setText(bundle.getString("Nombre"));
        binding.tvDireccionD.setText(bundle.getString("Direcion"));
        binding.tvHorariosD.setText(bundle.getString("Horario"));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleFarmaciaViewModel.class);
        // TODO: Use the ViewModel
    }

}