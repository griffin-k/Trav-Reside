package com.infogator.pk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class contacts extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 123;

    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ArrayList<ContactItem> contactList;

    // Store number waiting for permission to call
    private String pendingCallNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactList.add(new ContactItem("Police", "15", "Emergency police services", R.drawable.a));
        contactList.add(new ContactItem("Ambulance", "115", "Medical emergency services", R.drawable.b));
        contactList.add(new ContactItem("Fire Brigade", "16", "Fire emergency response", R.drawable.c));
        contactList.add(new ContactItem("Rescue", "1122", "Disaster rescue services", R.drawable.d));
        contactList.add(new ContactItem("Traffic Police", "122", "Traffic-related emergencies", R.drawable.e));
        contactList.add(new ContactItem("Women Helpline", "1099", "Support for women in distress", R.drawable.f));
        contactList.add(new ContactItem("Child Helpline", "1098", "Assistance for children in danger", R.drawable.g));
        contactList.add(new ContactItem("Disaster Management", "115", "Natural disaster response services", R.drawable.h));


        adapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(adapter);
    }

    private static class ContactItem {
        String title;
        String number;
        String description;
        int icon;

        ContactItem(String title, String number, String description, int icon) {
            this.title = title;
            this.number = number;
            this.description = description;
            this.icon = icon;
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

        private ArrayList<ContactItem> list;
        private Context context;

        ContactAdapter(ArrayList<ContactItem> list, Context context) {
            this.list = list;
            this.context = context;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView title, number, description;
            Button callMeButton;
            CardView cardView;

            ViewHolder(View view) {
                super(view);
                icon = view.findViewById(R.id.icon);
                title = view.findViewById(R.id.title);
                number = view.findViewById(R.id.number);
                description = view.findViewById(R.id.description);
                callMeButton = view.findViewById(R.id.button_call_me);
                cardView = (CardView) view;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ContactItem item = list.get(position);
            holder.title.setText(item.title);
            holder.number.setText(item.number);
            holder.description.setText(item.description);
            holder.icon.setImageResource(item.icon);

            holder.callMeButton.setOnClickListener(v -> {
                String phoneNumber = item.number;
                if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                    Toast.makeText(context, "Phone number is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // Request permission and save number for later call
                        pendingCallNumber = phoneNumber;
                        ActivityCompat.requestPermissions(contacts.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                    } else {
                        makePhoneCall(phoneNumber);
                    }
                } else {
                    // Older versions just call directly
                    makePhoneCall(phoneNumber);
                }
            });

            holder.cardView.setOnClickListener(null);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "No app found to handle the call", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pendingCallNumber != null) {
                    makePhoneCall(pendingCallNumber);
                    pendingCallNumber = null;
                }
            } else {
                Toast.makeText(this, "Permission denied to make calls", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
