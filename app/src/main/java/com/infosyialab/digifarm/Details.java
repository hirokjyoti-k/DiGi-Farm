package com.infosyialab.digifarm;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity {

    ImageView imageView;
    TextView Cname,Csoil,Ctime,Chumidity,Ctemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Cname = (TextView) findViewById(R.id.cpName);
        Csoil = (TextView) findViewById(R.id.cpsoil);
        Ctemp = (TextView) findViewById(R.id.cptemperature);
        Ctime = (TextView) findViewById(R.id.cptime);
        Chumidity = (TextView) findViewById(R.id.cphumidity);
        imageView = (ImageView) findViewById(R.id.cpimage);

        Bundle intent = getIntent().getExtras();

        Glide.with(Details.this)
                .load(intent.getString("Image"))
                .into(imageView);


        Cname.setText(intent.getString("Name"));
        Chumidity.setText(intent.getString("Humidity"));
        Ctime.setText(intent.getString("Time"));
        Csoil.setText(intent.getString("Soil"));
        Ctemp.setText(intent.getString("Temperature"));
    }
}
