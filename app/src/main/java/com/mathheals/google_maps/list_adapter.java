package com.mathheals.google_maps;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class list_adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Yer> myerListesi;

    public list_adapter(Activity activity, List<Yer> kisiler) {

        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        myerListesi = kisiler;
    }

    @Override
    public int getCount() {
        return myerListesi.size();
    }

    @Override
    public Yer getItem(int position) {

        return myerListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.list_view, null);
        TextView textView = (TextView) satirView.findViewById(R.id.isim);
        Yer yer = myerListesi.get(position);
        textView.setText(yer.getIsim());

        return satirView;
    }
}