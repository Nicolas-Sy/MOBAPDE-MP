package com.example.sparechange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] categoryNames;
    private int[] categoryImage;

    public CategoryAdapter(Context c, String[] categoryNames, int[] categoryImage) {
        context = c;
        this.categoryNames = categoryNames;
        this.categoryImage = categoryImage;
    }

    @Override
    public int getCount() {
        return categoryNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        imageView.setImageResource(categoryImage[position]);
        textView.setText(categoryNames[position]);
        return convertView;
    }
}



