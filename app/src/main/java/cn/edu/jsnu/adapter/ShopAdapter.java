package cn.edu.jsnu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Shop;

public class ShopAdapter extends DrinkBaseAdapter{
    private List<Shop> listItems;
    private LayoutInflater inflater;

    public ShopAdapter(Context context, List<Shop> data) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems=data;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        final Shop shop = listItems.get(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.shop_item, null);
            holder.res_name= (TextView) convertView.findViewById(R.id.res_name);
            holder.res_bar = (RatingBar) convertView.findViewById(R.id.ratingBar1);
            holder.image = (ImageView) convertView.findViewById(R.id.res_image);
            holder.res_address = (TextView) convertView.findViewById(R.id.res_address);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();}
        holder.res_name.setText((listItems.get(position).getShopname()));
        holder.res_bar.setRating((listItems.get(position).getLevel()));
        holder.res_address.setText((listItems.get(position).getAddress()));
        // 给 ImageView 设置一个 tag
        holder.image.setTag(listItems.get(position).getPic());
        // 预设一个图片
        holder.image.setImageResource(R.drawable.error);
        // 通过 tag 来防止图片错位
        if (holder.image.getTag() != null && holder.image.getTag().equals(listItems.get(position).getPic())&&!listItems.get(position).getPic().equals("")) {
            loadImageByVolley(holder.image.getTag().toString(), holder.image);
        }
        return convertView;
    }
    class Holder {
        RatingBar res_bar;
        TextView res_name,res_address;
        ImageView image;
    }
}
