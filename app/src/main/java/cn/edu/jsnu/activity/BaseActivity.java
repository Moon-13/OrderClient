package cn.edu.jsnu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import cn.edu.jsnu.OrderApplication;
import cn.edu.jsnu.R;

public class BaseActivity extends Activity{
    protected Context context;
    public RequestQueue requestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(this);
    }

    protected void init(Context context)
    {
        this.context=context;

        requestQueue=OrderApplication.getRequestQueue(context);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 利用Volley获取String数据，格式为JSON形式
     */
    public void getJSONByVolley(String url,JSONObject params) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setJSONDataToView(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        getNetError();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    protected void setJSONDataToView(JSONObject data) {
    }
    public void getToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void getNetError() {
        getToast("亲~网络连接失败！");
    }
}
