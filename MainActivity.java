package com.example.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText imie, nazwisko, email, haslo;
    Button rejestracja;
    TextView komunikat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imie = findViewById(R.id.imie);
        nazwisko = findViewById(R.id.nazwisko);
        email = findViewById(R.id.email);
        haslo = findViewById(R.id.haslo);
        rejestracja = findViewById(R.id.rejestracja);
        komunikat = findViewById(R.id.komunikat);

        rejestracja.setOnClickListener(v -> sprawdz());
    }

    public void sprawdz() {

        String imieTekst = imie.getText().toString().trim();
        String nazwiskoTekst = nazwisko.getText().toString().trim();
        String emailTekst = email.getText().toString().trim();
        String hasloTekst = haslo.getText().toString().trim();

        // Puste pola
        if (imieTekst.isEmpty() ||
                nazwiskoTekst.isEmpty() ||
                emailTekst.isEmpty() ||
                hasloTekst.isEmpty()) {

            komunikat.setText("Uzupełnij wszystkie pola");
            return;
        }

        // Email
        if (!Patterns.EMAIL_ADDRESS.matcher(emailTekst).matches()) {
            komunikat.setText("Podaj poprawny adres email");
            return;
        }

        // Hasło
        boolean duza = false;
        boolean mala = false;
        boolean specjalny = false;

        for (int i = 0; i < hasloTekst.length(); i++) {

            char znak = hasloTekst.charAt(i);

            if (Character.isUpperCase(znak)) {
                duza = true;
            }

            if (Character.isLowerCase(znak)) {
                mala = true;
            }

            if (!Character.isLetterOrDigit(znak)) {
                specjalny = true;
            }
        }

        if (hasloTekst.length() < 8 ||
                !duza ||
                !mala ||
                !specjalny) {

            String tekst = "Hasło musi mieć:";

            if (hasloTekst.length() < 8) {
                tekst += " co najmniej 8 znaków,";
            }

            if (!duza) {
                tekst += " dużą literę,";
            }

            if (!mala) {
                tekst += " małą literę,";
            }

            if (!specjalny) {
                tekst += " znak specjalny,";
            }

            komunikat.setText(tekst);
            return;
        }

        komunikat.setText("Dane są poprawne");
    }
}
