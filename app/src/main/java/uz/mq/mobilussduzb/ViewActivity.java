package uz.mq.mobilussduzb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView comIcon;
    TextView comTitle, comSlogan;
    LinearLayout viewParent;
    ViewAdapter viewAdapter;
    int key;
    ActionBar actionBar;
    String TimeTableString;
    public Intent selectedSub;
    String lang = "ru";
    int company;
    String trgetType = "inter";
    ScrollView scrollView;
    LinearLayout.LayoutParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        comIcon = (ImageView) findViewById(R.id.comLogo);
        comSlogan = (TextView) findViewById(R.id.comSlogan);
        comTitle = (TextView) findViewById(R.id.comTitle);
        recyclerView = (RecyclerView) findViewById(R.id.viewData);
        viewParent = (LinearLayout) findViewById(R.id.viewParent);
        Intent data = getIntent();

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
        actionBar.setElevation(0);
        scrollView = (ScrollView) findViewById(R.id.scrollRating);

        int color = data.getIntExtra("comColor", 0);
        int logo = data.getIntExtra("comLogo", 0);

        actionBar.setBackgroundDrawable(new ColorDrawable(color));

        lang = getSharedPreferences("lang", MODE_PRIVATE).getString("lang", "uz");
        if(Utils.getSDKVersion() >= 21){
            getWindow().setStatusBarColor(color);
        }
        comIcon.setImageResource(logo);

        setTitle(data.getStringExtra("actionType"));

        viewParent.setBackgroundColor(color);
        comTitle.setText(data.getStringExtra("comTitle"));
        comSlogan.setText(data.getStringExtra("comSlogan"));
        key = data.getIntExtra("comID", 0);


        selectedSub = getIntent();

        Collection<item_view> tweets = new ArrayList<>();

        TimeTableString = LoadData("baza.json");


        company = selectedSub.getIntExtra("com", 0);
        trgetType = selectedSub.getStringExtra("type");
        setTitle(selectedSub.getStringExtra("title"));

        try {
            JSONObject subjJson = new JSONObject(TimeTableString);

            JSONArray currentSessiya = subjJson.getJSONObject(lang).getJSONArray("data").getJSONObject(company).getJSONArray(trgetType);

            int i = 0;
            while (!currentSessiya.isNull(i)){

                String Title = currentSessiya.getJSONObject(i).getString("title");
                String Code = currentSessiya.getJSONObject(i).getString("code");

                tweets.add(new item_view(Title, Code, color));

                i += 1;
            }
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        scaleViewAnim(recyclerView, 0, tweets.size()*124);
        viewAdapter = new ViewAdapter();
        recyclerView.setNestedScrollingEnabled(false);
        viewAdapter.setItems(tweets, ViewActivity.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(viewAdapter);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();

                if (scrollY >= convertDpToPixel(180f, ViewActivity.this)){
                    getSupportActionBar().setElevation(18);

                }else{
                    getSupportActionBar().setElevation(0);
                }
            }
        });


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
        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }


    public void scaleViewAnim(final View view, int startDp, int scaleDp){

        ValueAnimator slideAnimator = ValueAnimator
                .ofInt( (int) convertDpToPixel(startDp ,ViewActivity.this),  (int) convertDpToPixel(scaleDp ,ViewActivity.this))
                .setDuration(300);
        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // get the value the interpolator is at
                Integer value = (Integer) animation.getAnimatedValue();
                // I'm going to set the layout's height 1:1 to the tick
                view.getLayoutParams().height = value.intValue();
                // force all layouts to see which ones are affected by
                // this layouts height change
                view.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }


    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
