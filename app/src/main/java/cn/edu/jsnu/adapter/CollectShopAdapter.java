package cn.edu.jsnu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.adapter.DrinkBaseAdapter;
import cn.edu.jsnu.bean.Shop;
import cn.edu.jsnu.fragment.DrinkListFragment;
import cn.edu.jsnu.util.Contants;

public class CollectShopAdapter extends DrinkBaseAdapter {
    private List<Shop> listItems;
    private LayoutInflater inflater;
    private int user_id;
    private Shop shop;


    public CollectShopAdapter(Context context, List<Shop> data,int user_id) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems = data;
        this.user_id=user_id;
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
        final int p=position;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.collect_shop_item, null);
            holder.res_name= (TextView) convertView.findViewById(R.id.res_name);
            holder.btn_collect = (Button) convertView.findViewById(R.id.btn_collect);
            holder.btn_enter = (Button) convertView.findViewById(R.id.btn_enter);
            holder.image = (ImageView) convertView.findViewById(R.id.res_image);
            holder.res_address = (TextView) convertView.findViewById(R.id.res_address);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();}
        holder.res_name.setText((listItems.get(position).getShopname()));
        holder.res_address.setText((listItems.get(position).getAddress()));

        // 给 ImageView 设置一个 tag
        holder.image.setTag(listItems.get(position).getPic());
        // 预设一个图片
        holder.image.setImageResource(R.drawable.error);
        // 通过 tag 来防止图片错位
        if (holder.image.getTag() != null && holder.image.getTag().equals(listItems.get(position).getPic())&&!listItems.get(position).getPic().equals("")) {
            loadImageByVolley(holder.image.getTag().toString(), holder.image);
        }
        holder.btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop = listItems.get(p);
                getJSONByVolley(Contants.BASEURL + "userCollectShop.do?shop_id=" + shop.getShop_id()+"&user_id="+user_id);
            }
        });
        holder.btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop = listItems.get(p);
                int shop_id =shop.getShop_id();
                String shopname = shop.getShopname();
                Bundle bundle = new Bundle();
                bundle.putInt("shop_id", shop_id);
                bundle.putString("shopname", shopname);
                bundle.putInt("flag", 1);//标记返回

                DrinkListFragment drinkListFragment = new DrinkListFragment();
                changeFrament(drinkListFragment, bundle, "drinkListFragment");
            }
        });
        return convertView;
    }
    class Holder {
        TextView res_name,res_address;
        ImageView image;
        Button btn_collect,btn_enter;
    }

    @Override
    protected void setJSONDataToView(String url, JSONObject data) {
        try {
            String collected = data.getString("success");
            if("1".equals(collected)) {
                Toast.makeText(context,"取消成功",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context,"取消失败",Toast.LENGTH_LONG).show();
            }
            listItems.remove(shop);
            notifyDataSetChanged();
        }catch (JSONException e)
        {

        }
    }
}
