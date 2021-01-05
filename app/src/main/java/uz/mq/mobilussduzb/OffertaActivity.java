package uz.mq.mobilussduzb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class OffertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Utils.getSDKVersion() >= 21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        WebView webView = findViewById(R.id.webView);

        webView.loadUrl("file:///android_asset/offerta.html");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

}