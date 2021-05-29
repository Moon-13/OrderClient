package cn.edu.jsnu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Drink;
import cn.edu.jsnu.bean.Shop;
import cn.edu.jsnu.fragment.DrinkDetailFragment;
import cn.edu.jsnu.util.Contants;

public class CollectDrinkAdapter extends DrinkBaseAdapter {
    private List<Drink> listItems;
    private LayoutInflater inflater;
    private int user_id, drink_id;
    private Drink drink;
    private Shop shop;


    public CollectDrinkAdapter(Context context, List<Drink> data, int user_id) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems = data;
        this.user_id = user_id;
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
        final int p = position;

        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.collect_drink_item, null);
            holder.drink_name = (TextView) convertView.findViewById(R.id.drink_name);
            holder.btn_uncollect = (Button) convertView.findViewById(R.id.btn_uncollect);
            holder.btn_enter = (Button) convertView.findViewById(R.id.btn_enter);
            holder.drink_image = (ImageView) convertView.findViewById(R.id.drink_image);
            holder.drink_price = (TextView) convertView.findViewById(R.id.drink_price);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.drink_name.setText(listItems.get(position).getDrinkname());
        holder.drink_price.setText(String.valueOf(listItems.get(position).getPrice()) + "元");
        holder.drink_image.setTag(listItems.get(position).getPic());
        // 预设一个图片
        holder.drink_image.setImageResource(R.drawable.error);
        // 通过 tag 来防止图片错位
        if (holder.drink_image.getTag() != null && holder.drink_image.getTag().equals(listItems.get(position).getPic()) && !listItems.get(position).getPic().equals("")) {
            loadImageByVolley(holder.drink_image.getTag().toString(), holder.drink_image);
        }

        holder.btn_uncollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drink = listItems.get(p);
                Log.e("========", drink.toString());
                getJSONByVolley(Contants.BASEURL + "userCollectDrink.do?drink_id=" + drink.getDrink_id() + "&user_id=" + user_id);
            }
        });
        holder.btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drink = listItems.get(p);
                Log.e("========", drink.toString());
                getJSONByVolley(Contants.BASEURL + "getShopById.do?shop_id=" + drink.getShop_id());
            }
        });
        return convertView;
    }

    class Holder {
        TextView drink_name, drink_price;
        ImageView drink_image;
        Button btn_uncollect, btn_enter;
    }

    @Override
    protected void setJSONDataToView(String url, JSONObject data) {
        if (url.contains("userCollectDrink")) {
            try {
                String collected = data.getString("success");
                if ("1".equals(collected)) {
                    getToast("取消成功");
                } else {
                    getToast("取消失败");
                }
                listItems.remove(drink);
                notifyDataSetChanged();
            } catch (JSONException e) {

            }
        } else {
            Gson gson = new Gson();
            shop = gson.fromJson(data.toString(), Shop.class);
            Bundle bundle = new Bundle();
            bundle.putInt("drink_id", drink_id);
            bundle.putString("phonenum", shop.getPhonenum());
            bundle.putInt("flag", 1);//标记返回
            DrinkDetailFragment drinkDetailFragment = new DrinkDetailFragment();
            changeFrament(drinkDetailFragment, bundle, "drinkDetailFragment");
        }

    }
}