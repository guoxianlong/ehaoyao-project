package obtain_logistics_info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 有返回值的线程
 */
public class Test {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date upLoadTime;
		try {
			upLoadTime = sdf.parse("2016-05-06 19:00:43");
			Date lastTime = sdf.parse("2016-05-06 19:01:43");
			if (lastTime != null && (upLoadTime.before(lastTime)
					|| upLoadTime.equals(lastTime))) {
				System.out.println("11");
			}else{
				System.out.println("22");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
