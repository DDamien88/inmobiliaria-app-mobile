package com.example.inmobiliariaderamo.ui.pagos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Pago;
import com.example.inmobiliariaderamo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> pagosLiveData = new MutableLiveData<>();

    public DetallePagosViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Pago>> getPagosLiveData() {
        return pagosLiveData;
    }



    public void cargarPagos(int idContrato) {
        String token = ApiClient.leerToken(getApplication());



        Log.d("PagosDebug", "Enviando ID de contrato: " + idContrato);

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();

        Call<List<Pago>> call = api.obtenerPagosPorContrato("Bearer " + token, idContrato);
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pagosLiveData.postValue(response.body());
                    Toast.makeText(getApplication(),  "Pagos cargados", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("PagosError" , response.message());
                    Log.d("PagosError", response.code() + "");
                    Toast.makeText(getApplication(), "No se pudieron obtener los pagos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Log.e("PagosViewModel", "Error: " + t.getMessage());
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
