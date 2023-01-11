package test1;

public class Meeting {
	String[] time;
	String[] member;
	String host;
	int count;
	
	
	public Meeting(String t, String m, String h) {
		time = t.split(",");
		member = m.split(",");
		host = h;
		count = member.length;
	};
	
	public void show() {
		System.out.print("  시간: " + time[0] + ":00 ~ " + (Integer.parseInt(time[time.length-1])+1) + ":00 ");
		System.out.print("  참가자: ");
		for(int i=0; i<member.length; i++)
			System.out.print(member[i] + " ");
		System.out.print("  예약자: " + host);
		System.out.println("");
	}
	
	public String getTime() {
		String str = "";
		for(int i=0; i<time.length; i++) {
			str += time[i] + ",";
		}
		return str.substring(0,str.length()-1);
	}
	
	public String getMember() {
		String str = "";
		for(int i=0; i<member.length; i++) {
			str += member[i] + ",";
		}
		return str.substring(0,str.length()-1);
	}
}
