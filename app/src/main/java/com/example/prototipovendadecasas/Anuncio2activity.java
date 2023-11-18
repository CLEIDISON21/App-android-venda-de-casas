package com.example.prototipovendadecasas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Anuncio2activity extends AppCompatActivity {
    private Button btn_proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio2activity);


        btn_proximo = findViewById(R.id.btn_proximo);
        btn_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anuncio2activity.this,Anuncio3Activity.class);
                startActivity(intent);

            }
        });
      
    }
}