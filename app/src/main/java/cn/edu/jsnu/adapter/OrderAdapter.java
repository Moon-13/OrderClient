package cn.edu.jsnu.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Order;
import cn.edu.jsnu.util.Contants;

public class OrderAdapter extends DrinkBaseAdapter{
    private List<Order> listItems;
    private LayoutInflater inflater;

    public OrderAdapter(Context context, List<Order> data) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems=data;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.order_item, null);
            holder.drink_name= (TextView) convertView.findViewById(R.id.drink_name);
            holder.drink_detail = (TextView) convertView.findViewById(R.id.drink_detail);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.shop_address= (TextView) convertView.findViewById(R.id.shop_address);
            holder.btn_comment=(Button)convertView.findViewById(R.id.btn_comment);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();}
        holder.drink_name.setText(listItems.get(position).getDrinkname());
        holder.shop_name.setText(listItems.get(position).getShopname());
        holder.shop_address.setText(listItems.get(position).getShopaddress());
        int num=listItems.get(position).getNum();
        double price=listItems.get(position).getPrice();
        holder.drink_detail.setText(price+"*"+num+"="+listItems.get(position).getSum()+"元");
        try{
            String content=listItems.get(position).getContent();
            if(!content.equals(""))
                holder.btn_comment.setVisibility(View.INVISIBLE);//已有内容
        }catch (NullPointerException e)
        {
            holder.btn_comment.setVisibility(View.VISIBLE);
        }
        holder.btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(context);
                new AlertDialog.Builder(context).setTitle("评论")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = et.getText().toString();
                                if (input.equals("")) {
                                    getToast("评论内容不能为空！");
                                }
                                else {
                                    String params="order_id="+listItems.get(position).getOrder_id()+
                                            "&content='"+input+"'";
                                    getJSONByVolley(Contants.BASEURL + "insertComment.do?"+params);
                                    holder.btn_comment.setVisibility(View.INVISIBLE);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });
        return convertView;
    }

    @Override
    protected void setJSONDataToView(String url, JSONObject data) {
        try {
            String success = data.getString("success");
            if("1".equals(success)) {
                getToast("评论成功");
            }
            else {
                getToast("评论失败");
            }
            notifyDataSetChanged();
        }catch (JSONException e)
        {
        }
    }

    class Holder {
        TextView drink_name,drink_detail,shop_name,shop_address;
        Button btn_comment;
    }

}
