package com.example.prototipovendadecasas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private EditText Edit_nome,Edit_email,Edit_senha;
    private Button btnCadstro;
    String[] mensagens = {"Preencha todos os Campos","Cadastrado com sucesso!"};
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        IniciarComponentes();
        btnCadstro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = Edit_nome.getText().toString();
                String email = Edit_email.getText().toString();
                String senha = Edit_senha.getText().toString();

                if (nome.isEmpty()|| email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(getApplicationContext(), mensagens[0], Toast.LENGTH_SHORT).show();
                    //Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    //snackbar.setBackgroundTint(Color.WHITE);
                    //snackbar.setTextColor(Color.BLACK);
                   // snackbar.show();
                }else {
                    CadastrarUsuario(v);
                }

            }
        });
    }
    public void login(View v)
    {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
    private void CadastrarUsuario(View v){

        String email = Edit_email.getText().toString();
        String senha = Edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    SalvarDadosUsuario();
                    Toast.makeText(getApplicationContext(), mensagens[1], Toast.LENGTH_SHORT).show();
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Senha deve conter no minimo 6 e maximo 10 caracteres";
                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email já cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Email invalido ,verifique!";
                    }catch (Exception e){
                        erro = "Erro ao Cadastrar Usuario";
                    }
                    Toast.makeText(getApplicationContext(), erro, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void SalvarDadosUsuario(){
        String nome = Edit_nome.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> usuarios =new HashMap<>();
        usuarios.put("nome",nome);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("db","Sucesso ao salvar os dados");
                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error","Erro ao salvar os dados" + e.toString());
                    }
                });

    }
    private void IniciarComponentes(){
        Edit_nome = findViewById(R.id.Edit_nome);
        Edit_email = findViewById(R.id.Edit_email);
        Edit_senha = findViewById(R.id.Edit_senha);
        btnCadstro = findViewById(R.id.btnCadstro);

    }
}


