package mouse.test.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
 
public class Child1_NaviFragment_article extends Fragment implements OnGestureListener {

	private GestureDetector detector;
	private ViewFlipper flipper;
	private Button button;
	private Context context;
	private CustomOnTouchListener myOnTouchListener;
	private ListView lv1;
	private List<Map<String, Object>>lv1_data;	
	ImageView []iamges=new ImageView[4];
	int i = 0;
	private float starty, endy, movelen; 
	
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
			final ViewGroup vg = container;
			View articleChildl1View = inflater.inflate(R.layout.article_child1_layout, container, false);
			context = inflater.getContext();
			lv1 = (ListView)articleChildl1View.findViewById(R.id.lv_CarInfo);
			SimpleAdapter sa1 = new SimpleAdapter(this.context, get_lv1_Data(), R.layout.article_child1_simpleadapter, 
					new String[] {"head", "title", "date", "reply"}, new int[] {R.id.iv_head, R.id.tvTitle, R.id.tvDate, R.id.tvReply});
		
			View convertView  = inflater.inflate(R.layout.article_child1_headivew_layout, null);
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
			setImage(0);			
		
			int  ch = convertView.getMeasuredHeight();
			ch = 300;
			Log.d("height", String.valueOf(ch));
			convertView.setPadding(0,  -1 * ch, 0, 0);
			lv1.addHeaderView(convertView, null, false);
			lv1.setAdapter(sa1);
			lv1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					//return false;
					
					switch(event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							starty = event.getY();
							break;
						case MotionEvent.ACTION_MOVE:
							endy = event.getY();
							if (starty - endy <= 150) {
								
							}
							else {
								
							}
							break;
						case MotionEvent.ACTION_UP:
							break;
					}
					
					if (event.getY() <= 300){
						vg.requestDisallowInterceptTouchEvent(true);
						boolean result = detector.onTouchEvent(event);  
						return result;  
					}						
					else
					{
						vg.requestDisallowInterceptTouchEvent(false);
						
						return false;
					}


				}				
			});
//			((ViewParent) getParentFragment()).requestDisallowInterceptTouchEvent(true);
//			flipper.addView(addView());	
		    /* Fragment中，注册 
		    * 接收MainActivity的Touch回调的对象 
		    * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理 
		    */  
			/*
			myOnTouchListener = new CustomOnTouchListener() {  


				@Override  
				public boolean onTouch(MotionEvent ev) {
					Log.d("touchx",String.valueOf(ev.getX()));
					Log.d("touchy",String.valueOf(ev.getY()));
					if (ev.getY() <= 300){
						vg.requestDisallowInterceptTouchEvent(true);
						boolean result = detector.onTouchEvent(ev);  
						return result;  
					}						
					else
					{
						vg.requestDisallowInterceptTouchEvent(false);
						return false;
					}
	//		          super.dispatchTouchEvent(ev);  
	//		          commOnTouchEvent(ev);  //进行子View手势的相应操作  
	//		          return true;  				

				}  
			};  
//			container.requestDisallowInterceptTouchEvent(true);
//			((ViewParent) getParentFragment()) .requestDisallowInterceptTouchEvent(true);
	    	((Test_Navi)this.getActivity()).registerCustomOnTouchListener(myOnTouchListener); 
*/    	
//			return convertView;
			return articleChildl1View;
	    }
		
		private List<Map<String, Object>> get_lv1_Data() {
			lv1_data = new ArrayList<Map<String, Object>>();
			for(int i = 1; i < 25; i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("head", R.drawable.test_head);
				map.put("title", "1500434000" + String.valueOf(i));
				long time=System.currentTimeMillis();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");  
				String t=format.format(new Date(time));
				map.put("date", t);
				map.put("reply", 50);
				lv1_data.add(map);
			}
			return lv1_data;
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
			Log.d("classname", this.getClass().getName());
			if(!this.getClass().getName() .equals("mouse.test.fragment.Child1_NaviFragment_article"))
				return false;
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
		
		private void setImage(int i)
		{
			for(int j=0;j<4;j++)
			{
				if(j!=i){
				iamges[j].setImageResource(R.drawable.navi_icon_oval);
				iamges[j].setAlpha(50);//0~255透明度值 
				android.view.ViewGroup.LayoutParams para;  
		        para = iamges[j].getLayoutParams();   
		          
		        para.height = 10;  
		        para.width = 10;  
		        iamges[j].setLayoutParams(para);  
				}
				else{
					iamges[j].setImageResource(R.drawable.navi_icon_oval);
					iamges[j].setAlpha(255);
				}
			}
		}	
}
