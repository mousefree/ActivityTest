package mouse.test.fragment;

//import mouse.test.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mouse.test.R;
import mouse.test.adapter.Article_ChildFragment_Adapter;
import mouse.test.asynctask.LoginAsyncTask;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * ViewPager 他实际上就是google提供的一种方便的fragment切换的控件，并且提供一些特殊的效果，
 * 比如自己手工去切换fragment要方便，并且效果好看。
 */


public class Main_NaviFragment_article extends Fragment {
	
	private LinearLayout article_fragment_hsv_child;
	private ViewPager vPager;
	private List<Fragment> fragmentList;
	private ImageView cursor;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度	
    private int oldx = 0;
    private Map map;
	//三个一般必须重载的方法
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        System.out.println("ExampleFragment--onCreate");
        
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
			        // Inflate the layout for this fragment
		View convertView  = inflater.inflate(R.layout.article_main_layout, container, false);
		article_fragment_hsv_child = (LinearLayout)convertView.findViewById(R.id.article_fragment_hsv_child);
		cursor = (ImageView) convertView.findViewById(R.id.cursor);
		InitImageView(convertView);
		map = new HashMap();
		for(int i = 1; i <=5; i++ ) {
			final TextView tv1 = new TextView(convertView.getContext());
			tv1.setPadding(15, 15, 15, 0);
			tv1.setText("项目" + String.valueOf(i));
			tv1.setTag(i);
			tv1.setOnClickListener(new LabelOnClickListener(convertView.getContext()));
			article_fragment_hsv_child.addView(tv1);	
			
			/*
			 *   下面这段代码，实现的功能是在onCreate方法里面，可以得到布局控件的高度和宽度以及屏幕位置信息，在这个单元没有使用
			 */
			if(i == 1) {
				 tv1.post(new Runnable() {
				        @Override
				        public void run() {
							int[] location = new  int[2] ;
							tv1.getLocationOnScreen(location);
							oldx = location[0];
				        }
				    });
			}
		}
		fragmentList = new ArrayList<Fragment>();
		Child1_NaviFragment_article child1fragment = new Child1_NaviFragment_article();
		Child2_NaviFragment_article child2fragment = new Child2_NaviFragment_article();
		Child3_NaviFragment_article child3fragment = new Child3_NaviFragment_article();
		Child4_NaviFragment_article child4fragment = new Child4_NaviFragment_article();
		Child5_NaviFragment_article child5fragment = new Child5_NaviFragment_article();
		
		fragmentList.add(child1fragment);
		fragmentList.add(child2fragment);
		fragmentList.add(child3fragment);
		fragmentList.add(child4fragment);
		fragmentList.add(child5fragment);
		
		vPager = (ViewPager) convertView.findViewById(R.id.viewpager);  
        vPager.setAdapter(new Article_ChildFragment_Adapter(getChildFragmentManager(), fragmentList)); 
		vPager.setCurrentItem(0);
		vPager.setOnPageChangeListener(new CustomPageChange());
		return convertView;
    }
	
	private void InitImageView(View v) {
		         
		         bmpW = 50;
//		         bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.blueline)
//		                 .getWidth();// 获取图片宽度
		         DisplayMetrics dm = new DisplayMetrics();
		         getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		         int screenW = dm.widthPixels;// 获取分辨率宽度
		         offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		         Matrix matrix = new Matrix();
		         matrix.postTranslate(offset, 0);
		         cursor.setImageMatrix(matrix);// 设置动画初始位置
		     }	
	
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        System.out.println("ExampleFragment--onPause");
    }    

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("ExampleFragment--onResume");
    }

    @Override
    public void onStop()
    {
        // TODO Auto-generated method stub
        super.onStop();
        System.out.println("ExampleFragment--onStop");
    }	
    
	private class LabelOnClickListener implements OnClickListener {

		private Context context;
		
		public LabelOnClickListener(Context c) {
			context = c;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vPager.setCurrentItem(Integer.parseInt(v.getTag().toString()) - 1);
			for(int i = 0; i < article_fragment_hsv_child.getChildCount(); i++) {
				TextView v1 = (TextView)article_fragment_hsv_child.getChildAt(i);
				v1.setTextColor(context.getResources().getColor(R.color.title_black_color));				
			}
			((TextView) v).setTextColor(context.getResources().getColor(R.color.title_blue_color));
/*
 * 下面这段代码，是用动作控制图片的移动。
 */
/*
			int[] location = new  int[2] ;
			v.getLocationOnScreen(location);
			Animation animation = new TranslateAnimation(oldx, location[0], 0, 0);
			oldx =  location[0];
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
*/			
		}
		
	}
	
	private class CustomPageChange implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}		
	}
}