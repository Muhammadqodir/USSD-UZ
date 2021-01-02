package uz.mq.mobilussduzb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.TweetViewHolder> {


    private List<item_view> tweetList = new ArrayList<>();
    Context ctx;

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.bind(tweetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public void setItems(Collection<item_view> tweets, Context ctx) {
        tweetList.addAll(tweets);
        this.ctx = ctx;
        notifyDataSetChanged();
    }

    public void clearItems() {
        tweetList.clear();
        notifyDataSetChanged();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        CardView btnParent;

        public TweetViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ussdDesc);
            textView1 = itemView.findViewById(R.id.ussdCode);
            btnParent = itemView.findViewById(R.id.btnParent);
        }

        public void bind(final item_view tweet) {
            textView.setText(tweet.getTitle());
            textView1.setText(tweet.getCode());
            textView.setTextColor(tweet.getColor());
            btnParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, ussdToCallableUri(tweet.getCode()));
                    ctx.startActivity(intent);
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
}