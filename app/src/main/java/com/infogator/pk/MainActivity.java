package com.infogator.pk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infogator.pk.AllTourAttractions.TourAttractionsList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView touristdestinations, touristattractions;
    ArrayList<TouristDestinations_MODEL> touristDestinations_models;
    ArrayList<TouristAttractions_MODEL> touristAttractions_models;
    TouristDestinations_ADAPTER touristDestinations_adapter;
    TouristAttractions_ADAPTER touristAttractions_adapter;
    TextView seetouristdestinations;
    Button explore, explore2;
    ImageView featuredImage;
    ImageButton phoneCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        touristdestinations = findViewById(R.id.touristdestinations_recyclerview);
        touristattractions = findViewById(R.id.touristattractions_recyclerview);
        phoneCallButton = findViewById(R.id.phoneCallButton);
        explore = findViewById(R.id.exploretouristdestinations_button);
        explore2 = findViewById(R.id.exploretouristdestinations_button2);
        featuredImage = findViewById(R.id.featuredImage);
        seetouristdestinations = findViewById(R.id.seetouristdestinations_link);

        // Setup tourist destinations list
        touristDestinations_models = new ArrayList<>();
        touristDestinations_models.add(new TouristDestinations_MODEL(R.drawable.badshahi, "Badshahi Mosque", "Lahore"));
        touristDestinations_models.add(new TouristDestinations_MODEL(R.drawable.swat, "Sawat", "Malakand"));
        touristDestinations_models.add(new TouristDestinations_MODEL(R.drawable.nelum, "Neelam Valley", "Azad Kashmir"));
        touristDestinations_models.add(new TouristDestinations_MODEL(R.drawable.hunza, "Hunza Valley", "Gilgit"));

        touristDestinations_adapter = new TouristDestinations_ADAPTER(this, touristDestinations_models);
        touristdestinations.setAdapter(touristDestinations_adapter);
        touristdestinations.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // Setup tourist attractions list
        touristAttractions_models = new ArrayList<>();
        touristAttractions_models.add(new TouristAttractions_MODEL(R.drawable.sindh, "Sindh", "Southeastern, Pakistan"));
        touristAttractions_models.add(new TouristAttractions_MODEL(R.drawable.punjab, "Punjab", "Eastern, Pakistan"));
        touristAttractions_models.add(new TouristAttractions_MODEL(R.drawable.blouchistan, "Balochistan", "Southwestern, Pakistan"));
        touristAttractions_models.add(new TouristAttractions_MODEL(R.drawable.kpk, "KPK", "Northwestern, Pakistan"));

        touristAttractions_adapter = new TouristAttractions_ADAPTER(this, touristAttractions_models);
        touristattractions.setAdapter(touristAttractions_adapter);
        touristattractions.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        // Set click listeners
        explore.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TourAttractionsList.class)));

        seetouristdestinations.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TourAttractionsList.class)));

        explore2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, qrcode.class)));

        featuredImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, bookings.class)));

        phoneCallButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, contacts.class)));
    }
}
