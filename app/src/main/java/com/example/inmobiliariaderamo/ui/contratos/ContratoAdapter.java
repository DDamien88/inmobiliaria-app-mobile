package com.example.inmobiliariaderamo.ui.contratos;

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

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {

    private Context context;
    private List<Inmueble> lista;
    private LayoutInflater inflater;

    public ContratoAdapter(Context context, List<Inmueble> lista, LayoutInflater inflater) {
        this.context = context;
        this.lista = lista;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = lista.get(position);

        holder.tvDireccion.setText(inmueble.getDireccion());
        Glide.with(context)
                .load(ApiClient.BASE_URL + inmueble.getImagen())
                .placeholder(R.drawable.inmueble)
                .into(holder.img);

        holder.btnVer.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                    .navigate(R.id.detalleContratoFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvDireccion;
        Button btnVer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgInmuebleContrato);
            tvDireccion = itemView.findViewById(R.id.tvDireccionContrato);
            btnVer = itemView.findViewById(R.id.btnVerContrato);
        }
    }
}
