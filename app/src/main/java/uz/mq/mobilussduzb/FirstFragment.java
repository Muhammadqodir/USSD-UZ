package uz.mq.mobilussduzb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String selComSlogan;
    private String selComTite;
    private String balans;
    private String daqida;
    private int page;
    private int selComID;
    private int selComImage;
    private int selComColor;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, DataModel model) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putInt("selComColor", model.getColor());
        args.putInt("selComImage", model.getImage());
        args.putString("selComSlogan", model.getDesc());
        args.putString("selComTite", model.getTitle());
        args.putString("daqiqa", model.getDaqida());
        args.putString("balans", model.getBalans());
        args.putInt("selComID", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        selComID = getArguments().getInt("selComID", 0);
        selComTite = getArguments().getString("selComTite");
        selComSlogan = getArguments().getString("selComSlogan");
        selComImage = getArguments().getInt("selComImage");
        selComColor = getArguments().getInt("selComColor");
        balans = getArguments().getString("balans");
        daqida = getArguments().getString("daqiqa");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_item, container, false);
        ((ImageView) view.findViewById(R.id.comLogo)).setImageResource(selComImage);
        ((TextView) view.findViewById(R.id.comTitle)).setText(selComTite);
        ((TextView) view.findViewById(R.id.comSlogan)).setText(selComSlogan);
        ((TextView) view.findViewById(R.id.comSlogan)).setText(selComSlogan);
        ((CardView) view.findViewById(R.id.btnInternet)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnService)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnSms)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnTarif)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnTime)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnUssd)).setCardBackgroundColor(selComColor);
        ((CardView) view.findViewById(R.id.btnInternet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.inter);
                Intent multiData = new Intent(getContext(), ActivityViewMultiData.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "inter");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                multiData.putExtra("targetIcon", R.drawable.ic_icons8_internet);
                startActivity(multiData);
            }
        });
        ((CardView) view.findViewById(R.id.btnService)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.xizmatlar);
                Intent multiData = new Intent(getContext(), ActivityViewMultiData.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "service");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                startActivity(multiData);
            }
        });
        ((CardView) view.findViewById(R.id.btnSms)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.smstoplam);
                Intent multiData = new Intent(getContext(), ActivityViewMultiData.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "sms");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                multiData.putExtra("targetIcon", R.drawable.ic_icons8_sms);
                startActivity(multiData);
            }
        });
        ((CardView) view.findViewById(R.id.btnTime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.daqiqatoplamlar);
                Intent multiData = new Intent(getContext(), ActivityViewMultiData.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "daqiqa");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                multiData.putExtra("targetIcon", R.drawable.ic_icons8_watch);
                startActivity(multiData);
            }
        });
        ((CardView) view.findViewById(R.id.btnTarif)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.tarif);
                Intent multiData = new Intent(getContext(), ActivityViewMultiData.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "tarif");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                startActivity(multiData);
            }
        });
        ((CardView) view.findViewById(R.id.btnUssd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actionType = getResources().getString(R.string.foydali);
                Intent multiData = new Intent(getContext(), ViewActivity.class);
                multiData.putExtra("com", selComID);
                multiData.putExtra("title", actionType);
                multiData.putExtra("type", "ussd");
                multiData.putExtra("comColor", selComColor);
                multiData.putExtra("comLogo", selComImage);
                multiData.putExtra("comTitle", selComTite);
                multiData.putExtra("comSlogan", selComSlogan);
                startActivity(multiData);
            }
        });


        ((Button) view.findViewById(R.id.btnChekBalans)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, ussdToCallableUri(balans));
                startActivity(intent);
            }
        });

        ((Button) view.findViewById(R.id.btnChekTraik)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, ussdToCallableUri(daqida));
                startActivity(intent);
            }
        });

        return view;
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