package cn.edu.jsnu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.edu.jsnu.R;
import cn.edu.jsnu.util.Contants;

public class RegisterActivity extends BaseActivity {
    private CheckBox check;
    private TextView xiahua;
    private EditText zhanghao,mima,mima2,phone,et_address,et_comment;
    private Button btn;
    private ImageButton back_to_login;

    @Override
    protected void init(Context context){
        super.init(context);
        setContentView(R.layout.activity_register);
        back_to_login=(ImageButton)findViewById(R.id.reg_back);
        check=(CheckBox)findViewById(R.id.checkBox1);
        btn=(Button)findViewById(R.id.btn_reg);
        zhanghao=(EditText)findViewById(R.id.edit_zhanghao);
        mima=(EditText)findViewById(R.id.edit_mima);
        mima2=(EditText)findViewById(R.id.edit_mima2);
        phone=(EditText)findViewById(R.id.edit_phone);
        et_address=(EditText)findViewById(R.id.edit_address);
        et_comment=(EditText)findViewById(R.id.edit_comment);
        xiahua=(TextView)findViewById(R.id.xiahua);
        xiahua.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        back_to_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = zhanghao.getText().toString();
                String password = mima.getText().toString();
                String mobileNum = phone.getText().toString();
                // String address=et_address.getText().toString();
                String address="徐州";
                String comment=et_comment.getText().toString();
                Log.e("=====",comment);
                String mi2 = mima2.getText().toString();
                if (zhanghao.length() > 0 && mima.length() > 0) {
                    if (password.equals(mi2)) {
                        if (check.isChecked()) {
                            if(mobileNum.length()>0) {
                                try {
                                    username= URLEncoder.encode(username, "UTF-8");
                                    password= URLEncoder.encode(password, "UTF-8");
                                    mobileNum= URLEncoder.encode(mobileNum, "UTF-8");
                                    address= URLEncoder.encode(address, "UTF-8");
                                    comment= URLEncoder.encode(comment, "UTF-8");
                                    String params = "?username=" + username + "&userpass=" + password +
                                            "&mobilenum=" + mobileNum + "&address=" + address + "&comment=" + comment;
                                    getJSONByVolley(Contants.BASEURL + "userRegister.do" + params, null);
                                }catch (UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                                getToast("请填写电话号码，否则无法下单");

                        } else {
                            getToast("请阅读协议");
                        }
                    } else {
                        getToast("请输入账号密码");
                    }
                } else {
                    getToast("请输入账号密码");
                }
            }
        });
    }

    @Override
    protected void setJSONDataToView(JSONObject response) {
        try {
            if ("1".equals(response.getString("success"))) {
                getToast("注册成功");
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            } else
                getToast("注册失败，检查网络或更改用户名");
        } catch (JSONException e) {

        }
    }
}
