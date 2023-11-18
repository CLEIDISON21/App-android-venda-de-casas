package com.example.prototipovendadecasas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btnCadastro;
    private EditText Edit_email, Edit_senha;
    private Button button_entrar, btnEsqueceuSenha;
    private ProgressBar progress;
    String[] mensagens = {"Preencha todos os Campos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        IniciarComponentes();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Edit_email.getText().toString();
                String senha = Edit_senha.getText().toString();
                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), mensagens[0], Toast.LENGTH_SHORT).show();
                } else {
                    AutenticarUsuario(v);
                }
            }
        });

        btnEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarRedefinicaoSenha();
            }
        });
    }

    private void AutenticarUsuario(View v) {
        String email = Edit_email.getText().toString();
        String senha = Edit_senha.getText().toString();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        }
                    }, 2000);
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = "Erro ao logar usuário";
                    }
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarRedefinicaoSenha() {
        String email = Edit_email.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Insira seu email para redefinir a senha", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Email para redefinição de senha enviado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Falha ao enviar email para redefinição de senha", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioAtual != null) {
            TelaPrincipal();
        }
    }

    private void TelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes() {
        btnCadastro = findViewById(R.id.btnCadstro);
        Edit_email = findViewById(R.id.Edit_email);
        Edit_senha = findViewById(R.id.Edit_senha);
        button_entrar = findViewById(R.id.button_entrar);
        progress = findViewById(R.id.progress);
        btnEsqueceuSenha = findViewById(R.id.btnEsqueceuSenha);
    }
}
