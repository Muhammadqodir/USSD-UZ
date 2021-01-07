package uz.mq.mobilussduzb;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    FragmentPagerAdapter adapterViewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Window window;

    int sdk = android.os.Build.VERSION.SDK_INT;
    Integer[] colors;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {Manifest.permission.CALL_PHONE};

    AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActionBar();
        initViews();
        checkPermissions();
    }

    ActionBar mActionBar;
    private void setActionBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);
        mActionBar = getSupportActionBar();
        if (sdk >= 21){
            appBarLayout.setOutlineProvider(null);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_modern_menu);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }
    DrawerLayout drawer;
    ConstraintLayout clBg;
    String selComTite, selComSlogan;
    int selComImage;
    int selComColor;
    int selComID;
    ArrayList<DataModel> models;
    LinearLayout llNavHeader;
    JSONObject subjJson;
    public void initViews(){
        drawer = findViewById(R.id.drawer_layout);
        colors = new Integer[]{
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5)
        };


        final String TimeTableString = LoadData("baza.json");
        try {
            subjJson = new JSONObject(TimeTableString);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }


        models = new ArrayList<>();
        try {
            models.add(new DataModel(R.drawable.uzmobile, "UzMobile", getResources().getString(R.string.uzmobileslogan), colors[0], subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(0).getString("balansUssd"), subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(0).getString("daqiqaUssd")));
            models.add(new DataModel(R.drawable.mobiuz1, "Mobiuz", getResources().getString(R.string.mobislogan), colors[1], subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(1).getString("balansUssd"), subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(1).getString("daqiqaUssd")));
            models.add(new DataModel(R.drawable.usell, "Ucell", getResources().getString(R.string.ucellslogan), colors[2], subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(2).getString("balansUssd"), subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(2).getString("daqiqaUssd")));
            models.add(new DataModel(R.drawable.beeline, "Beeline", getResources().getString(R.string.beelineslogan), colors[3], subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(3).getString("balansUssd"), subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(3).getString("daqiqaUssd")));
            models.add(new DataModel(R.drawable.perfectum, "Perfectum", getResources().getString(R.string.perfectslogan), colors[4], subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(4).getString("balansUssd"), subjJson.getJSONObject("uz").getJSONArray("data").getJSONObject(4).getString("daqiqaUssd")));

        }catch (Exception e){

        }
        selComColor = models.get(0).getColor();
        selComImage = models.get(0).getImage();
        selComSlogan = models.get(0).getDesc();
        selComTite = models.get(0).getTitle();
        selComID = 0;
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        llNavHeader = (LinearLayout) headerLayout.findViewById(R.id.llHeaderNav);
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), models);
        vpPager.setAdapter(adapterViewPager);
        clBg = (ConstraintLayout) findViewById(R.id.mainBg);
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapterViewPager.getCount() -1) && position < (colors.length - 1)) {
                    clBg.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                    if (sdk >= 21){
                        window.setStatusBarColor((Integer) argbEvaluator.evaluate(
                                positionOffset,
                                colors[position],
                                colors[position + 1]
                                )
                        );
                        getWindow().setStatusBarColor(
                                (Integer) argbEvaluator.evaluate(
                                        positionOffset,
                                        colors[position],
                                        colors[position + 1]
                                )
                        );
                    }
                    drawer.setStatusBarBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                    mActionBar.setBackgroundDrawable(new ColorDrawable((Integer) argbEvaluator.evaluate(
                            positionOffset,
                            colors[position],
                            colors[position + 1]
                    )
                    ));
                }
            }

            @Override
            public void onPageSelected(int position) {

                selComColor = models.get(position).getColor();
                selComImage = models.get(position).getImage();
                selComSlogan = models.get(position).getDesc();
                selComTite = models.get(position).getTitle();

                selComID = position;
                llNavHeader.setBackgroundColor(models.get(position).getColor());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.action_notifications:
                //edit
                break;
        }
        return super.onOptionsItemSelected(item);
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
    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();

        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
//            case R.id.nav_info:
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/html");
//                intent.putExtra(Intent.EXTRA_EMAIL, "m.qodir777@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "USSD UZ");
//                intent.putExtra(Intent.EXTRA_TEXT, "");
//
//                startActivity(Intent.createChooser(intent, "Send Email"));
//
//                break;
            case R.id.nav_offerta:
                startActivity(new Intent(MainActivity.this, OffertaActivity.class));

                break;
            case R.id.nav_tlg:
                Intent intent_tlg = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=uzmobile_asadbek"));
                startActivity(intent_tlg);

                break;
            case R.id.nav_inst:
                Uri uri_inst = Uri.parse("https://www.instagram.com/uz_simkarta/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri_inst);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/uz_simkarta/")));
                }

                break;
//            case R.id.nav_facebook:
//
//                try {
//                    Intent intent_face = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/myussduz"));
//                    startActivity(intent_face);
//                } catch(Exception e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/myussduz")));
//                }
//
//                break;
            case R.id.nav_rate:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
//            case R.id.nav_setting:
//                selectLang();
//                break;
            case R.id.nav_share:
                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    PackageManager pm = getPackageManager();
                    ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
                    File srcFile = new File(ai.publicSourceDir);
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.setType("application/vnd.android.package-archive");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
                    startActivity(Intent.createChooser(share, "Ulashish"));
                } catch (Exception e) {
                    Log.e("ShareApp", e.getMessage());
                }

                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(MainActivity.this, "Ruxsat berilmadi", Toast.LENGTH_SHORT).show();

                        return;
                    }
                }
                // all permissions were granted

                break;
        }
    }
}