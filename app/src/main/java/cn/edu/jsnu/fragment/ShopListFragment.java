package cn.edu.jsnu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.adapter.DrinkAdapter;
import cn.edu.jsnu.adapter.ShopAdapter;
import cn.edu.jsnu.bean.Drink;
import cn.edu.jsnu.bean.Shop;
import cn.edu.jsnu.util.Contants;

public class ShopListFragment extends BaseFragment{
    private ListView list;
    List<Shop> drinks=new ArrayList<>();
    private int drink_id;
    ShopAdapter adapter = null;


    Shop shop;
    protected View init(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.shop_list, container, false);
        list=(ListView)view.findViewById(R.id.listView1);

        drinks=new ArrayList<Shop>();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int shop_id = drinks.get(arg2).getShop_id();
//                int drink_id = drinks.get(arg2).getDrink_id();
                //获取当前店铺信息
                getJSONByVolley(Contants.BASEURL + "getShopById.do?shop_id=" + shop_id);
                //getJSONByVolley(Contants.BASEURL + "getShopById.do?shop_id=1" );
            }
        });


        String url = Contants.BASEURL + "getAllShop.do?";
        getJSONArrayByVolley(url);
        return view;
    }
    protected synchronized void setJSONArrayToView(JSONArray data) {
        if (getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    drinks.clear();
                    Gson gson=new Gson();
                    drinks.addAll(gson.fromJson(data.toString(),new TypeToken<List<Shop>>(){}.getType()));
                    if (adapter==null){
                        adapter=new ShopAdapter(getActivity(), drinks);
                        list.setAdapter(adapter);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }


    }
    @Override
    protected void setJSONDataToView(String url, JSONObject data) {
        Gson gson=new Gson();
        shop=gson.fromJson(data.toString(), Shop.class);
        Bundle bundle = new Bundle();
        bundle.putInt("drink_id", drink_id);
        bundle.putString("phonenum", shop.getPhonenum());
        bundle.putInt("shop_id", shop.getShop_id());
        bundle.putString("shopname", shop.getShopname());
        bundle.putInt("flag", 2);//标记返回
        DrinkListFragment drinkDetailFragment = new DrinkListFragment();
        changeFrament(drinkDetailFragment, bundle, "drinkDetailFragment");
    }

}
