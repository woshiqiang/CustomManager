package com.hbck.custommanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hbck.custommanager.R;
import com.hbck.custommanager.bean.User;

import java.util.List;

/**
 * @author JianQiang
 * @Date 2018-07-03.
 */
public class UserAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> list;

    public UserAdapter(Context mContext, List<User> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_user, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_username.setText(list.get(position).getUsername());
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_sex.setText(list.get(position).getSex() == 1 ? "男" : "女");
        holder.tv_phone.setText(list.get(position).getPhone());
        return convertView;
    }

    private static class ViewHolder {
        public TextView tv_name;
        public TextView tv_username;
        public TextView tv_sex;
        public TextView tv_phone;

        public ViewHolder(View view) {
            this.tv_name = view.findViewById(R.id.tv_name);
            this.tv_username = view.findViewById(R.id.tv_username);
            this.tv_sex = view.findViewById(R.id.tv_sex);
            this.tv_phone = view.findViewById(R.id.tv_phone);
        }
    }
}
