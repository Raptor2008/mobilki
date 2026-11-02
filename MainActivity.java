package com.example.pliki;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText nazwaPliku;
    EditText tekstPliku;

    Button przyciskOtworz;
    Button przyciskZapisz;
    Button przyciskUsun;

    String otwartyPlik = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nazwaPliku = findViewById(R.id.nazwaPliku);
        tekstPliku = findViewById(R.id.tekstPliku);

        przyciskOtworz = findViewById(R.id.przyciskOtworz);
        przyciskZapisz = findViewById(R.id.przyciskZapisz);
        przyciskUsun = findViewById(R.id.przyciskUsun);


        przyciskOtworz.setOnClickListener(v -> {

            String nazwa = nazwaPliku.getText().toString();

            if (nazwa.isEmpty()) {
                Toast.makeText(this,
                        "Podaj nazwę pliku",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            otworzPlik(nazwa);
        });


        przyciskZapisz.setOnClickListener(v -> {

            if (otwartyPlik.isEmpty()) {

                Toast.makeText(this,
                        "Najpierw otwórz plik",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            zapiszPlik();
        });


        przyciskUsun.setOnClickListener(v -> {

            if (otwartyPlik.isEmpty()) {

                Toast.makeText(this,
                        "Najpierw otwórz plik",
                        Toast.LENGTH_SHORT).show();

                return;
            }

            usunPlik();
        });
    }


    void otworzPlik(String nazwa) {

        File plik = new File(getFilesDir(), nazwa);

        try {

            InputStream wejscie;

            // Najpierw sprawdzamy pamięć wewnętrzną
            if (plik.exists()) {

                wejscie = new FileInputStream(plik);

            } else {

                // Jeżeli nie ma pliku, próbujemy assets
                wejscie = getAssets().open(nazwa);
            }

            byte[] dane = new byte[wejscie.available()];

            wejscie.read(dane);
            wejscie.close();

            String tekst = new String(dane, "UTF-8");

            tekstPliku.setText(tekst);

            otwartyPlik = nazwa;

            Toast.makeText(this,
                    "Otwarto plik",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            Toast.makeText(this,
                    "Nie można otworzyć pliku",
                    Toast.LENGTH_SHORT).show();
        }
    }


    void zapiszPlik() {

        try {

            String tekst = tekstPliku.getText().toString();

            FileOutputStream plik =
                    openFileOutput(otwartyPlik, MODE_PRIVATE);

            plik.write(tekst.getBytes("UTF-8"));

            plik.close();

            Toast.makeText(this,
                    "Plik został zapisany",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            Toast.makeText(this,
                    "Błąd podczas zapisywania",
                    Toast.LENGTH_SHORT).show();
        }
    }


    void usunPlik() {

        try {

            File plik = new File(getFilesDir(), otwartyPlik);

            if (plik.exists()) {

                boolean usunieto = plik.delete();

                if (usunieto) {

                    tekstPliku.setText("");
                    nazwaPliku.setText("");

                    otwartyPlik = "";

                    Toast.makeText(this,
                            "Plik został usunięty",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this,
                            "Nie udało się usunąć pliku",
                            Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(this,
                        "Nie można usunąć tego pliku",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

            Toast.makeText(this,
                    "Błąd podczas usuwania pliku",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
