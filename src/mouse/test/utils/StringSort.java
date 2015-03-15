package mouse.test.utils;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Comparator;  
import java.util.ArrayList;  
import java.util.Collections; 
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StringSort {
	
	public static String DictSort(String token, String timestamp, String nonce) {
	
	ArrayList list = new ArrayList();  
	  list.add(token);  
	  list.add(timestamp);  
	  list.add(nonce);    
	  /* 
	   * 运用Collections的sort（）方法对其进行排序 sort（）方法需要传 连个参数，一个是需要进行排序的Collection 另一个是一个Comparator 
	   */  
	  Collections.sort(list);
	return list.get(0).toString() + list.get(1).toString() + list.get(2).toString();
	}
}


