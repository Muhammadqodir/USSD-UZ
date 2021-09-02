package uz.mq.mobilussduzb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SelectDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dialog);

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
