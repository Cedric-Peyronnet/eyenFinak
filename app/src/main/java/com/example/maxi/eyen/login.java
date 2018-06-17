package com.example.maxi.eyen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class login extends AppCompatActivity {
    Intent intent;
    Bundle bd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**Connexion au serveur Parse de eyenight**/
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getResources().getString(R.string.applicationId))
                .clientKey(getResources().getString(R.string.clientKey))
                .server(getResources().getString(R.string.serverName))
                .build()
        );

        /** Link du bouton avec la view + création de l'évenement clic d'enregistrement **/
        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(login.this,register.class);
                bd = new Bundle();
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
        /** Link du bouton avec la view + création de l'évenement clic de connexion **/
        Button connec = findViewById(R.id.connect);
        connec.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            connexion();
            }
        });
    }
    /** methode de connexion **/
    private void connexion(){
        EditText pw = findViewById(R.id.password);
        String pass = convertPassMd5(pw.getText().toString());
        EditText log = findViewById(R.id.login);
        String login = log.getText().toString();
        try {
            ParseUser.logIn(login, pass);
            intent = new Intent(login.this,evenement.class);
            bd = new Bundle();
            intent.putExtras(bd);
            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /** Convertisseur pour du cryptage en MD5 **/
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





