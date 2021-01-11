package com.example.demobuttonlinks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/");

    public void addData(ButtonModel data) {
        addData(data, null);
    }

    public void addData(ButtonModel data, Callback callback) {
        ref.push().setValue(data);
        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void deleteData(String key) {
        deleteData(key, null);
    }

    public void deleteData(String key, Callback callback) {
        ref.child(key).removeValue();
        if (callback != null) {
            callback.onSuccess();
        }
    }

    public List<ButtonModel> getData() {
        return getData(null);
    }

    public List<ButtonModel> getData(DataCallback callback) {
        List<ButtonModel> data = new ArrayList<ButtonModel>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ButtonModel bm = ds.getValue(ButtonModel.class);
                    bm.setKey(ds.getKey());
                    data.add(bm);
                }
                if (callback != null) {
                    callback.onSuccess(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return data;
    }

}
