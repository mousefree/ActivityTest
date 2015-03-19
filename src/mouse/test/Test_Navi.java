package mouse.test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Test_Navi extends Activity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_navi_layout);
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
	}

/*	
	Integer[] mButtonState = { R.drawable.nav_icon_article_f,  
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
}
