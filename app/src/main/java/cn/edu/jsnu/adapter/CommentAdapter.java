package cn.edu.jsnu.adapter;

import cn.edu.jsnu.R;
import cn.edu.jsnu.bean.Order;
import cn.edu.jsnu.util.Contants;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
public class CommentAdapter extends DrinkBaseAdapter{
    private List<Order> listItems;
    private LayoutInflater inflater;


    public CommentAdapter(Context context, List<Order> data) {
        super(context);
        this.inflater = LayoutInflater.from(context);
        this.listItems = data;
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
            convertView = inflater.inflate(R.layout.comment_item, null);
            holder.drink_name = (TextView) convertView.findViewById(R.id.drink_name);
            holder.drink_detail = (TextView) convertView.findViewById(R.id.drink_detail);
            holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
            holder.btn_del = (Button) convertView.findViewById(R.id.btn_del);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.drink_name.setText(listItems.get(position).getDrinkname());
        holder.shop_name.setText("【" + listItems.get(position).getShopname() + "】");
        holder.content.setText(listItems.get(position).getContent() + "(" + listItems.get(position).getComment_time() + ")");
        holder.drink_detail.setText("单价：" + listItems.get(position).getPrice() + "元");
        final int order_id = listItems.get(position).getOrder_id();

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(context);
                et.setText(listItems.get(position).getContent());
                new AlertDialog.Builder(context).setTitle("修改评论")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    String input = et.getText().toString();
                                    if (input.equals("")) {
                                        getToast("评论内容不能为空！");
                                    } else {
                                        String params = "order_id=" + order_id +
                                                "&content='" + URLEncoder.encode(input,"utf-8") + "'";
                                        getJSONByVolley(Contants.BASEURL + "updateComment.do?" + params);
                                        listItems.get(position).setContent(input);
                                        notifyDataSetChanged();
                                    }
                                }catch (UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String params = "order_id=" + order_id ;
                getJSONByVolley(Contants.BASEURL + "deleteComment.do?" + params);
                listItems.remove(position);
            }

        });
        return convertView;
    }

    class Holder {
        TextView drink_name, drink_detail, shop_name, content;
        Button btn_edit, btn_del;
    }

    @Override
    protected void setJSONDataToView(String url, JSONObject data) {
        try {
            String success = data.getString("success");
            if (url.contains("updateComment")) {
                if ("1".equals(success)) {
                    getToast("评论修改成功");
                }
                else
                    getToast("评论修改成失败");

            } else {
                if ("1".equals(success)) {
                    getToast("评论删除成功");
                } else {
                    getToast("评论删除失败");
                }
            }
        } catch (JSONException e) {
        }
    }
}
