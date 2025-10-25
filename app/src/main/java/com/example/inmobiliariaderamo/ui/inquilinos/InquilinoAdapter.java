package com.example.inmobiliariaderamo.ui.inquilinos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {

    private Context context;
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public InquilinoAdapter(Context context, List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inquilino, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.tvDireccion.setText(inmueble.getDireccion());

        Glide.with(context)
                .load(ApiClient.BASE_URL + inmueble.getImagen())
                .placeholder(R.drawable.inmueble)
                .into(holder.imgInmueble);

        holder.btnVerInquilino.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idInmueble", inmueble.getIdInmueble());
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.detalleInquilinoFragment, bundle);
        });
        ;
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgInmueble;
        TextView tvDireccion;
        Button btnVerInquilino;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            btnVerInquilino = itemView.findViewById(R.id.btnVerInquilino);
        }
    }
}
