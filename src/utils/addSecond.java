package utils;

import java.sql.Timestamp;

public class addSecond {
	public  Timestamp addOneSecond(Timestamp date) {
		long second=date.getTime()+1000;
		Timestamp cdate=new Timestamp(second);
		return cdate;
}
	/* public static void main(String[] args) {
		 addSecond asd=new addSecond();
		Timestamp a=new Timestamp(System.currentTimeMillis());
		Timestamp b=asd.addOneSecond(a);
		System.out.println(b);
		
	 }*/
}

