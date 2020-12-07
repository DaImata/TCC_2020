package com.example.tcc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tcc.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void TelaCadastro(View v) {
        Intent intentCadastro = new Intent(MainActivity.this, TelaCadastro.class);
        startActivity(intentCadastro);
    }

    public void TelaInicial(View v) {
        Intent intentInicial = new Intent(MainActivity.this, TelaInicial.class);
        startActivity(intentInicial);
        finish();
    }
}