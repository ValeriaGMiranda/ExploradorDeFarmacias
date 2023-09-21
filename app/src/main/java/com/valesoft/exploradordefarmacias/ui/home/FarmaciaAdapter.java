package com.valesoft.exploradordefarmacias.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valesoft.exploradordefarmacias.R;
import com.valesoft.exploradordefarmacias.modelo.Farmacia;

import java.util.List;

public class FarmaciaAdapter extends RecyclerView.Adapter<FarmaciaAdapter.ViewHolder> {

    private List<Farmacia> farmacias;
    private Context context;
    private LayoutInflater li;
    private OnItemClickListener listener;

    public FarmaciaAdapter(List<Farmacia> farmacias, Context context, LayoutInflater li) {
        this.farmacias = farmacias;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_farmacia,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombre.setText(farmacias.get(position).getNombre());
        holder.direccion.setText(farmacias.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return farmacias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombre;
        private TextView direccion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tvNombre);
            direccion = itemView.findViewById(R.id.tvDireccion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(farmacias.get(position));
                        }
                    }
                }
            });



        }
    }

    public interface OnItemClickListener {
        void onItemClick(Farmacia position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
