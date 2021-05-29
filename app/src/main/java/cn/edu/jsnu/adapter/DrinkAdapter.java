package cn.edu.jsnu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Drink;

public class DrinkAdapter extends DrinkBaseAdapter {
    private List<Drink> listItems;
    private LayoutInflater inflater;

    public DrinkAdapter(Context context, List<Drink> data) {
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
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.drink_item, null);
            holder.drink_name= (TextView) convertView.findViewById(R.id.drink_name);
            holder.drink_image = (ImageView) convertView.findViewById(R.id.drink_image);
            holder.drink_price = (TextView) convertView.findViewById(R.id.drink_price);
            holder.drink_intro= (TextView) convertView.findViewById(R.id.drink_intro);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();}
        holder.drink_name.setText(listItems.get(position).getDrinkname());
        holder.drink_intro.setText(listItems.get(position).getIntro());
        holder.drink_price.setText(String.valueOf(listItems.get(position).getPrice()) +"元");
// 给 ImageView 设置一个 tag
        holder.drink_image.setTag(listItems.get(position).getPic());
        // 预设一个图片
        holder.drink_image.setImageResource(R.drawable.error);
        // 通过 tag 来防止图片错位
        if (holder.drink_image.getTag() != null && holder.drink_image.getTag().equals(listItems.get(position).getPic())&&!listItems.get(position).getPic().equals("")) {
            loadImageByVolley(holder.drink_image.getTag().toString(), holder.drink_image);
        }
        return convertView;
    }
    class Holder {
        TextView drink_name,drink_price,drink_intro;
        ImageView drink_image;
    }

}
