package uz.mq.mobilussduzb;

import android.content.Context;
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
}
