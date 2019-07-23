package com.example.passgen_android;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txvnumin;
    Button btnGenerate;
    TextView txvpassout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvnumin = findViewById(R.id.txvnumin);
        txvpassout = findViewById(R.id.txvpassout);
        btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numin = txvnumin.getText().toString();
                int passlen;
                if (numin.equals("")){
                    passlen = 10;
                    txvnumin.setText(String.valueOf(passlen));
                } else{
                    passlen =  Integer.parseInt(numin);
                    if (passlen>1024){
                        passlen = 1024;
                        txvnumin.setText(String.valueOf(passlen));
                    }
                }

                String password = randomAlphaNumeric(passlen);
                txvpassout.setText(password);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("passwordCopy", password);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        txvpassout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("passwordCopy", txvpassout.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
