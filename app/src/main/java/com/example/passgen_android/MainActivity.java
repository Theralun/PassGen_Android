package com.example.passgen_android;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txvnumin;
    Button btnGenerate;
    TextView txvpassout;
    Switch swUpper;
    Switch swLower;
    Switch swNumerals;
    Switch swSigns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvnumin = findViewById(R.id.txvnumin);
        txvpassout = findViewById(R.id.txvpassout);
        btnGenerate = findViewById(R.id.btnGenerate);

        swUpper = findViewById(R.id.swUpper);
        swLower = findViewById(R.id.swLower);
        swNumerals = findViewById(R.id.swNums);
        swSigns = findViewById(R.id.swSigns);

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

                String pool = "";

                if (swUpper.isChecked())
                    pool += ALPHA_UPPER;

                if (swLower.isChecked())
                    pool += ALPHA_LOWER;

                if (swNumerals.isChecked())
                    pool += NUMS;

                if (swSigns.isChecked())
                    pool += SPECIALS;

                if (pool.equals(""))
                    pool = ALPHA_UPPER+ALPHA_LOWER+NUMS+SPECIALS;

                String password = randomAlphaNumeric(passlen, pool);
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


    private static final String ALPHA_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHA_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMS = "0123456789";
    private static final String SPECIALS = "-_%?!§$&~=";


    public static String randomAlphaNumeric(int count, String pool) {


        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*pool.length());
            builder.append(pool.charAt(character));
        }
        return builder.toString();
    }
}
