/*
 * 这个类里面实现了两个功能的核心代码，在以后使用的时候，最好的方式是做成单独继承ListView来使用。以下收藏一些链接地址，方便以后
 * 使用的时候进行查阅
 * http://blog.csdn.net/janronehoo/article/details/7259955
 * http://blog.csdn.net/toyuexinshangwan/article/details/8194044
 * http://blog.csdn.net/cherry609195946/article/details/8843712
 * http://www.cnblogs.com/shang53880/p/3442014.html
 * http://www.cnblogs.com/xiaoran1129/archive/2012/07/04/2576221.html
 */

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
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
	private TextView tvRefreshState;
	private TextView tvRefreshDate;
	private ProgressBar pbRefreshImg;
	private ImageView iv_Bottom_Refresh_Icon;
	private TextView tv_Bottom_Refresh_Text;
	int i = 0;
	private float starty, endy, movelen; 
	private View headView1, headView2, footView1; 
	private int headView1Height, headView2Height;
	
	//因为涉及到handler数据处理，为方便我们定义如下常量
	private final static int REFRESH_BACKING = 0;      //反弹中
	private final static int REFRESH_BACED = 1;        //达到刷新界限，反弹结束后
	private final static int REFRESH_RETURN = 2;       //没有达到刷新界限，返回
	private final static int REFRESH_DONE = 3;         //加载数据结束	
	
	
	private final static int NONE_PULL_REFRESH = 0;   //正常状态
	private final static int ENTER_PULL_REFRESH = 1;  //进入下拉刷新状态
	private final static int OVER_PULL_REFRESH = 2;   //进入松手刷新状态
	private final static int EXIT_PULL_REFRESH = 3;     //松手后反弹后加载状态
	private int mPullRefreshState = 0;  
	
	private int lastIndex, countRecord;
	private AnimationDrawable animationDrawable;
	private boolean isAddFoot = false;
	
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
		
			headView1  = inflater.inflate(R.layout.article_child1_headivew_layout, null);
			context = headView1.getContext();
			iamges[0]=(ImageView) headView1.findViewById(R.id.imageview1);
			iamges[1]=(ImageView) headView1.findViewById(R.id.imageview2);
			iamges[2]=(ImageView) headView1.findViewById(R.id.imageview3);
			iamges[3]=(ImageView) headView1.findViewById(R.id.imageview4);
			
			detector = new GestureDetector(this);
			flipper = (ViewFlipper) headView1.findViewById(R.id.ViewFlipper1);
			flipper.addView(addImageView(R.drawable.bg_fulllevel));
			flipper.addView(addImageView(R.drawable.bg_marrow));
			flipper.addView(addImageView(R.drawable.bg_recommend));
			flipper.addView(addImageView(R.drawable.bg_top));
			setImage(0);	
			
			headView2  = inflater.inflate(R.layout.article_child1_refresh_headview_layout, null);			
			tvRefreshState = (TextView)headView2.findViewById(R.id.tvRefreshState);
			tvRefreshDate = (TextView)headView2.findViewById(R.id.tvRefreshDate);
			pbRefreshImg = (ProgressBar)headView2.findViewById(R.id.pbRefreshImg);
			
			footView1 = inflater.inflate(R.layout.article_child1_loading_bottomview_layout, null);
			iv_Bottom_Refresh_Icon = (ImageView)footView1.findViewById(R.id.iv_Bottom_Refresh_Icon);
			tv_Bottom_Refresh_Text = (TextView)footView1.findViewById(R.id.tv_Bottom_Refresh_Text);

			lv1.addHeaderView(headView2, null, false);
			lv1.addHeaderView(headView1, null, false);
			
			lv1.setAdapter(sa1);

			measureView(headView1);
			measureView(headView2);
/*			
			ViewTreeObserver observer = headView1.getViewTreeObserver();    
			observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {    
			           @Override    
			           public void onGlobalLayout() {    
			        	   headView1.getViewTreeObserver().removeGlobalOnLayoutListener(this);    
			//    final int w = view.getMeasuredWidth();  
			            headView1Height = headView1.getMeasuredHeight();  
			           }    
			       }); 			
*/			
			headView1Height = headView1.getMeasuredHeight();
			Log.d("headView1Height", String.valueOf(headView1Height));
			headView2Height = headView2.getMeasuredHeight();
	//		ch = 300;
			Log.d("headView2Height", String.valueOf(headView2Height));
			headView2.setPadding(0,  -1 * headView2Height, 0, 0);			
			pbRefreshImg.setMax(headView2Height);
			
			lv1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					//return false;
					float i = 0;
					float j = 0;

					
					if (event.getY() <= headView1Height){
						vg.requestDisallowInterceptTouchEvent(true);
						boolean result = detector.onTouchEvent(event);  
						return result;  
					}						
					else
					{
						vg.requestDisallowInterceptTouchEvent(false);
						switch(event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							starty = event.getY();
							break;
						case MotionEvent.ACTION_MOVE:
							endy = event.getY();
							Log.d("x", String.valueOf(starty));
							Log.d("y", String.valueOf(endy));
							i = endy - starty;
							if (i > 0) {
//								headView2.setPadding(0, (int) (-1 * headView2Height + i), 0, 0);
								j = headView2Height / 2;
								if(i - j >= 0){
									if(i - j <= headView2Height) {
										headView2.setPadding(0, (int) (-1 * headView2Height + i), 0, 0);
										pbRefreshImg.setProgress((int) (i - j));
										tvRefreshState.setText("向下拉将刷新数据");
									}
									else {
										headView2.setPadding(0, headView2.getPaddingTop(), 0, 0);										
										pbRefreshImg.setProgress(headView2Height);
										tvRefreshState.setText("松开手将刷新数据");
									}
								}
							Log.d("i", String.valueOf(i));
							};
							break;
						case MotionEvent.ACTION_UP:
							endy = event.getY();
							i = endy - starty;
							if(i > 0) {
							j = headView2Height / 2;
							if (i - j >= headView2Height) {
								//刷新数据
							//	pbRefreshImg.setProgress(80);
							}
							new Thread() {
			                    public void run() {
			                    	Message msg;
			                    	movelen = headView2.getPaddingTop();
									while(headView2.getPaddingTop() > -1 * headView2Height) {
//										headView2.setPadding(0, (int) endy--, 0, 0);
										if(headView2.getPaddingTop() <= headView2Height) {
											int x = headView2.getPaddingTop();
											pbRefreshImg.setProgress(x);
										}
										msg = mHandler.obtainMessage();
			                            msg.what = REFRESH_BACKING;
			                            mHandler.sendMessage(msg);
			                            try {
			                                sleep(2);//慢一点反弹，别一下子就弹回去了
			                            } catch (InterruptedException e) {
			                                e.printStackTrace();
			                            }
			                        }
			                        msg = mHandler.obtainMessage();
			                        if (mPullRefreshState == OVER_PULL_REFRESH) {
			                            msg.what = REFRESH_BACED;//加载数据完成，结束返回
			                        } else {
			                            msg.what = REFRESH_RETURN;//未达到刷新界限，直接返回
			                        }
			                        mHandler.sendMessage(msg);
			                    };
			                }.start();							

							};
							break;
						}						
						return false;
					}
				}				
			});
			
			lv1.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					lastIndex = lv1.getLastVisiblePosition() + 1;
					countRecord = totalItemCount;
					Log.i("lastIndex", String.valueOf(lastIndex));
					Log.i("countRecord", String.valueOf(countRecord));
					if(lastIndex == countRecord && !isAddFoot) {
						lv1.addFooterView(footView1);
						isAddFoot = true;
						iv_Bottom_Refresh_Icon.setImageResource(R.anim.animation_loading);         
						animationDrawable = (AnimationDrawable) iv_Bottom_Refresh_Icon.getDrawable();  
						animationDrawable.start(); 
						tv_Bottom_Refresh_Text.setText("数据刷新中，请稍候。。。");
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
		
		private Handler mHandler = new Handler(){
		    @Override
		    public void handleMessage(Message msg) {
		        switch (msg.what) {
		        case REFRESH_BACKING:
		            headView2.setPadding(headView2.getPaddingLeft(),
		                    (int) (movelen--),
		                    headView2.getPaddingRight(),
		                    headView2.getPaddingBottom());
		            break;
		        case REFRESH_BACED:
/*
		        	mHeaderTextView.setText("正在加载...");
		            mHeaderProgressBar.setVisibility(View.VISIBLE);
		            mHeaderPullDownImageView.setVisibility(View.GONE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);
		            mPullRefreshState = EXIT_PULL_REFRESH;
		            new Thread() {
		                public void run() {
		                    sleep(2000);//处理后台加载数据
		                    Message msg = mHandler.obtainMessage();
		                    msg.what = REFRESH_DONE;
		                    //通知主线程加载数据完成
		                    mHandler.sendMessage(msg);
		                };
		            }.start();
		       */
		            break;
		        case REFRESH_RETURN:
		            //未达到刷新界限，返回
		        	/*
		            mHeaderTextView.setText("下拉刷新");
		            mHeaderProgressBar.setVisibility(View.INVISIBLE);
		            mHeaderPullDownImageView.setVisibility(View.VISIBLE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);
		            mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
		                    0,
		                    mHeaderLinearLayout.getPaddingRight(),
		                    mHeaderLinearLayout.getPaddingBottom());
		            mPullRefreshState = NONE_PULL_REFRESH;
		            setSelection(1);
		            */
		            break;
		        case REFRESH_DONE:
		            //刷新结束后，恢复原始默认状态
		        	/*
		            mHeaderTextView.setText("下拉刷新");
		            mHeaderProgressBar.setVisibility(View.INVISIBLE);
		            mHeaderPullDownImageView.setVisibility(View.VISIBLE);
		            mHeaderReleaseDownImageView.setVisibility(View.GONE);
		            mHeaderUpdateText.setText(getContext().getString(R.string.app_list_header_refresh_last_update, 
		                    mSimpleDateFormat.format(new Date())));
		            mHeaderLinearLayout.setPadding(mHeaderLinearLayout.getPaddingLeft(),
		                    0,
		                    mHeaderLinearLayout.getPaddingRight(),
		                    mHeaderLinearLayout.getPaddingBottom());
		            mPullRefreshState = NONE_PULL_REFRESH;
		            setSelection(1);
		            */
		            break;
		        default:
		            break;
		        }
		    }
		};		
		
		private void measureView(View child) {
			ViewGroup.LayoutParams p = child.getLayoutParams();
			if (p == null) {
				p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
			int lpHeight = p.height;
			int childHeightSpec;
			if (lpHeight > 0) {
				childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
						MeasureSpec.EXACTLY);
			} else {
				childHeightSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
			child.measure(childWidthSpec, childHeightSpec);
		}
		
		private List<Map<String, Object>> get_lv1_Data() {
			lv1_data = new ArrayList<Map<String, Object>>();
			for(int i = 1; i < 10; i++){
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
			if(e1 == null || e2 == null)
				return false;
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
