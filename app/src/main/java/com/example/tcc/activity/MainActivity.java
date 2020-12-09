package com.example.tcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tcc.R;
import com.example.tcc.helper.ConfigFirebase;
import com.example.tcc.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnEntrar;
    private ProgressBar progressLogin;

    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        verificarLogin();
    }

    //Verificar usuário logado
    public void verificarLogin(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), TelaInicial.class));
            finish();
        }
    }

    //Login do usuário
    public void Entrar(View view) {
        String email = campoEmail.getText().toString(), senha = campoSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        } else {
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            loginUsuario(usuario);
        }
    }

    public void loginUsuario(Usuario usuario){
        progressLogin.setVisibility(View.VISIBLE);
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressLogin.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), TelaInicial.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
                    progressLogin.setVisibility(View.GONE);
                }
            }
        });

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

    public void inicializarComponentes() {
        campoEmail = findViewById(R.id.edtEmailLogin);
        campoSenha = findViewById(R.id.edtSenhaLogin);
        progressLogin = findViewById(R.id.progressLogin);
    }
}