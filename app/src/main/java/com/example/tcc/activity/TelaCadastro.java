package com.example.tcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class TelaCadastro extends AppCompatActivity {

    private EditText campoEmail, campoSenha, campoConfirmaSenha;
    private ProgressBar progressCadastro;

    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        inicializarComponentes();
    }

    // Verificação de campos
    public void criarConta(View view) {
        String email = campoEmail.getText().toString(), senha = campoSenha.getText().toString(), confirmaSenha = campoConfirmaSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        } else {
            usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setConfirmaSenha(confirmaSenha);
            cadastroUsuario(usuario);
        }
    }

    // Cadastro de usuário e validações
    public void cadastroUsuario(Usuario usuario) {
        progressCadastro.setVisibility(View.VISIBLE);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getConfirmaSenha()).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressCadastro.setVisibility(View.GONE);
                            Toast.makeText(TelaCadastro.this, "Cadastro efetuado!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), TelaInicial.class));
                            finish();
                        } else {
                            progressCadastro.setVisibility(View.GONE);

                            String erro;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "Senha fraca";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "Email inválido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "Conta já cadastrada";
                            } catch (Exception e) {
                                erro = "cadastro de usuário inválido: " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(TelaCadastro.this, "" + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void inicializarComponentes() {
        campoEmail = findViewById(R.id.edtEmailCadastro);
        campoSenha = findViewById(R.id.edtSenhaCadastro);
        campoConfirmaSenha = findViewById(R.id.edtConfirmaSenhaCadastro);
        progressCadastro = findViewById(R.id.progressCadastro);
    }

    public void MainActivity(View v) {
        finish();
    }
}