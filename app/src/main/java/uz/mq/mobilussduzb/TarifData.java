package uz.mq.mobilussduzb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class TarifData extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DataItem> objects;
    String targetType;
    String comTitle;

    TarifData(Context context, ArrayList<DataItem> products, String targetType, String comTitle) {
        ctx = context;
        objects = products;
        this.targetType = targetType;
        this.comTitle = comTitle;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.tarif_item, parent, false);
        }

        final DataItem p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.itemTitle)).setText(p.getTitle());
        if (p.getCost().equals("None")){
            ((LinearLayout) view.findViewById(R.id.summaCont)).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.itemCost)).setVisibility(View.GONE);
        }else{
            ((TextView) view.findViewById(R.id.itemCost)).setText(p.getCost());
        }
        ((TextView) view.findViewById(R.id.itemDes)).setText(p.getDescription());

        ((CardView) view.findViewById(R.id.itemCardView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent diaog = new Intent(ctx, SelectDialog.class);
                diaog.putExtra("title", p.getTitle());
                diaog.putExtra("cost", p.getCost());
                diaog.putExtra("value", p.getCost());
                diaog.putExtra("description", p.getDescription());
                diaog.putExtra("code", p.getCode());
                diaog.putExtra("type", targetType);
                diaog.putExtra("comTitle", comTitle);

                ctx.startActivity(diaog);

            }
        });

        return view;
    }

    // товар по позиции
    DataItem getProduct(int position) {
        return ((DataItem) getItem(position));
    }
}