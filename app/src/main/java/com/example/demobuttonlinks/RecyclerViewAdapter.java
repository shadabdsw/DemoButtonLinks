package com.example.demobuttonlinks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<ButtonModel> data;
    DatabaseAdapter dbAdapter = new DatabaseAdapter();

    int dynamicButtonColor = Color.parseColor("#FF018786");

    public RecyclerViewAdapter(List<ButtonModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.btn_dynamic, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String label = data.get(position).getLabel();
        final String link = data.get(position).getLink();

        holder.button.setText(label);

        // Changing dynamic button color on LandingActivity
        if (label.equals("Dynamic Buttons")) {
            holder.button.setBackgroundColor(dynamicButtonColor);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (label.equals("Dynamic Buttons")) {
                    v.getContext().startActivity(new Intent(v.getContext(), DynamicButtonActivity.class));
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    v.getContext().startActivity(browserIntent);
                }
            }
        });

        holder.button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Allowing delete on long click if activity is DynamicButtonActivity
                if (v.getContext().getClass().getSimpleName().equals("DynamicButtonActivity")) {
                    Log.e("MESSAGE", v.getContext().getClass().getSimpleName());
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Warning");
                    builder.setMessage("Do you want to delete this button?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbAdapter.deleteData(data.get(position).getKey(), new Callback() {
                                @Override
                                public void onSuccess() {
                                    data.clear();
                                    dbAdapter.getData(new DataCallback() {
                                        @Override
                                        public void onSuccess(List<ButtonModel> data) {
                                            RecyclerViewAdapter.this.data = data;
                                            notifyDataSetChanged();
                                        }
                                    });
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }

}