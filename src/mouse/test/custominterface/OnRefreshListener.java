package mouse.test.custominterface;

/** 
 * Interface definition for a callback to be invoked when list should be 
 * refreshed. 
 * 接口定义一个回调方法当列表应当被刷新 
 */  
public interface OnRefreshListener {  
    /** 
     * Called when the list should be refreshed. 
     * 当列表应当被刷新是调用这个方法 
     * <p> 
     * A call to {@link PullToRefreshListView #onRefreshComplete()} is 
     * expected to indicate that the refresh has completed. 
     */  
    public void onRefresh();  
      
    public void onLoadMore();  
} 
