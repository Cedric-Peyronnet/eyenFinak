package com.example.maxi.eyen;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button register = findViewById(R.id.register);

        /** Link du bouton avec la view + création de l'évenement clic de creation d'un utilisateur **/
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               creationUser();
            }
        });
    }
    //methode pour créer un utilisateur
    private void creationUser(){
        // Les box qu'on vient récupérer pour créer un utilisateur
        EditText mail = findViewById(R.id.email);
        EditText pw = findViewById(R.id.password);
        EditText log = findViewById(R.id.login);

        ParseUser user = new ParseUser();
        String pass = convertPassMd5(pw.getText().toString());

        try{
            user.setUsername(log.getText().toString());
            user.setPassword(pass.toString());
            user.setEmail(mail.getText().toString());
           // user.signUpInBackground();
            user.logOutInBackground();
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                    }
                }
            });
            Toast.makeText(getApplicationContext(), "Votre compte a été crée ", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erreur lors de la création", Toast.LENGTH_LONG).show();
        }
    }
    //Cryptage en MD5
    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
}
