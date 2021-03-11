package com.example.einzelbeispieltek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button buttonSerververbindung;
    private TextView textView;
    private EditText editText;

    private Button buttonQuersumme;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSerververbindung = (Button) findViewById(R.id.buttonSerververbindung);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        buttonQuersumme = (Button) findViewById(R.id.buttonQuersumme);
        textView2 = (TextView) findViewById(R.id.textView2);

        buttonSerververbindung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run();
            }
        });

        buttonQuersumme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quersummeGesamt = 0;
                int quersummeGerade = 0;
                int quersummeUngerade = 0;
                int ergebnis = 0;
                String sentence1 = editText.getText().toString();;
                int numberGesamt = Integer.parseInt(sentence1);
                int numberUngerade = Integer.parseInt(sentence1);


                while (numberGesamt > 0){
                    int lezteZiffer = numberGesamt % 10;
                    quersummeGesamt = quersummeGesamt + lezteZiffer;
                    numberGesamt = numberGesamt / 10;
                }
                while (numberUngerade > 0){
                    int lezteZiffer = numberUngerade % 10;
                    quersummeUngerade = quersummeUngerade + lezteZiffer;
                    numberUngerade = numberUngerade / 100;
                }
                quersummeGerade = quersummeGesamt - quersummeUngerade;
                ergebnis = quersummeGerade - quersummeUngerade;


                System.out.println("Gesamt: " + quersummeGesamt);
                System.out.println("Gerade: " + quersummeGerade);
                System.out.println("Ungerade: " + quersummeUngerade);
                System.out.println("Ergebnis: " + ergebnis);

                if (ergebnis % 2 == 0){
                   textView2.setText("Zahl ist gerade");
                }else {
                    textView2.setText("Zahl ist ungerade");}
            }
        });
    }
    
    public void run() {
        ExampleThread thread = new ExampleThread();
        thread.start();
    }
    class ExampleThread extends Thread{
        @Override
        public void run() {
            try {
                String sentence = editText.getText().toString();
                //String modifiedSentence;
                //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                Socket clientSocket = new Socket("se2-isys.aau.at", 53212);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //sentence = inFromUser.readLine();
                outToServer.writeBytes(sentence + '\n');
                //modifiedSentence = inFromServer.readLine();
                textView.setText(inFromServer.readLine());
                clientSocket.close();
            }
            catch (Exception Netzwerkfehler) {
                System.out.println("Error");
            }
        }
    }
}