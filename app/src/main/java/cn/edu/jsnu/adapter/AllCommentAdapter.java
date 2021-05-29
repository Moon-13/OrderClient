package cn.edu.jsnu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Order;

public class AllCommentAdapter extends DrinkBaseAdapter {
    private List<Order> listItems;
    private LayoutInflater inflater;

    public AllCommentAdapter(Context context, List<Order> data) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems = data;
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
            convertView = inflater.inflate(R.layout.comment_item, null);
            holder.drink_name = (TextView) convertView.findViewById(R.id.drink_name);
            holder.drink_detail = (TextView) convertView.findViewById(R.id.drink_detail);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
            holder.btn_del = (Button) convertView.findViewById(R.id.btn_del);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.drink_name.setText(listItems.get(position).getDrinkname());
        holder.shop_name.setText(listItems.get(position).getUsername()+"——" +listItems.get(position).getComment_time());
        holder.content.setText(listItems.get(position).getContent());
        holder.drink_detail.setText("单价：" + listItems.get(position).getPrice() + "元【" + listItems.get(position).getShopname() + "】");
        holder.btn_edit.setVisibility(View.INVISIBLE);
        holder.btn_del.setVisibility(View.INVISIBLE);
        return convertView;
    }

    class Holder {
        TextView drink_name, drink_detail, shop_name, content;
        Button btn_edit, btn_del;
    }

}
