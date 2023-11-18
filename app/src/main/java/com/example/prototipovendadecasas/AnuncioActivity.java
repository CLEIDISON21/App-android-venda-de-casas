package com.example.prototipovendadecasas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AnuncioActivity extends AppCompatActivity {
    private Button btn_proximo;
    private Button btn_voltar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio);

        btn_proximo = findViewById(R.id.btn_proximo);
        btn_voltar_login = findViewById(R.id.btn_voltar_login);

        btn_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnuncioActivity.this, Anuncio2activity.class);
                startActivity(intent);
            }
        });
        btn_voltar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AnuncioActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}