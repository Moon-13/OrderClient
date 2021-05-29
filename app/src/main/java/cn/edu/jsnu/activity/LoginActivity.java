package cn.edu.jsnu.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.jsnu.R;
import cn.edu.jsnu.util.Contants;

public class LoginActivity extends BaseActivity {

    private EditText zhanghao;
    private EditText mima;
    private Button btn_login;
    private TextView btn_reg;

    protected void init(Context context){
        super.init(context);
        setContentView(R.layout.activity_login);
        btn_reg=(TextView)findViewById(R.id.register_btn);
        btn_login=(Button)findViewById(R.id.btn_login);
        zhanghao=(EditText)findViewById(R.id.login_name);
        mima=(EditText)findViewById(R.id.login_pass);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = zhanghao.getText().toString();
                String password = mima.getText().toString();
                //if (zhanghao.length() > 0 & mima.length() > 0) {
                if(true){
                    String params = "?username=" + username + "&userpass=" + password;
                    getJSONByVolley(Contants.BASEURL + "userLogin.do" + params,null);
                } else {
                    getToast("请输入账号密码");
                }
            }
        });
    }
    @Override
    protected void setJSONDataToView(JSONObject response) {
        try {
            Log.e("=======================",response.toString());
            if("-1".equals(response.getString("userid")))
                getToast("用户名和密码不能为空");
            else if(!"0".equals(response.getString("userid"))) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
                saveUser(response.getString("userid"));
            }
            else
                getToast("用户名和密码不能为空");
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String user_id)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("username", zhanghao.getText().toString());
        int userid=Integer.parseInt(user_id);
        editor.putInt("user_id", userid);
        editor.putString("userpass",mima.getText().toString());
        editor.commit();//提交修改
    }
}
