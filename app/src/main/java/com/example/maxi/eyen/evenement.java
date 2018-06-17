package com.example.maxi.eyen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * La principal class pour gérer les evenements
 */
public class evenement extends AppCompatActivity {

    private ArrayList<event> listEvent = new ArrayList<>();
    private ImageButton imageProfil;
    public static final int PICK_IMAGE = 1;
    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement);
        imageProfil = findViewById(R.id.buttonImageShow);
        TextView username = findViewById(R.id.textViewUserName);
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            String getUsername = currentUser.getUsername();
            username.setText(getUsername);
            byte[] byteArray = new byte[0];
            /** on essaye de récuper l'image sur le serveur cloud Parse **/
            try {
                ParseFile pf = currentUser.getParseFile("image");
                        if(pf != null) {
                            byteArray = pf.getData();
                            Bitmap retrivedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                            imageProfil.setImageBitmap(retrivedBitmap);
                        }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /** Methode pour rajouter une image de profil **/
            imageProfil.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                    startActivityForResult(chooserIntent, PICK_IMAGE);
                }
            });
        } else {

        }
        /** Query pour récuperer quelques informations pour afficher sur l'item layout  **/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.selectKeys(Arrays.asList("locationName", "address", "name", "city", "startDate", "endDate", "publicEvent"));
        try {
            List<ParseObject> results = query.find();
            for (int i = 0; i < results.size(); i++) {
                event ev = new event();
                ParseObject p = results.get(i);
                ev.setAddress(p.getString("locationName"));
                ev.setLocation(p.getString("address"));
                ev.setName(p.getString("name"));
                ev.setCity(p.getString("city"));
                ev.setStartDate(p.getDate("startDate"));
                ev.setEndDate(p.getDate("endDate"));
                listEvent.add(ev);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RecyclerView rv = findViewById(R.id.recyclerView);
        MyAdapter monAdapteur = new MyAdapter(listEvent);
        rv.setAdapter(monAdapteur);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /** Ajout d'une image de profil et ajout sur le serveur parse d'une image **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            Bitmap imagePick;
            try {
                imagePick = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(imagePick, imageProfil.getWidth(), imageProfil.getHeight(), false);
                imageProfil.setImageBitmap(resizedBitmap);

               ByteArrayOutputStream stream = new ByteArrayOutputStream();
               resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);

               byte[] byteArray = stream.toByteArray();

               ParseFile file = new ParseFile("image.png", byteArray);

               ParseObject Images = new ParseObject("User");
               currentUser.put("image", file);
               currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("AppInfo", "Profile pic success");

                        } else {
                            Log.i("AppInfo", "Profile pic FAIL");
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
