package com.example.inmobiliariaderamo.ui.pagos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.modelo.Pago;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {

    private List<Pago> pagos;

    public PagosAdapter(List<Pago> pagos) {
        this.pagos = pagos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pago, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = pagos.get(position);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        holder.tvNroPago.setText("Pago N°: " + pago.getIdPago());
        holder.tvFechaPago.setText("Fecha: " + (pago.getFechaPago() != null ? formato.format(pago.getFechaPago()) : "No hay datos"));
        holder.tvImporte.setText("Importe: $" + pago.getMonto());
        holder.tvDetalle.setText("Detalle: " + pago.getDetalle());
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNroPago, tvFechaPago, tvImporte, tvDetalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNroPago = itemView.findViewById(R.id.tvNroPago);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
        }
    }
}
