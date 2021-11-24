package junit_test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import coordinate.GetCoordinate;
import utils.addSecond;

public class coordinateTest {
	
        @Test
		public void testCoordinate() throws Exception {
			GetCoordinate s = new GetCoordinate();
			s.getValueOfR1();
			s.getValueOfR2();
			double[] xy = new double[2];
			xy = s.getFinalCoordinates( "A2 00 41 45 31 07 01 54 15 40 75 00 ", "2018-10-19 15:12:31", 3);	//注意iid 后面有空格		
			System.out.println(xy[0] + " " + xy[1]);
			
		}
        @Test
		public void testaddSecond() throws Exception {
			addSecond a=new addSecond();
			Timestamp date=new Timestamp(0);
			System.out.println(a.addOneSecond(date));
		}
      @Test 
        public  void  test() throws Exception{
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(); 
			String Date=df.format((date));
			Timestamp createDate = Timestamp.valueOf(Date);
			System.out.println(Date);// //2018-08-21 08:14:25
			System.out.println(createDate);//2018-08-21 08:14:25.0
      }
}
