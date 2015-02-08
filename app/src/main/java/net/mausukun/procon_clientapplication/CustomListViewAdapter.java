package net.mausukun.procon_clientapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mausu on 15/02/08.
 */
public class CustomListViewAdapter extends ArrayAdapter<UserData> {
    static class ViewHolder {
        ImageView imageView;
        TextView userNameView;
        TextView userIdView;
        TextView messageView;

    }

    // コンストラクタ
    public CustomListViewAdapter(Context context,int textViewResourceId, ArrayList<UserData> labelList) {
        super(context,textViewResourceId, labelList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        // Viewを再利用している場合は新たにViewを作らない
        if (view == null) {
            LayoutInflater inflater =  (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listiew_item, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.userImageView);
            TextView userNameView = (TextView)view.findViewById(R.id.useNameTextView);
            TextView userIdView = (TextView)view.findViewById(R.id.userIdTextView);
            TextView messageView = (TextView)view.findViewById(R.id.messageTextView);

            holder = new ViewHolder();
            holder.imageView = imageView;
            holder.userNameView = userNameView;
            holder.userIdView = userIdView;
            holder.messageView = messageView;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 行毎に背景色を変える
        if(position%2==0){
            holder.imageView.setBackgroundColor(Color.parseColor("#aa0000"));
        }else{
            holder.imageView.setBackgroundColor(Color.parseColor("#880000"));
        }

        UserData userData = getItem(position);
        holder.userNameView.setText(userData.getName());
        holder.userIdView.setText(Integer.toString(userData.getId()));
        holder.messageView.setText(userData.getGeoLocation().toString());

        return view;
    }
}
