package mouse.test.fragment;

import java.util.ArrayList;
import java.util.Map;

import mouse.test.R;
import mouse.test.Test_Navi;
import mouse.test.adapter.Article_ChildFragment_Adapter;
import mouse.test.custominterface.CustomOnTouchListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Child1_NaviFragment_article extends Fragment implements OnGestureListener {

	private GestureDetector detector;
	private ViewFlipper flipper;
	private Button button;
	private Context context;
	private CustomOnTouchListener myOnTouchListener;
	ImageView []iamges=new ImageView[4];
	int i = 0;
	
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
			View convertView  = inflater.inflate(R.layout.article_child1_layout, container, false);
			context = convertView.getContext();
			iamges[0]=(ImageView) convertView.findViewById(R.id.imageview1);
			iamges[1]=(ImageView) convertView.findViewById(R.id.imageview2);
			iamges[2]=(ImageView) convertView.findViewById(R.id.imageview3);
			iamges[3]=(ImageView) convertView.findViewById(R.id.imageview4);
			
			detector = new GestureDetector(this);
			flipper = (ViewFlipper) convertView.findViewById(R.id.ViewFlipper1);
			flipper.addView(addImageView(R.drawable.bg_fulllevel));
			flipper.addView(addImageView(R.drawable.bg_marrow));
			flipper.addView(addImageView(R.drawable.bg_recommend));
			flipper.addView(addImageView(R.drawable.bg_top));
//			flipper.addView(addView());	
		    /* Fragment中，注册 
		    * 接收MainActivity的Touch回调的对象 
		    * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理 
		    */  
			myOnTouchListener = new CustomOnTouchListener() {  


				@Override  
				public boolean onTouch(MotionEvent ev) {  
					boolean result = detector.onTouchEvent(ev);  
					return result;  
				}  
			};  
			((Test_Navi)this.getActivity()).registerCustomOnTouchListener(myOnTouchListener); 
			return convertView;
	    }
		
		private View addImageView(int id) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(id);
			return iv;
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

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			System.out.println("in------------>>>>>>>");
			if (e1.getX() - e2.getX() > 120) {
				if (i < 3) {
					i++;
					setImage(i);
					flipper.setInAnimation(AnimationUtils.loadAnimation(context,
							R.anim.animation_right_in));
					flipper.setOutAnimation(AnimationUtils.loadAnimation(context,
							R.anim.animation_left_out));
					flipper.showNext();
				}
				return true;
			} 
			else if (e1.getX() - e2.getX() < -120) {
				if (i > 0) {
					i--;
					setImage(i);
					this.flipper.setInAnimation(AnimationUtils.loadAnimation(context,
							R.anim.animation_left_in));
					this.flipper.setOutAnimation(AnimationUtils.loadAnimation(context,
							R.anim.animation_right_out));
					this.flipper.showPrevious();
				}
				return true;
			}
			return false;
			
		}
		
		void setImage(int i)
		{
			for(int j=0;j<4;j++)
			{
				if(j!=i){
				iamges[j].setImageResource(R.drawable.navi_icon_oval);
				iamges[j].setAlpha(20);//0~255透明度值 
				android.view.ViewGroup.LayoutParams para;  
		        para = iamges[j].getLayoutParams();   
		          
		        para.height = 30;  
		        para.width = 30;  
		        iamges[j].setLayoutParams(para);  
				}
				else{
					iamges[j].setImageResource(R.drawable.navi_icon_oval);
					iamges[j].setAlpha(255);
				}
					
				
			}
		}	
}
