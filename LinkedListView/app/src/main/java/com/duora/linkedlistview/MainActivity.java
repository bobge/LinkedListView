package com.duora.linkedlistview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


    private ImageView shopCart;//购物车
    private ViewGroup anim_mask_layout;//动画层
    private ImageView ball;// 小圆点
    private int buyNum = 0;//购买数量
    private BadgeView buyNumView;//购物车上的数量标签
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

        shopCart= (ImageView) findViewById(R.id.iv_add_cart);
        buyNumView= (BadgeView) findViewById(R.id.tv_count_price);
    }


    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        shopCart.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                buyNum++;//让购买数量加1
                buyNumView.setText(buyNum + "");//
                buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
                buyNumView.show();
            }
        });

    }
}
