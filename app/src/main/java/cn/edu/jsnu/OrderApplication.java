package cn.edu.jsnu;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class OrderApplication extends Application {
    private static RequestQueue requestQueue = null;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }
    public static RequestQueue getRequestQueue(Context context){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(context);
        }

        return requestQueue;
    }
}

