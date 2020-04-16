package com.ja.szyfr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;

    Button encrypt;
    Button decode;
    Boolean clickedButtonEncrypt=false;
    Boolean clickedButtonDecode=false;

    String sentence =" ";
    long X;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);


        encrypt = (Button)findViewById(R.id.encryption);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentence = editText2.getText().toString();
                X = Long.valueOf(editText1.getText().toString());
                clickedButtonEncrypt=true;

                encryptCalculation();
                clickedButtonEncrypt=false; // po odwiedzeniu funkcji ustawiamy pierwotny stan, aby ponownie wykorzystać szyfrowanie
            }
        });

        decode = (Button)findViewById(R.id.decoding);

        decode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentence = editText2.getText().toString();
                X = Long.valueOf(editText1.getText().toString());
                clickedButtonDecode=true;

                encryptCalculation();
                clickedButtonDecode=false;
            }
        });

    }

    protected void encryptCalculation() {
        char cipherSign;
        char[] cipherArray = new char[100]; // nasza tablica obsłuży do 100 znaków
        int i;
        long a, m, c, character = 0;

        // definiujemy generator LCG

        m = 3956280000l;
        a = 1978141l;
        c = 1309l;


            for (i = 0; i < sentence.length(); i++) {
                char sign = sentence.charAt(i);


    // obliczamy kolejną liczbę pseudolosową

                X = (a * X + c) % m;

    // szyfrujemy literkę

                if ((sign >= 'A') && (sign <= 'Z')) {

                    if(clickedButtonEncrypt == true) {
                        character = 65 + (sign - 65 + X % 26) % 26;
                    }else if(clickedButtonDecode == true){
                        character = 65 + (sign - 39 - X % 26) % 26;
                    }

                } else {
                    character = 32; // w tabeli ASCII oznacza spację potrzebną do rozdzielenia wyrazów
                }

            cipherSign = (char) character;
            cipherArray[i] = cipherSign; // zapisujemy znak char do tablicy aby potem łatwo było wyświetlić zaszyfrowany tekst

            int lastIteration = sentence.length();
            lastIteration = lastIteration - 1;

            String resultText = new String(cipherArray); // zapisujemy char do string
            // wypisujemy zaszyfrowany tekst

            if (i == lastIteration) {
                System.out.println(cipherArray);
                Log.i("wynik", resultText);


                TextView text = findViewById(R.id.resultwindow); // wyświetlanie zmiennej string
                text.setText(resultText);

            }
        }
    }
}

