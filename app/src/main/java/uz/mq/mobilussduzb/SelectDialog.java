package uz.mq.mobilussduzb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class SelectDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dialog);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Window window = this.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        Intent data = getIntent();

        setTitle(data.getStringExtra("title"));

        String cost = data.getStringExtra("cost");
        final String title = data.getStringExtra("title");
        String value = data.getStringExtra("value");
        final String description = data.getStringExtra("description")
                .replace("*beeline_code", getString(R.string.beeline_code))
                .replace("*ucell_code", getString(R.string.ucell_code))
                .replace("*uzmobile_code", getString(R.string.uzmobile_code))
                .replace("*mobiuz_code", getString(R.string.mobiuz_code));
        final String code = data.getStringExtra("code")
                .replace("*beeline_code", getString(R.string.beeline_code))
                .replace("*ucell_code", getString(R.string.ucell_code))
                .replace("*uzmobile_code", getString(R.string.uzmobile_code))
                .replace("*mobiuz_code", getString(R.string.mobiuz_code));;

        ((TextView) findViewById(R.id.tvDescription)).setText(description);

        if (code.equals("") || code.equals("-")){
            ((Button) findViewById(R.id.btnActive)).setVisibility(View.GONE);
        }else{

            ((Button) findViewById(R.id.btnActive)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title_key = data.getStringExtra("title").replace(".", ",");
                    if (!Utils.isOnline(SelectDialog.this)){
                        Utils.addCachedData(SelectDialog.this, data.getStringExtra("comTitle") + "/" + data.getStringExtra("type") + "/" + title_key);
                    }else{
                        Log.e("ClientId", getString(R.string.client_id));
                        DatabaseReference clientStat = database.getReference("clients").child(getString(R.string.client_id)).child("stat").child(data.getStringExtra("comTitle")).child(data.getStringExtra("type"));
                        clientStat.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(title_key)){
                                    clientStat.child(title_key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            int val = (int) snapshot.getValue(Integer.class);
                                            val += 1;
                                            clientStat.child(title_key).setValue(val);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }else{
                                    clientStat.child(title_key).setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                    Intent intent = new Intent(Intent.ACTION_CALL, ussdToCallableUri(code));
                    startActivity(intent);
                }
            });

        }
        ((Button) findViewById(R.id.btnShare)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "\n\n" + title + "\n\n" + description + "\n\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                startActivity(shareIntent);
            }
        });
    }

    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {

            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }
}
