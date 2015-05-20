package mouse.test.customview;
//http://smallwoniu.blog.51cto.com/3911954/1308959
import mouse.test.R;
import mouse.test.custominterface.OnRefreshListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class CustomListView extends ListView implements OnScrollListener {
	//,
//		OnGestureListener {
//	private GestureDetector detector;
	private ViewFlipper flipper;
	private TextView tvRefreshState;
	private TextView tvRefreshDate;
	private ProgressBar pbRefreshImg;
	private ImageView iv_Bottom_Refresh_Icon;
	private TextView tv_Bottom_Refresh_Text;
	private View headView1, headView2, footView1;
	private int headView1Height, headView2Height;
	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;
	private LayoutInflater inflater;
	private int lastIndex, countRecord;
	private int currentIndexImg = 0;
	private AnimationDrawable animationDrawable;
	private boolean isAddFoot = false;
	private LinearLayout imgContainer;
	private ImageView[] imgList;
	private float starty, endy, movelen;
	private float startx, endx, movelenx;
	private boolean isMove; //确定有横向移动.
	// 因为涉及到handler数据处理，为方便我们定义如下常量
	private final static int REFRESH_BACKING = 0; // 反弹中
	private final static int REFRESH_BACED = 1; // 达到刷新界限，反弹结束后
	private final static int REFRESH_RETURN = 2; // 没有达到刷新界限，返回
	private final static int REFRESH_DONE = 3; // 加载数据结束

	private final static int NONE_PULL_REFRESH = 0; // 正常状态
	private final static int ENTER_PULL_REFRESH = 1; // 进入下拉刷新状态
	private final static int OVER_PULL_REFRESH = 2; // 进入松手刷新状态
	private final static int EXIT_PULL_REFRESH = 3; // 松手后反弹后加载状态
	private int mPullRefreshState = 0;

	/*
	 * 是否显示图片导航
	 */
	private boolean IsShowNaviImgHeadView;
	/*
	 * 是否显示刷新
	 */
	private boolean IsShowRefreshHeadView;
	/*
	 * 是否显示加载中
	 */
	private Boolean IsShowLoadingFootView;
	private OnRefreshListener mOnRefreshListener; // 刷新监听器
	// private OnScrollListener mOnScrollListener; // 列表滚动监听器

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init(context);
	}

	private void Init(Context context) {
		inflater = LayoutInflater.from(context);
//		detector = new GestureDetector(this);
		headView1 = inflater.inflate(R.layout.refresh_headview_layout, null);
		tvRefreshState = (TextView) headView1.findViewById(R.id.tvRefreshState);
		tvRefreshDate = (TextView) headView1.findViewById(R.id.tvRefreshDate);
		pbRefreshImg = (ProgressBar) headView1.findViewById(R.id.pbRefreshImg);

		headView2 = inflater.inflate(R.layout.navi_img_headivew_layout, null);
		flipper = (ViewFlipper) headView2.findViewById(R.id.ViewFlipper1);
		imgContainer = (LinearLayout) headView2.findViewById(R.id.imgContainer);
		// flipper.addView(addImageView(R.drawable.bg_fulllevel));
		// flipper.addView(addImageView(R.drawable.bg_marrow));
		// flipper.addView(addImageView(R.drawable.bg_recommend));
		// flipper.addView(addImageView(R.drawable.bg_top));

		footView1 = inflater.inflate(R.layout.loading_bottomview_layout, null);
		iv_Bottom_Refresh_Icon = (ImageView) footView1
				.findViewById(R.id.iv_Bottom_Refresh_Icon);
		tv_Bottom_Refresh_Text = (TextView) footView1
				.findViewById(R.id.tv_Bottom_Refresh_Text);
		// super.setOnScrollListener(this);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// return true;
		float i = 0;
		float j = 0;
		Log.d("headview2x", String.valueOf(headView2.getPivotX()));
		Log.d("headview2y", String.valueOf(headView2.getPivotY()));
		float eventgety, headview2getpivoty;
		eventgety = event.getY();
		headview2getpivoty = headView2.getMeasuredHeight();
		if (event.getY() <= headview2getpivoty) {
			if (IsShowNaviImgHeadView) {
//				requestDisallowInterceptTouchEvent(true);
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startx = event.getX();
					isMove = false;
					break;
				case MotionEvent.ACTION_MOVE:
						isMove = true;
					break;
				case MotionEvent.ACTION_UP:
					if (isMove) {
						endx = event.getX();
						if (endx > startx) {
							if (endx - startx > 120) {
								if (currentIndexImg < imgList.length - 1) {
									currentIndexImg++;
								} else {
									currentIndexImg = 0;
								}
								setImage(currentIndexImg);
								flipper.setInAnimation(AnimationUtils
										.loadAnimation(getContext(),
												R.anim.animation_right_in));
								flipper.setOutAnimation(AnimationUtils
										.loadAnimation(getContext(),
												R.anim.animation_left_out));
								flipper.showNext();
							}
						}
						else
						{
							if (endx - startx < -120) {
								if (currentIndexImg > 0) {
									currentIndexImg--;
								} else {
									currentIndexImg = imgList.length - 1;
								}
								setImage(currentIndexImg);
								this.flipper.setInAnimation(AnimationUtils
										.loadAnimation(getContext(),
												R.anim.animation_left_in));
								this.flipper.setOutAnimation(AnimationUtils
										.loadAnimation(getContext(),
												R.anim.animation_right_out));
								this.flipper.showPrevious();
							}
						}
					}
					isMove = false;
					break;
				}
/*
				requestDisallowInterceptTouchEvent(true);
				boolean result = detector.onTouchEvent(event);
				return result;
*/				
			}
		} else {
			if (this.IsShowRefreshHeadView) {
//					requestDisallowInterceptTouchEvent(false);
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						starty = event.getY();
						break;
					case MotionEvent.ACTION_MOVE:
						endy = event.getY();
						Log.d("x", String.valueOf(starty));
						Log.d("y", String.valueOf(endy));
						i = endy - starty;
						if (i > 0) {
							// headView2.setPadding(0, (int) (-1 * headView2Height +
							// i), 0, 0);
							j = headView1Height / RATIO;
							if (i - j >= 0) {
								if (i - j <= headView1Height) {
									headView1.setPadding(0, (int) (-1
											* headView1Height + i), 0, 0);
									pbRefreshImg.setProgress((int) (i - j));
									tvRefreshState.setText("向下拉将刷新数据");
								} else {
									headView1.setPadding(0,
											headView1.getPaddingTop(), 0, 0);
									pbRefreshImg.setProgress(headView1Height);
									tvRefreshState.setText("松开手将刷新数据");
								}
							}
							Log.d("i", String.valueOf(i));
						}
						;
						break;
					case MotionEvent.ACTION_UP:
						endy = event.getY();
						i = endy - starty;
						if (i > 0) {
							j = headView1Height / 2;
							if (i - j >= headView1Height) {
								// 刷新数据
								// pbRefreshImg.setProgress(80);
							}
							new Thread() {
								public void run() {
									Message msg;
									movelen = headView1.getPaddingTop();
									while (headView1.getPaddingTop() > -1
											* headView1Height) {
										// headView2.setPadding(0, (int) endy--, 0,
										// 0);
										if (headView1.getPaddingTop() <= headView1Height) {
											int x = headView1.getPaddingTop();
											pbRefreshImg.setProgress(x);
										}
										msg = mHandler.obtainMessage();
										msg.what = REFRESH_BACKING;
										mHandler.sendMessage(msg);
										try {
											sleep(2);// 慢一点反弹，别一下子就弹回去了
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									msg = mHandler.obtainMessage();
									if (mPullRefreshState == OVER_PULL_REFRESH) {
										msg.what = REFRESH_BACED;// 加载数据完成，结束返回
									} else {
										msg.what = REFRESH_RETURN;// 未达到刷新界限，直接返回
									}
									mHandler.sendMessage(msg);
								};
							}.start();
						}
						break;
					}
		//			return true;
				}				
		}		
		return super.onTouchEvent(event);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_BACKING:
				headView1.setPadding(headView1.getPaddingLeft(),
						(int) (movelen--), headView1.getPaddingRight(),
						headView1.getPaddingBottom());
				break;
			case REFRESH_BACED:
				/*
				 * mHeaderTextView.setText("正在加载...");
				 * mHeaderProgressBar.setVisibility(View.VISIBLE);
				 * mHeaderPullDownImageView.setVisibility(View.GONE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 * mPullRefreshState = EXIT_PULL_REFRESH; new Thread() { public
				 * void run() { sleep(2000);//处理后台加载数据 Message msg =
				 * mHandler.obtainMessage(); msg.what = REFRESH_DONE;
				 * //通知主线程加载数据完成 mHandler.sendMessage(msg); }; }.start();
				 */
				break;
			case REFRESH_RETURN:
				// 未达到刷新界限，返回
				/*
				 * mHeaderTextView.setText("下拉刷新");
				 * mHeaderProgressBar.setVisibility(View.INVISIBLE);
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 * mHeaderLinearLayout
				 * .setPadding(mHeaderLinearLayout.getPaddingLeft(), 0,
				 * mHeaderLinearLayout.getPaddingRight(),
				 * mHeaderLinearLayout.getPaddingBottom()); mPullRefreshState =
				 * NONE_PULL_REFRESH; setSelection(1);
				 */
				break;
			case REFRESH_DONE:
				// 刷新结束后，恢复原始默认状态
				/*
				 * mHeaderTextView.setText("下拉刷新");
				 * mHeaderProgressBar.setVisibility(View.INVISIBLE);
				 * mHeaderPullDownImageView.setVisibility(View.VISIBLE);
				 * mHeaderReleaseDownImageView.setVisibility(View.GONE);
				 * mHeaderUpdateText.setText(getContext().getString(R.string.
				 * app_list_header_refresh_last_update,
				 * mSimpleDateFormat.format(new Date())));
				 * mHeaderLinearLayout.setPadding
				 * (mHeaderLinearLayout.getPaddingLeft(), 0,
				 * mHeaderLinearLayout.getPaddingRight(),
				 * mHeaderLinearLayout.getPaddingBottom()); mPullRefreshState =
				 * NONE_PULL_REFRESH; setSelection(1);
				 */
				break;
			default:
				break;
			}
		}
	};

	/**
	 * Register a callback to be invoked when this list should be refreshed.
	 * 注册监听器
	 * 
	 * @param onRefreshListener
	 *            The callback to run.
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	private ImageView addImageView(int id) {
		ImageView iv = new ImageView(getContext());
		iv.setImageResource(id);
		return iv;
	}

	/*
	 * 计算HeadView高度的方法.
	 */
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

	/*
	 * 设置导航显示的图片资源 source 为本地资源文件的ID数组
	 */
	public void SetNaviImg(int[] source) {
		imgContainer.removeAllViews();
		LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lp0.setMargins(0, 0, 10, 0);
		imgList = new ImageView[source.length];
		for (int i = 0; i < source.length; i++) {
			flipper.addView(addImageView(source[i]));
			imgList[i] = new ImageView(getContext());
			imgContainer.addView(imgList[i], lp0);
		}
		setImage(0);
		headView2Height = headView2.getMeasuredHeight();
	}

	private void setImage(int i) {
		for (int j = 0; j < imgList.length; j++) {
			if (j != i) {
				imgList[j].setImageResource(R.drawable.navi_icon_oval);
				imgList[j].setAlpha(50);// 0~255透明度值
				android.view.ViewGroup.LayoutParams para;
				para = imgList[j].getLayoutParams();

				para.height = 10;
				para.width = 10;
				imgList[j].setLayoutParams(para);
			} else {
				imgList[j].setImageResource(R.drawable.navi_icon_oval);
				imgList[j].setAlpha(255);
			}
		}
	}

	/*
	 * 设置导航显示的图片资源 source 为通过其他方式获取的图片数组
	 */
	public void SetNaviImg(Bitmap[] source) {

	}

	public boolean isIsShowNaviImgHeadView() {
		return IsShowNaviImgHeadView;
	}

	public void setIsShowNaviImgHeadView(boolean isShowNaviImgHeadView) {
		IsShowNaviImgHeadView = isShowNaviImgHeadView;
		if (isShowNaviImgHeadView) {
			this.addHeaderView(headView2, null, false);
			measureView(headView2);
			headView2Height = headView2.getMeasuredHeight();
		}
	}

	public boolean isIsShowRefreshHeadView() {
		return IsShowRefreshHeadView;
	}

	public void setIsShowRefreshHeadView(boolean isShowRefreshHeadView) {
		IsShowRefreshHeadView = isShowRefreshHeadView;
		if (isShowRefreshHeadView) {
			addHeaderView(headView1, null, false);
			measureView(headView1);
			headView1Height = headView1.getMeasuredHeight();
			headView1.setPadding(0, -1 * headView1Height, 0, 0);
			pbRefreshImg.setMax(headView1Height);
		}
	}

	public Boolean getIsShowLoadingFootView() {
		return IsShowLoadingFootView;
	}

	public void setIsShowLoadingFootView(Boolean isShowLoadingFootView) {
		IsShowLoadingFootView = isShowLoadingFootView;
		setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (IsShowLoadingFootView) {
			lastIndex = getLastVisiblePosition() + 1;
			countRecord = totalItemCount;
			Log.i("lastIndex", String.valueOf(lastIndex));
			Log.i("countRecord", String.valueOf(countRecord));
			if (lastIndex == countRecord && !isAddFoot) {
				addFooterView(footView1);
				isAddFoot = true;
				iv_Bottom_Refresh_Icon
						.setImageResource(R.anim.animation_loading);
				animationDrawable = (AnimationDrawable) iv_Bottom_Refresh_Icon
						.getDrawable();
				animationDrawable.start();
				tv_Bottom_Refresh_Text.setText("数据刷新中，请稍候。。。");
			}
		}
	}

/*	
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
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
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
		// TODO Auto-generated method stub
		System.out.println("in------------>>>>>>>");
		if (e1 == null || e2 == null)
			return false;
		Log.d("classname", this.getClass().getName());
		if (!this.getClass().getName()
				.equals("mouse.test.fragment.Child1_NaviFragment_article"))
			return false;
		if (e1.getX() - e2.getX() > 120) {

			if (currentIndexImg < 3) {
				currentIndexImg++;
			} else {
				currentIndexImg = 0;
			}
			setImage(currentIndexImg);
			flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.animation_right_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.animation_left_out));
			flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			if (currentIndexImg > 0) {
				currentIndexImg--;
				setImage(currentIndexImg);
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.animation_left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.animation_right_out));
				this.flipper.showPrevious();
			}
			return true;
		}
		return false;
	}
	

@Override
public boolean dispatchTouchEvent(MotionEvent event)
{
     if(detector.onTouchEvent(event))
     {
            event.setAction(MotionEvent.ACTION_CANCEL);
     }
     return super.dispatchTouchEvent(event);
}

*/
}
