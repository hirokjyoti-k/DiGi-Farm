package com.infosyialab.digifarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout home,weather,guide, controller, profile;
    Toolbar toolbar;
    Button controllers, sensors;
    FirebaseAuth firebaseAuth;
    TextView Pump, Humidifier, Cooler, Light, Temp, Humidity, Soil;
    ToggleButton Lightbtn, Coolerbtn, Pumpbtn, Humidifierbtn;
    DatabaseReference pumpdb,lightdb,coolerdb,humidifierdb;
    WebView webView;
    String dID;
    RecyclerView recyclerView;
    List<Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = (LinearLayout) findViewById(R.id.homeUI);
        weather = (LinearLayout) findViewById(R.id.weatherUI);
        guide = (LinearLayout) findViewById(R.id.guideUI);
        controller = (LinearLayout) findViewById(R.id.controllerUI);
        profile = (LinearLayout) findViewById(R.id.profileUI);

        Pump = (TextView) findViewById(R.id.pump);
        Humidifier = (TextView) findViewById(R.id.humidifier);
        Cooler = (TextView) findViewById(R.id.cooler);
        Light = (TextView) findViewById(R.id.light);
        Temp = (TextView) findViewById(R.id.temperature);
        Humidity = (TextView) findViewById(R.id.humidity);
        Soil = (TextView) findViewById(R.id.soil);

        Coolerbtn = (ToggleButton) findViewById(R.id.coolerbtn);
        Lightbtn = (ToggleButton) findViewById(R.id.lightbtn);
        Humidifierbtn = (ToggleButton) findViewById(R.id.humidifierbthn);
        Pumpbtn = (ToggleButton) findViewById(R.id.pumpbtn);

        webView = findViewById(R.id.weather);

        controllers = (Button) findViewById(R.id.controller);
        sensors = (Button) findViewById(R.id.sensor);

        controllers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.GONE);
                controller.setVisibility(View.VISIBLE);
                gotoController();
            }
        });

        sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.VISIBLE);
                controller.setVisibility(View.GONE);
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getUserInfo();

        loadGuide();

    }

    private void getUserInfo() {

        firebaseAuth = FirebaseAuth.getInstance();
        String mailID = firebaseAuth.getCurrentUser().getEmail();
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("UserDetails");
        Query query = mref.orderByChild("Email").equalTo(mailID);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserInfo userInfo = snapshot.getValue(UserInfo.class);

                    dID = userInfo.DeviceID;

                    Temp.setText(""+userInfo.Temp);
                    Humidity.setText(""+userInfo.Humidity);
                    Soil.setText(""+userInfo.SOIL);

                    if(userInfo.PUMP == 0){
                        Pump.setText("OFF");
                    }else {
                        Pump.setText("ON");
                    }

                    if(userInfo.Humidifier == 0){
                        Humidifier.setText("OFF");
                    }else {
                        Humidifier.setText("ON");
                    }

                    if(userInfo.Cooler == 0){
                        Cooler.setText("OFF");
                    }else {
                        Cooler.setText("ON");
                    }

                    if(userInfo.LIGHT == 0){
                        Light.setText("OFF");
                    }else {
                        Light.setText("ON");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                home.setVisibility(View.VISIBLE);
                profile.setVisibility(View.GONE);
                weather.setVisibility(View.GONE);
                guide.setVisibility(View.GONE);
                controller.setVisibility(View.GONE);
                break;


            case R.id.profile:
                loadProfile();
                home.setVisibility(View.GONE);
                profile.setVisibility(View.VISIBLE);
                weather.setVisibility(View.GONE);
                guide.setVisibility(View.GONE);
                controller.setVisibility(View.GONE);
                break;

            case R.id.weather:
                loadWeather();
                home.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                weather.setVisibility(View.VISIBLE);
                guide.setVisibility(View.GONE);
                controller.setVisibility(View.GONE);
                break;


            case R.id.guide:
                home.setVisibility(View.GONE);
                profile.setVisibility(View.GONE);
                weather.setVisibility(View.GONE);
                guide.setVisibility(View.VISIBLE);
                controller.setVisibility(View.GONE);
                break;

            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void loadProfile() {


        final TextView name = (TextView) findViewById(R.id.nametxt);
        final TextView email = (TextView) findViewById(R.id.emailtxt);
        final TextView deviceId = (TextView) findViewById(R.id.deviceIdtxt);
        Button logout = (Button) findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();
        String mailID = firebaseAuth.getCurrentUser().getEmail();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("UserDetails");
        Query query = mref.orderByChild("Email").equalTo(mailID);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserInfo userInfo = snapshot.getValue(UserInfo.class);

                    name.setText(""+userInfo.Name);
                    email.setText(""+userInfo.Email);
                    deviceId.setText(""+userInfo.DeviceID);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this ,login.class );
                startActivity(intent);
                finish();
            }
        });


    }

    private void loadGuide() {

        data = new ArrayList<>();
        recyclerView  = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Guide");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Data snapshotData = snapshot.getValue(Data.class);
                    data.add(snapshotData);
                }
                recyclerView.setAdapter(new DataAdapter(MainActivity.this, data));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadWeather() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.loadUrl("https://openweathermap.org/city/1271476");
    }

    private void gotoController() {

        pumpdb = FirebaseDatabase.getInstance().getReference("UserDetails/"+dID+"/PUMP");
        coolerdb = FirebaseDatabase.getInstance().getReference("UserDetails/"+dID+"/Cooler");
        lightdb = FirebaseDatabase.getInstance().getReference("UserDetails/"+dID+"/LIGHT");
        humidifierdb = FirebaseDatabase.getInstance().getReference("UserDetails/"+dID+"/Humidifier");

        Pumpbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Pumpbtn.isChecked()){
                    pumpdb.setValue(1);
                }else {
                    pumpdb.setValue(0);
                }
            }
        });

        Coolerbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Coolerbtn.isChecked()){
                    coolerdb.setValue(1);
                }else {
                    coolerdb.setValue(0);
                }
            }
        });

        Lightbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Lightbtn.isChecked()){
                    lightdb.setValue(1);
                }else {
                    lightdb.setValue(0);
                }
            }
        });

        Humidifierbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Humidifierbtn.isChecked()){
                    humidifierdb.setValue(1);
                }else {
                    humidifierdb.setValue(0);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
