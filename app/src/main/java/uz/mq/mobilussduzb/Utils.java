package uz.mq.mobilussduzb;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

public class Utils {
    public static int getSDKVersion(){
        return android.os.Build.VERSION.SDK_INT;
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            return false;
        }
    }

    public static String[] getCachedData(Context context){
        SharedPreferences preferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String cache = preferences.getString("offlineData", "");
        preferences.edit().putString("offlineData", "").apply();
        return cache.split(";");
    }

    public static void addCachedData(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        String cache = preferences.getString("offlineData", "");
        if (cache.equals("")){
            preferences.edit().putString("offlineData", key).apply();
        }else{
            preferences.edit().putString("offlineData", cache+";"+key).apply();
        }
    }
}
