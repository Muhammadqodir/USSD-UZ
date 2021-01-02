package uz.mq.mobilussduzb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ActivityViewMultiData extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<ArrayList<DataItem>> items = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    int count = 0;
    String TimeTableString;
    TabLayout tabLayout;
    public Intent selectedSub;
    String lang = "ru";
    int company;
    String trgetType = "inter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_multi_data);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedSub = getIntent();

        ActionBar actionBar = getSupportActionBar();

        int color = selectedSub.getIntExtra("comColor", 0);
        int drawable = selectedSub.getIntExtra("targetIcon", 0);

        actionBar.setBackgroundDrawable(new ColorDrawable(color));
        if (Utils.getSDKVersion() >= 21){
            getWindow().setStatusBarColor(color);
        }


        TimeTableString = LoadData("baza.json");

        lang = getSharedPreferences("lang", MODE_PRIVATE).getString("lang", "uz");

        company = selectedSub.getIntExtra("com", 0);
        trgetType = selectedSub.getStringExtra("type");
        setTitle(selectedSub.getStringExtra("title"));

        try {
            JSONObject subjJson = new JSONObject(TimeTableString);

            JSONArray currentSessiya = subjJson.getJSONObject(lang).getJSONArray("data").getJSONObject(company).getJSONArray(trgetType);

            int i = 0;
            while (!currentSessiya.isNull(i)){

                String Name = currentSessiya.getJSONObject(i).getString("name");

                titles.add(Name);

                JSONArray lessonTypes = currentSessiya.getJSONObject(i).getJSONArray("data");

                int j = 0;
                ArrayList<DataItem> itemOne = new ArrayList<>();
                while (!lessonTypes.isNull(j)){
                    String title = lessonTypes.getJSONObject(j).getString("title");
                    String cost = "None";
                    if (!lessonTypes.getJSONObject(j).isNull("summa")){
                        cost = lessonTypes.getJSONObject(j).getString("summa");
                    }
                    String description = lessonTypes.getJSONObject(j).getString("description");
                    String code = lessonTypes.getJSONObject(j).getString("code");

                    String value = "";
                    if (!lessonTypes.getJSONObject(j).isNull("ammout")){
                        value = lessonTypes.getJSONObject(j).getString("ammout");
                    }
                    itemOne.add( new DataItem(title, value, cost, description, code, drawable));
                    j += 1;
                }
                items.add(itemOne);
                i += 1;
                count = i;
            }
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        if (count == 1){
            tabLayout = findViewById(R.id.sliding_tabs);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }


        viewPager = findViewById(R.id.viewpager);

        if (trgetType.equals("tarif") || trgetType.equals("service")){
            viewPager.setAdapter(new SubFragmentAdapterTarif(getSupportFragmentManager(), ActivityViewMultiData.this, count, items, titles));
        }else {
            viewPager.setAdapter(new SubFragmentAdapter(getSupportFragmentManager(), ActivityViewMultiData.this, count, items, titles));
        }


        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setBackgroundColor(color);
        tabLayout.setupWithViewPager(viewPager);

    }
    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
