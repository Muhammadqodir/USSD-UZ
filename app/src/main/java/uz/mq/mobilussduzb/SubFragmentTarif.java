package uz.mq.mobilussduzb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SubFragmentTarif extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    Context context;
    ArrayList<DataItem> items;
    String targetType;
    String comTitle;

    public static SubFragmentTarif newInstance(int page, String targetType, String comTitle) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("targetType", targetType);
        args.putString("comTitle", comTitle);
        SubFragmentTarif fragment = new SubFragmentTarif();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
            targetType = getArguments().getString("targetType");
            comTitle = getArguments().getString("comTitle");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_fragment, container, false);
        ListView listView = (ListView) view.findViewById(R.id.LessonType);
        ArrayList<DataItem> item = SubFragmentAdapterTarif.items.get(mPage);


        TarifData adapter = new TarifData(SubFragmentAdapterTarif.context, item, targetType, comTitle);

        listView.setAdapter(adapter);
        return view;
    }
}