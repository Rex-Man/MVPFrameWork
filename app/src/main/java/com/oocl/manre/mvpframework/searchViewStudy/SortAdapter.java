package com.oocl.manre.mvpframework.searchViewStudy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.oocl.manre.mvpframework.R;

import java.util.List;

/**
 * Created by manre on 2/14/17.
 */

public class SortAdapter extends BaseAdapter implements SectionIndexer{
    private List<TruckerModel> truckerModels;
    private Context mContext;

    public SortAdapter(Context mContext,List<TruckerModel> truckerModels) {
        this.truckerModels=truckerModels;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return truckerModels.size();
    }

    @Override
    public Object getItem(int i) {
        return truckerModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        final TruckerModel truckerModel=truckerModels.get(i);
        if(view==null)
        {
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.items,null);
            viewHolder.tvCategoryname= (TextView) view.findViewById(R.id.categoryname);
            viewHolder.tvTruckerNameShow= (TextView) view.findViewById(R.id.truckerNameShow);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }


        //viewHolder.tvCategoryname.setVisibility(View.VISIBLE);
        viewHolder.tvCategoryname.setText(truckerModel.getSortLetters());
        viewHolder.tvTruckerNameShow.setText(truckerModels.get(i).getName());
        return view;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
       for(int i=0;i<getCount();i++)
       {
           String sortStr=truckerModels.get(i).getSortLetters();
           char firstChar=sortStr.toUpperCase().charAt(0);
           if(firstChar==section)
           {
               return i;
           }
       }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {

        return truckerModels.get(i).getSortLetters().charAt(0);
    }

    final static class ViewHolder{
        TextView tvCategoryname;
        TextView tvTruckerNameShow;
    }
}
