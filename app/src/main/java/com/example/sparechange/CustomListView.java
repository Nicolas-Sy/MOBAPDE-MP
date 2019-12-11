package com.example.sparechange;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListView extends ArrayAdapter<String> {

//    <!--    UNUSED CLASS: NOT DELETING IT MUNA -->
    private Activity context;

    //EXAMPLE
    private String[] fruitname;
    private String[] desc;
    private Integer[] imgid;

    public CustomListView(Activity context, String[] fruitname, String[] desc, Integer[] imgid) {
        super(context, R.layout.row_item_transactions, fruitname);

        this.context = context;
//        this.transaction = transaction;
        this.fruitname = fruitname;
        this.desc = desc;
        this.imgid = imgid;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        ViewHolder viewHolder=null;
        if(r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.row_item_transactions, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.imageViewIcon.setImageResource(R.drawable.shopping);
        viewHolder.name.setText(fruitname[position]);
        viewHolder.category.setText(desc[position]);
//        viewHolder.amount.setText(String.valueOf(transaction.getAmount()));

        return r;

    }

    class ViewHolder{
        TextView name, category, amount, date;
        ImageView imageViewIcon;

        ViewHolder(View v){
            name = (TextView) v.findViewById(R.id.name);
            category = (TextView) v.findViewById(R.id.category);
            amount = (TextView) v.findViewById(R.id.amount);
            date = (TextView) v.findViewById(R.id.date);
            imageViewIcon = (ImageView) v.findViewById(R.id.imageViewIcon);
        }

    }
}
