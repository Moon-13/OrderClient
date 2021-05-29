package cn.edu.jsnu.fragment;

import android.widget.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.adapter.CommentAdapter;
import cn.edu.jsnu.adapter.OrderAdapter;
import cn.edu.jsnu.bean.Order;
import cn.edu.jsnu.bean.User;
import cn.edu.jsnu.util.Contants;
import cn.edu.jsnu.util.ListViewHeightUtil;

public class MeFragment extends BaseFragment {
    private ListView list;
    private EditText et_username,et_phonenum,et_useraddress,et_pass;
    private RadioButton rb_order,rb_comment;
    private Button btn_ok;
    private User user;
    private int flag=0;//标记：订单显示和评论显示
    @Override
    protected View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, container, false);
        list=(ListView)view.findViewById(R.id.listView);
        et_username=(EditText)view.findViewById(R.id.username);
        et_phonenum=(EditText)view.findViewById(R.id.phonenum);
        et_useraddress=(EditText)view.findViewById(R.id.useraddress);
        et_pass=(EditText)view.findViewById(R.id.userpass);
        rb_order=(RadioButton)view.findViewById(R.id.order_radio);
        rb_comment=(RadioButton)view.findViewById(R.id.comment_radio);
        btn_ok=(Button)view.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String params;
                params="user_id="+user_id+"&username="+et_username.getText()+"&userpass="+et_pass.getText()+
                        "&address="+et_useraddress.getText()+"&mobilenum="+et_phonenum.getText();
                getJSONByVolley(Contants.BASEURL + "updateUserById.do?" + params);
            }
        });
        rb_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_order.setBackgroundResource(R.color.colorMain);
                rb_comment.setBackgroundResource(R.color.gray);
                flag = 0;
                String params = "?user_id=" + user_id;
                getJSONArrayByVolley(Contants.BASEURL + "getAllUserOrder.do" + params);
            }
        });
        rb_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_comment.setBackgroundResource(R.color.colorMain);
                rb_order.setBackgroundResource(R.color.gray);
                flag = 1;
                String params = "?user_id=" + user_id;
                getJSONArrayByVolley(Contants.BASEURL + "getAllUserComment.do" + params);
            }
        });

        getJSONByVolley(Contants.BASEURL + "getUserById.do?user_id=" + user_id);
        getJSONArrayByVolley(Contants.BASEURL + "getAllUserOrder.do?user_id="+user_id);
        return view;
    }
    @Override
    protected void setJSONDataToView(String url, JSONObject data) {

        Gson gson = new Gson();
        if(url.contains("getUserById")) {
            user = gson.fromJson(data.toString(), User.class);
            et_username.setText(username);
            et_useraddress.setText(user.getAddress());
            et_pass.setText(userpass);
            et_phonenum.setText(user.getMobilenum());
        }else
        {
            try {
                if (1==data.getInt("success"))
                    getToast("信息更新成功");
                else
                    getToast("信息更新失败");
            }catch (JSONException e)
            {
            }
        }
    }

    @Override
    protected void setJSONArrayToView(JSONArray data) {
        Gson gson=new Gson();
        if(flag==0)
        {
            List<Order> orders= gson.fromJson(data.toString(),new TypeToken<List<Order>>(){}.getType());
            OrderAdapter adapter=new OrderAdapter(this.getActivity(),orders);
            list.setAdapter(adapter);
            ListViewHeightUtil.setListViewHeightBasedOnChildren(list);//动态设置高度
        }else
        {
            List<Order> comments= gson.fromJson(data.toString(),new TypeToken<List<Order>>(){}.getType());
            CommentAdapter adapter=new CommentAdapter(this.getActivity(),comments);
            list.setAdapter(adapter);
            ListViewHeightUtil.setListViewHeightBasedOnChildren(list);//动态设置高度
        }

    }
}
