package com.example.projekt_2;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editTextLiczbaKostek;
    Button buttonLosuj;
    Button buttonRosnaco;
    Button buttonMalejaco;

    GridLayout gridKostki;
    TextView textViewWyniki;

    ArrayList<Integer> wyniki = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);

        buttonLosuj = findViewById(R.id.buttonLosuj);
        buttonRosnaco = findViewById(R.id.buttonRosnaco);
        buttonMalejaco = findViewById(R.id.buttonMalejaco);

        gridKostki = findViewById(R.id.gridKostki);
        textViewWyniki = findViewById(R.id.textViewWyniki);

        gridKostki.setColumnCount(3);

        buttonLosuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                losujKostki();
            }
        });

        buttonRosnaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(wyniki);

                pokazWyniki();
                pokazKostki();
            }
        });

        buttonMalejaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(wyniki, Collections.reverseOrder());

                pokazWyniki();
                pokazKostki();
            }
        });
    }

    public void losujKostki() {

        String tekst = editTextLiczbaKostek.getText().toString();

        if (tekst.isEmpty()) {

            Toast.makeText(this,
                    "Podaj liczbe kostek",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        int liczbaKostek = Integer.parseInt(tekst);

        if (liczbaKostek <= 0) {

            Toast.makeText(this,
                    "Liczba kostek musi byc wieksza od 0",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        wyniki.clear();

        Random losowanie = new Random();

        for (int i = 0; i < liczbaKostek; i++) {

            int liczba = losowanie.nextInt(6) + 1;

            wyniki.add(liczba);
        }

        pokazWyniki();
        pokazKostki();
    }

    public void pokazWyniki() {

        String tekst = "";

        for (int i = 0; i < wyniki.size(); i++) {

            tekst = tekst + wyniki.get(i);

            if (i < wyniki.size() - 1) {
                tekst = tekst + ", ";
            }
        }

        textViewWyniki.setText(tekst);
    }

    public void pokazKostki() {

        gridKostki.removeAllViews();

        for (int i = 0; i < wyniki.size(); i++) {

            TextView kostka = new TextView(this);

            kostka.setText(String.valueOf(wyniki.get(i)));
            kostka.setTextSize(30);
            kostka.setTextColor(Color.BLACK);
            kostka.setGravity(Gravity.CENTER);

            GridLayout.LayoutParams ustawienia = new GridLayout.LayoutParams();

            ustawienia.width = 180;
            ustawienia.height = 180;

            ustawienia.setMargins(10, 10, 10, 10);

            kostka.setLayoutParams(ustawienia);

            GradientDrawable tlo = new GradientDrawable();

            tlo.setColor(Color.WHITE);
            tlo.setStroke(4, Color.BLACK);
            tlo.setCornerRadius(20);

            kostka.setBackground(tlo);

            gridKostki.addView(kostka);
        }
    }
}
