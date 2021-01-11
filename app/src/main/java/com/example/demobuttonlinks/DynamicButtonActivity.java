package com.example.demobuttonlinks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DynamicButtonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dynamic Buttons");

        // Linking view objects
        recyclerView = findViewById(R.id.recycler);
        fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        DatabaseAdapter dbAdapter = new DatabaseAdapter();

        // Setting adapter to Recycler View
        dbAdapter.getData(new DataCallback() {
            @Override
            public void onSuccess(List<ButtonModel> data) {
                adapter = new RecyclerViewAdapter(data);
                recyclerView.setAdapter(adapter);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DynamicButtonActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.add_button_dialog, viewGroup, false);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button btnCancel = view.findViewById(R.id.btnCancel);
                Button btnAdd = view.findViewById(R.id.btnAdd);
                EditText etLabel = view.findViewById(R.id.etLabel);
                EditText etLink = view.findViewById(R.id.etLink);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(etLabel.getText()) || TextUtils.isEmpty(etLink.getText())) {
                            Toast.makeText(DynamicButtonActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                        } else {
                            String label = etLabel.getText().toString();
                            String link = etLink.getText().toString();
                            if (!link.startsWith("http")) {
                                link = "https://" + link;
                            }
                            dbAdapter.addData(new ButtonModel(label, link), new Callback() {
                                @Override
                                public void onSuccess() {
                                    dbAdapter.getData(new DataCallback() {
                                        @Override
                                        public void onSuccess(List<ButtonModel> data) {
                                            adapter = new RecyclerViewAdapter(data);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    });
                                }
                            });
                            alertDialog.cancel();
                        }
                    }
                });
            }
        });
    }

    // Back arrow button functionality
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}