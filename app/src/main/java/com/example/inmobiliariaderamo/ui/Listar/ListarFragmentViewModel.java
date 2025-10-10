package com.example.inmobiliariaderamo.ui.Listar;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mListaMutable;

    public ListarFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getLista() {
        if (mListaMutable == null) {
            mListaMutable = new MutableLiveData<>();
        }
        return mListaMutable;
    }

    public void cargarLista() {
        String token = ApiClient.leerToken(getApplication());

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<List<Inmueble>> call = api.listarInmuebles("Bearer " + token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    List<Inmueble> lista = response.body();
                    if (lista != null) {
                        mListaMutable.setValue(lista);
                        Toast.makeText(getApplication(), "Lista cargada correctamente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Error al obtener la lista: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ListarFragmentViewModel", "Error de conexión", t);

            }
        });
    }
}
