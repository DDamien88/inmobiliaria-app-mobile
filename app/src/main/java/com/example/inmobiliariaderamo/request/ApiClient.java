package com.example.inmobiliariaderamo.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.inmobiliariaderamo.R;
import com.example.inmobiliariaderamo.modelo.Contrato;
import com.example.inmobiliariaderamo.modelo.Inmueble;
import com.example.inmobiliariaderamo.modelo.Pago;
import com.example.inmobiliariaderamo.modelo.Propietario;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    public final static String BASE_URL = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";


    public static InmoServicio getInmoServicio() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoServicio.class);
    }


    public interface InmoServicio {

        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);


        @GET("api/Propietarios")
        Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarProp(@Header("Authorization") String token, @Body Propietario p);

        @FormUrlEncoded
        @POST("api/Propietarios/email")
        Call<String> resetPassword(@Field("email") String email);

        @PUT("api/Propietarios/changePassword")
        Call<Void> changePassword(
                @Header("Authorization") String token,
                @Field("currentPassword") String currentPassword,
                @Field("newPassword") String newPassword
        );

        @GET("api/Inmuebles")
        Call<List<Inmueble>> listarInmuebles(@Header("Authorization") String token);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);


        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token,
                                          @Body Inmueble inmueble);

        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> obtenerInmuebleConContrato(@Header("Authorization") String token);

        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> obtenerContratoPorInmueble(
                @Header("Authorization") String token,
                @Path("id") int idInmueble
        );

        @GET("api/pagos/contrato/{id}")
        Call<List<Pago>> obtenerPagosPorContrato(@Header("Authorization") String token,
                                                 @Path("id") int idContrato);


    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }

}