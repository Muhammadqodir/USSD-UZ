package uz.mq.mobilussduzb;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.File;

public class DBManager {

    public static void checkForNewVersion(final Context context){
        String clientId = context.getResources().getString(R.string.client_id);
        final SharedPreferences sharedPreferences = context.getSharedPreferences("DB", Context.MODE_PRIVATE);
        final int currentVersion = sharedPreferences.getInt("version", 0);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference lastVersion = database.getReference("codes");
        final DatabaseReference lastAvailableVersion = database.getReference("clients").child(clientId).child("max_available_db_version");
        lastVersion.child("db_version_code").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                final int lastV = (int) snapshot.getValue(Integer.class);
                lastAvailableVersion.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        final int lastAV = (int) snapshot.getValue(Integer.class);
                        if (currentVersion < lastV && lastV <= lastAV){
                            Toast.makeText(context, "New DB version available", Toast.LENGTH_LONG).show();
                            downloadFile(context, sharedPreferences, lastV);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private static void downloadFile(final Context context, final SharedPreferences sharedPreferences, final int newDBVersion) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ussd-app-maker.appspot.com");
        StorageReference  islandRef = storageRef.child("codes_db.json");

        File rootPath = new File(context.getExternalCacheDir(), "ussd");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,"codes_db.json");

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                sharedPreferences.edit().putInt("version", newDBVersion).apply();
                Toast.makeText(context, "DB was updated successfully", Toast.LENGTH_LONG).show();
                ((Activity) context).startActivity(new Intent(context, MainActivity.class));
                ((Activity) context).finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }

}
