package com.skripsi.lamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.lamp.model.MenuModel;
import com.skripsi.lamp.R;

import java.util.ArrayList;

/**
 * Created by gandhi on 1/6/17.
 */

public class MenuAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<MenuModel> menuModels;

    public MenuAdapter(Context context, ArrayList<MenuModel> menuModels){
        this.context = context;
        this.menuModels = menuModels;
    }
    @Override
    public int getCount() {
        return menuModels.size();
    }

    @Override
    public Object getItem(int position) {
        return menuModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = inflater.inflate(R.layout.menu_adapter, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
        TextView textView = (TextView) convertView.findViewById(R.id.namaMenu);
        TextView textstatus = (TextView) convertView.findViewById(R.id.statusMenu);

        imageView.setImageResource(menuModels.get(position).getIcon());
        textView.setText(menuModels.get(position).getNamaMenu());
        textstatus.setText(menuModels.get(position).getStatusMenu());
        return convertView;
    }
}
