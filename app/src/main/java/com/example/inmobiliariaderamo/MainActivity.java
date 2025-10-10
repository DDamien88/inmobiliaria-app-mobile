package com.example.inmobiliariaderamo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.inmobiliariaderamo.modelo.Propietario;
import com.example.inmobiliariaderamo.request.ApiClient;
import com.example.inmobiliariaderamo.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inmobiliariaderamo.databinding.ActivityMainBinding;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;




        //User logueado
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = ApiClient.leerToken(this);

        Call<Propietario> callPerfil = api.obtenerPerfil("Bearer " + token);
        callPerfil.enqueue(new retrofit2.Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, retrofit2.Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Propietario propietario = response.body();

                    NavigationView navigationView = binding.navView;
                    View headerView = navigationView.getHeaderView(0);
                    TextView tvNombreUsuario = headerView.findViewById(R.id.idUser);
                    TextView tvEmailUsuario = headerView.findViewById(R.id.tvEmail);

                    tvNombreUsuario.setText(propietario.getNombre() + " " + propietario.getApellido());
                    tvEmailUsuario.setText(propietario.getEmail());
                } else {
                    Log.e("MainActivity", "Error al obtener propietario: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.e("MainActivity", "Fallo de conexión: " + t.getMessage());
            }
        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmuebles)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //cerrar sesión

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Deseás cerrar la sesión actual?")
                        .setPositiveButton("Sí", (dialog, which) -> cerrarSesion())
                        .setNegativeButton("Cancelar", null)
                        .show();

                drawer.closeDrawers();
                return true;
            }
            // Dejar que NavigationUI maneje los otros items
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                binding.drawerLayout.closeDrawers();
            }
            return handled;
        });
    }

    private void cerrarSesion() {
        // Borrar token y datos del usuario
        SharedPreferences sp = getSharedPreferences("token.xml", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear(); // borra todo
        editor.apply();

        // Redirigir al LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}