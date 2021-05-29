package cn.edu.jsnu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import cn.edu.jsnu.bean.Drink;
import cn.edu.jsnu.bean.Shop;
import cn.edu.jsnu.util.Contants;

public class SearchFragment extends BaseFragment {
    private ListView list;
    List<Drink> drinks;
    private Button btn_search;
    private EditText et_search;
    private int drink_id;

    Shop shop;
    protected View init(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.search_list, container, false);
        list=(ListView)view.findViewById(R.id.drinklist);
        btn_search=(Button)view.findViewById(R.id.search_btn);
        et_search=(EditText)view.findViewById(R.id.search_edit);
        drinks=new ArrayList<Drink>();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int shop_id = drinks.get(arg2).getShop_id();
                drink_id=drinks.get(arg2).getDrink_id();
                //获取当前店铺信息
                getJSONByVolley(Contants.BASEURL + "getShopById.do?shop_id=" + shop_id);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result = et_search.getText().toString();
                    String url = Contants.BASEURL + "getDrinkBySearch.do?search=" + URLEncoder.encode(result, "UTF-8");
                    getJSONArrayByVolley(url);
                }catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
    protected void setJSONArrayToView(JSONArray data) {
        Gson gson=new Gson();
        drinks= gson.fromJson(data.toString(),new TypeToken<List<Drink>>(){}.getType());
        DrinkAdapter adapter=new DrinkAdapter(this.getActivity(), drinks);
        list.setAdapter(adapter);
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
        DrinkDetailFragment drinkDetailFragment = new DrinkDetailFragment();
        changeFrament(drinkDetailFragment, bundle, "drinkDetailFragment");
    }

}
