package obtain_logistics_info;

import java.util.HashMap;

import com.ehaoyao.logistics.common.vo.WayBillTransVo;

public class Test {

	public static void main(String[] args) {
		WayBillTransVo vo1 = new WayBillTransVo();
		vo1.setWayBillSourceNum("1a");
		vo1.setOrderFlagNum("2b");
		WayBillTransVo vo2 = new WayBillTransVo();
		vo2.setWayBillSourceNum("1a");
		vo2.setOrderFlagNum("2bb");
		
		WayBillTransVo vo3 = null;
		WayBillTransVo vo4 = null;
		
		HashMap<WayBillTransVo, Integer> hashMap = new HashMap<WayBillTransVo, Integer>();  
        hashMap.put(vo1, 1);
        hashMap.put(vo2, 2); 
        
		System.out.println(vo1.hashCode());
		System.out.println(vo2.hashCode());
		System.out.println(vo1.equals(vo2));
		System.out.println(hashMap.get(new WayBillTransVo("1a", "2b", null))+"--"+hashMap.get(vo2));
	}
}
