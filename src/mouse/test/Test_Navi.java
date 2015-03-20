package mouse.test;

import java.util.ArrayList;

import mouse.test.fragment.Main_NaviFragment_article;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 今天才搞明白，原来想在activity里面显示fragment，那么需要这个activity从FragmentActivity继承
 */
public class Test_Navi extends FragmentActivity {

	private ImageButton navi_imgbtn1;
	private ImageButton navi_imgbtn2;
	private ImageButton navi_imgbtn3;
	private ImageButton navi_imgbtn4;
	private ImageButton navi_imgbtn5;
	
	private TextView navi_tv1;
	private TextView navi_tv2;
	private TextView navi_tv3;
	private TextView navi_tv4;
	private TextView navi_tv5;
	
	private LinearLayout navi_ll_button1;
	private LinearLayout navi_ll_button2;
	private LinearLayout navi_ll_button3;
	private LinearLayout navi_ll_button4;
	private LinearLayout navi_ll_button5;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_navi_layout);
		
       Main_NaviFragment_article fragment = new Main_NaviFragment_article();
        changeFragment(fragment, "article");
		
		navi_imgbtn1 = (ImageButton)findViewById(R.id.navi_imgbtn1);
		navi_imgbtn2 = (ImageButton)findViewById(R.id.navi_imgbtn2);
		navi_imgbtn3 = (ImageButton)findViewById(R.id.navi_imgbtn3);
		navi_imgbtn4 = (ImageButton)findViewById(R.id.navi_imgbtn4);
		navi_imgbtn5 = (ImageButton)findViewById(R.id.navi_imgbtn5);
		
		navi_tv1 = (TextView)findViewById(R.id.navi_tv1);
		navi_tv2 = (TextView)findViewById(R.id.navi_tv2);
		navi_tv3 = (TextView)findViewById(R.id.navi_tv3);
		navi_tv4 = (TextView)findViewById(R.id.navi_tv4);
		navi_tv5 = (TextView)findViewById(R.id.navi_tv5);
	
		navi_ll_button1 = (LinearLayout)findViewById(R.id.navi_ll_button1);
		navi_ll_button2 = (LinearLayout)findViewById(R.id.navi_ll_button2);
		navi_ll_button3 = (LinearLayout)findViewById(R.id.navi_ll_button3);
		navi_ll_button4 = (LinearLayout)findViewById(R.id.navi_ll_button4);
		navi_ll_button5 = (LinearLayout)findViewById(R.id.navi_ll_button5);
		
		setButtonBackgroundBackImage();
		
		navi_ll_button1.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_ll_button2.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_ll_button3.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_ll_button4.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_ll_button5.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_imgbtn1.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_imgbtn2.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_imgbtn3.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_imgbtn4.setOnClickListener(new NaviBottomButtonClickListener(this));
		navi_imgbtn5.setOnClickListener(new NaviBottomButtonClickListener(this));		
	}
	
	private class NaviBottomButtonClickListener implements OnClickListener {

		private Context context;
		public NaviBottomButtonClickListener(Context c) {
			context = c;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setButtonBackgroundBackImage();
			switch(v.getId()) {
			case  R.id.navi_ll_button1:
			case  R.id.navi_imgbtn1:
					navi_imgbtn1.setBackgroundResource(R.drawable.nav_icon_article_p);
					//  下面这种方法是通过建立私有类的构造函数，把context传进来设置某个对象的值，也可以使用getBaseContext()，可以达到一样的效果，目前不知道差别是什么。
					//		navi_tv1.setTextColor(context.getResources().getColor(R.color.title_blue_color));
					navi_tv1.setTextColor(getBaseContext().getResources().getColor(R.color.title_blue_color));
				    Main_NaviFragment_article fragment = new Main_NaviFragment_article();
				    changeFragment(fragment, "article");
					break;
			case R.id.navi_ll_button2:
			case  R.id.navi_imgbtn2:
				navi_imgbtn2.setBackgroundResource(R.drawable.nav_icon_forum_p);
				navi_tv2.setTextColor(getBaseContext().getResources().getColor(R.color.title_blue_color));
				break;
			case R.id.navi_ll_button3:
			case  R.id.navi_imgbtn3:				
				navi_imgbtn3.setBackgroundResource(R.drawable.nav_icon_findcar_p);
				navi_tv3.setTextColor(getBaseContext().getResources().getColor(R.color.title_blue_color));
				break;
			case R.id.navi_ll_button4:
			case  R.id.navi_imgbtn4:				
				navi_imgbtn4.setBackgroundResource(R.drawable.nav_icon_sale_p);
				navi_tv4.setTextColor(getBaseContext().getResources().getColor(R.color.title_blue_color));
				break;
			case R.id.navi_ll_button5:
			case  R.id.navi_imgbtn5:				
				navi_imgbtn5.setBackgroundResource(R.drawable.nav_icon_my_p);
				navi_tv5.setTextColor(getBaseContext().getResources().getColor(R.color.title_blue_color));
				break;				
			}
		}
	}
	
	/*
	 * 设置底部导航栏的图片换成黑色，将导航栏的文字也换成黑色
	 */
	public void setButtonBackgroundBackImage() {
		
		navi_imgbtn1.setBackgroundResource(R.drawable.nav_icon_article_f);
		navi_imgbtn2.setBackgroundResource(R.drawable.nav_icon_forum_f);
		navi_imgbtn3.setBackgroundResource(R.drawable.nav_icon_findcar_f);
		navi_imgbtn4.setBackgroundResource(R.drawable.nav_icon_sale_f);
		navi_imgbtn5.setBackgroundResource(R.drawable.nav_icon_my_f);
		
		navi_tv1.setTextColor(getBaseContext().getResources().getColor(R.color.title_black_color));
		navi_tv2.setTextColor(getBaseContext().getResources().getColor(R.color.title_black_color));
		navi_tv3.setTextColor(getBaseContext().getResources().getColor(R.color.title_black_color));
		navi_tv4.setTextColor(getBaseContext().getResources().getColor(R.color.title_black_color));
		navi_tv5.setTextColor(getBaseContext().getResources().getColor(R.color.title_black_color));
	}
	
	private void changeFragment(Fragment f, String data) {
		/*
		 * 在程序中加入Fragment，这种是通过程序动态的增加，也可以直接在设计视图上面，增加Fragment
		 */
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
   
        fragmentTransaction.replace(R.id.navi_top_ll1, f);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        f.setArguments(bundle);
        fragmentTransaction.commit();
	}
}

/*	
	
	private Integer[] mNaviButtonState = { R.drawable.nav_icon_article_f,  
            R.drawable.nav_icon_article_p}; 
    Button mButton = (Button) findViewById(R.id.button);  
    mButton.setBackgroundDrawable(myButton.setbg(mButtonState));  

    public StateListDrawable setbg(Integer[] mImageIds) {  
            StateListDrawable bg = new StateListDrawable();  
            /*默认背景图*/
//            Drawable normal = this.getResources().getDrawable(mImageIds[0]);  
            /*选择时的背景图*/
//            Drawable selected = this.getResources().getDrawable(mImageIds[1]);  
            /*按下时的背景图*/
//            Drawable pressed = this.getResources().getDrawable(mImageIds[2]);  
            /*背景图绑定按钮各个状态*/
/*
            bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);  
            bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);  
            bg.addState(View.ENABLED_STATE_SET, normal);  
            bg.addState(View.FOCUSED_STATE_SET, selected);  
            bg.addState(View.EMPTY_STATE_SET, normal);  
            return bg;  
    } 
*/
