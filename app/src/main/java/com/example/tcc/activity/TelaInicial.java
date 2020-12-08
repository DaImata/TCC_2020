package com.example.tcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tcc.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static java.security.AccessController.getContext;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //Configurar bottom navigation view
        configuraBottomNavigation();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPager, new com.example.tcc.fragment.TelaInicial()).commit();
    }

    //Criação do bottom navigation view
    private void configuraBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);

        //Configs iniciais bottom navigation
        bottomNavigationViewEx.enableShiftingMode(false);

        //Habilitar navegação
        habilitarNavegacao(bottomNavigationViewEx);
    }

    //Tratamento navegação bottom navigation
    private void habilitarNavegacao(BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.ic_home:
                        fragmentTransaction.replace(R.id.viewPager, new com.example.tcc.fragment.TelaInicial()).commit();
                        return true;
                    case R.id.ic_categorias:
                        fragmentTransaction.replace(R.id.viewPager, new com.example.tcc.fragment.Categoria()).commit();
                        return true;
                    case R.id.ic_carrinho:
                        fragmentTransaction.replace(R.id.viewPager, new com.example.tcc.fragment.Carrinho()).commit();
                        return true;
                    case R.id.ic_minhaconta:
                        fragmentTransaction.replace(R.id.viewPager, new com.example.tcc.fragment.MinhaConta()).commit();
                        return true;
                }
                return false;
            }
        });
    }
}