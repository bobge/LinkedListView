package com.duora.linkedlistview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duora.linkedlistview.MainActivity;
import com.duora.linkedlistview.R;
import com.duora.linkedlistview.javabean.Goods;

import java.util.List;

/**
 * Created by bobge on 15/7/31.
 */
public class GoodsAdapter extends BaseAdapter{

    private List<Goods> list;
    private Context context;
    private CatograyAdapter catograyAdapter;
    public GoodsAdapter(Context context, List<Goods> list1) {
        this.context=context;
        this.list=list1;
    }

    public GoodsAdapter(Context context, List<Goods> list2, CatograyAdapter catograyAdapter) {
        this.context=context;
        this.list=list2;
        this.catograyAdapter=catograyAdapter;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=convertView;
        final Viewholder viewholder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.right_listview,null);
            viewholder=new Viewholder();
            viewholder.tv_name= (TextView) view.findViewById(R.id.tv_name);
            viewholder.tv_desc= (TextView) view.findViewById(R.id.tv_desc);
            viewholder.tv_price= (TextView) view.findViewById(R.id.tv_price);
            viewholder.iv_add= (ImageView) view.findViewById(R.id.iv_add);
            viewholder.iv_remove= (ImageView) view.findViewById(R.id.iv_remove);
            viewholder.et_acount= (EditText) view.findViewById(R.id.et_count);
            view.setTag(viewholder);
        }else
            viewholder= (Viewholder) view.getTag();
        viewholder.tv_name.setText(list.get(position).getGood_name());
        viewholder.tv_desc.setText(list.get(position).getDescrible());
        viewholder.tv_price.setText(list.get(position).getPrice());
        viewholder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = list.get(position).getCount();
                count++;
                list.get(position).setCount(count);
                viewholder.et_acount.setVisibility(View.VISIBLE);
                viewholder.iv_remove.setVisibility(View.VISIBLE);
                viewholder.et_acount.setText(list.get(position).getCount() + "");
                catograyAdapter.notifyDataSetChanged();
            }
        });
        Log.i("test",list.get(position).getCount()+"==");
        if (list.get(position).getCount()<=0){
            viewholder.et_acount.setVisibility(View.INVISIBLE);
            viewholder.iv_remove.setVisibility(View.INVISIBLE);
        }else{
            viewholder.et_acount.setVisibility(View.VISIBLE);
            viewholder.iv_remove.setVisibility(View.VISIBLE);
        }
        return view;
    }
    class Viewholder{
        TextView tv_name;
        TextView tv_desc;
        TextView tv_price;
        ImageView iv_add,iv_remove;
        EditText et_acount;
    }
    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((MainActivity)context).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

}
