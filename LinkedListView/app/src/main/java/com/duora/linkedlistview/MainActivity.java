package com.duora.linkedlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.duora.linkedlistview.adapter.CatograyAdapter;
import com.duora.linkedlistview.adapter.GoodsAdapter;
import com.duora.linkedlistview.javabean.Catogray;
import com.duora.linkedlistview.javabean.Goods;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private ListView listView1,listView2;

    private List<Catogray> list;

    private List<Goods> list2;
    private CatograyAdapter catograyAdapter;
    private GoodsAdapter goodsAdapter;
    private TextView tv_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        initView();
        initListData();
        initList1();
        addListener();

    }

    private void addListener() {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list2.clear();
                list2.addAll(list.get(position).getList());
                goodsAdapter.notifyDataSetChanged();
            }
        });
    }
    public int px2dip(float pxValue){
        final float scale = getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    private void initList1() {
        catograyAdapter=new CatograyAdapter(this,list);
        listView1.setAdapter(catograyAdapter);

        list2=new ArrayList<>();
        list2.addAll(list.get(0).getList());
        ImageView shopCat= (ImageView) findViewById(R.id.iv_add_cart);
        goodsAdapter=new GoodsAdapter(this,list2,catograyAdapter);
        listView2.setAdapter(goodsAdapter);
    }

    private void initListData() {
        list=new ArrayList<Catogray>();
        for (int i = 0; i < 17; i++) {
            Catogray catogray=new Catogray();
            catogray.setKind("item"+i);
            List<Goods> list1=new ArrayList<Goods>();
            for (int j = 0; j < 55; j++) {
                Goods goods=new Goods();
                goods.setGood_name("货品"+j);
                goods.setDescrible("描述" + j);
                goods.setPrice(20 + i + "");
                list1.add(goods);
            }
            catogray.setList(list1);
            list.add(catogray);
        }

    }


    private void initView() {
        setContentView(R.layout.activity_main);
        listView1= (ListView) findViewById(R.id.listview_1);
        listView2= (ListView) findViewById(R.id.listview_2);

    }

}
