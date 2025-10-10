package com.example.inmobiliariaderamo.ui.Listar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.modelo.Inmueble;


import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {

    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmueble = listaInmuebles.get(position);

        holder.tvDireccion.setText(inmueble.getDireccion());
        holder.tvPrecio.setText("$ " + inmueble.getValor());
        holder.tvAmbientes.setText("Ambientes: " + inmueble.getAmbientes());
    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }

    public void setLista(List<Inmueble> lista) {
        this.listaInmuebles = lista;
        notifyDataSetChanged();
    }

    // ---- ViewHolder interno ----
    public static class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvPrecio, tvAmbientes;
        ImageView imgInmueble;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvAmbientes = itemView.findViewById(R.id.tvAmbientes);
            imgInmueble = itemView.findViewById(R.id.imgInmueble);
        }
    }
}
