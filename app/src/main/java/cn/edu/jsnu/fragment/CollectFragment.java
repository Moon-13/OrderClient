package cn.edu.jsnu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.adapter.CollectDrinkAdapter;
import cn.edu.jsnu.adapter.CollectShopAdapter;
import cn.edu.jsnu.bean.Drink;
import cn.edu.jsnu.bean.Shop;
import cn.edu.jsnu.util.Contants;

public class CollectFragment extends BaseFragment {
    private RadioButton ra_shop_bt, ra_drink_bt;
    private ListView list;
    List<Drink> drinks;
    List<Shop> shops;
    private int flag = 0;

    protected View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_collection, container, false);
        list = (ListView) view.findViewById(R.id.collect_list);
        ra_shop_bt = (RadioButton) view.findViewById(R.id.ra_shop_bt);
        ra_drink_bt = (RadioButton) view.findViewById(R.id.ra_drink_bt);

        ra_shop_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ra_shop_bt.setBackgroundResource(R.color.colorMain);
                ra_drink_bt.setBackgroundResource(R.color.gray);
                shops = new ArrayList<Shop>();
                flag = 0;
                String params = "?user_id=" + user_id + "&flag=" + flag;
                getJSONArrayByVolley(Contants.BASEURL + "getAllUserCollection.do" + params);
            }
        });
        ra_drink_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ra_drink_bt.setBackgroundResource(R.color.colorMain);
                ra_shop_bt.setBackgroundResource(R.color.gray);
                flag = 1;
                String params = "?user_id=" + user_id + "&flag=" + flag;
                getJSONArrayByVolley(Contants.BASEURL + "getAllUserCollection.do" + params);
            }
        });
        //默认显示收藏店铺
        String params = "?user_id=" + user_id + "&flag=" + flag;
        getJSONArrayByVolley(Contants.BASEURL + "getAllUserCollection.do" + params);
        return view;
    }

    protected void setJSONArrayToView(JSONArray data) {
        Gson gson = new Gson();
        if (flag == 0) {
            shops = gson.fromJson(data.toString(), new TypeToken<List<Shop>>() {
            }.getType());
            CollectShopAdapter adapter = new CollectShopAdapter(this.getActivity(), shops, user_id);
            list.setAdapter(adapter);
        } else {
            drinks = gson.fromJson(data.toString(), new TypeToken<List<Drink>>() {
            }.getType());
            CollectDrinkAdapter adapter = new CollectDrinkAdapter(this.getActivity(), drinks, user_id);
            list.setAdapter(adapter);
        }
    }
}