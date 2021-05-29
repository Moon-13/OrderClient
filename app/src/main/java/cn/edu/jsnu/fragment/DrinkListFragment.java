package cn.edu.jsnu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
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

public class DrinkListFragment extends BaseFragment {
    private ListView list;
    private TextView shoptitle;
    List<Drink> drinks;
    private int shop_id;
    Shop shop;
    private Button btn_collect;
    private ImageButton ibtn_back;
    private boolean collect_flag=false;
    private String shopname;

    protected View init(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.drink_list, container, false);
        list=(ListView)view.findViewById(R.id.drinklist);
        shoptitle=(TextView)view.findViewById(R.id.shoptitle);
        shopname= getArguments().getString("shopname");
        shop_id=getArguments().getInt("shop_id");
        shoptitle.setText(shopname);
        drinks=new ArrayList<Drink>();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int drink_id = drinks.get(arg2).getDrink_id();
                Bundle bundle = new Bundle();
                bundle.putInt("drink_id", drink_id);
                bundle.putString("phonenum", shop.getPhonenum());
                bundle.putInt("shop_id", shop_id);
                bundle.putString("shopname", shopname);
                bundle.putInt("flag", 0);//标记返回
                DrinkDetailFragment drinkDetailFragment = new DrinkDetailFragment();
                changeFrament(drinkDetailFragment, bundle, "drinkDetailFragment");

            }
        });
        ibtn_back=(ImageButton)view.findViewById(R.id.drink_back);
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(0==getArguments().getInt("flag")) {
                    ShopListFragment shopListFragment = new ShopListFragment();
                    changeFrament(shopListFragment, null, "shopListFragment");
                }
                else
                {
                    CollectFragment collectFragment = new CollectFragment();
                    changeFrament(collectFragment, null, "collectFragment");
                }
            }

        });
        btn_collect=(Button)view.findViewById(R.id.coll_shop);
        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Contants.BASEURL + "userCollectShop.do?user_id=" + user_id + "&shop_id=" + shop_id;
                getJSONByVolley(url);
                collect_flag = !collect_flag;
                if (collect_flag)
                    btn_collect.setBackgroundResource(R.drawable.xihuanhou);
                else
                    btn_collect.setBackgroundResource(R.drawable.xihuan);

            }
        });
        //判断当前是否已经收藏
        getJSONByVolley(Contants.BASEURL + "isCollected.do?flag=0&shop_drink_id=" + shop_id + "&user_id=" + user_id);
        //获取所有的饮品
        getJSONArrayByVolley(Contants.BASEURL+"getDrinkByShop.do?shop_id="+shop_id);
        //获取当前店铺信息
        getJSONByVolley(Contants.BASEURL+"getShopById.do?shop_id="+shop_id);
        return view;
    }
    protected void setJSONArrayToView(JSONArray data) {
        Gson gson=new Gson();
        drinks= gson.fromJson(data.toString(),new TypeToken<List<Drink>>(){}.getType());
        DrinkAdapter adapter=new DrinkAdapter(this.getActivity(), drinks);
        list.setAdapter(adapter);
    }

    @Override
    protected void setJSONDataToView(String url,JSONObject data) {
        //读取收藏信息
        if(url.contains("isCollected")) {
            try {
                String collected = data.getString("collected");
                if("1".equals(collected)) {
                    btn_collect.setBackgroundResource(R.drawable.xihuanhou);
                    collect_flag=true;
                }
                else {
                    btn_collect.setBackgroundResource(R.drawable.xihuan);
                    collect_flag=false;
                }
            }catch (JSONException e)
            {
            }
        }else if(url.contains("userCollectShop"))
        {
            if(collect_flag)
                getToast("店铺收藏成功！");
            else
                getToast("店铺取消收藏！");
        }
        else
        {
            Gson gson=new Gson();
            shop=gson.fromJson(data.toString(), Shop.class);
        }
    }
}
