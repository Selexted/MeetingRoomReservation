package test1;
import java.util.ArrayList;
import java.util.List;

public class Date {
	List<Room> rooms;
	String day;
	public Date(String day) {
		rooms = new ArrayList<Room>();
		this.day = day;
	};
	
	public void addRoom(Room r) {
		rooms.add(r);
	}
	
	public int getIndexOfRoom(String name) {
		for(int i=0; i<rooms.size(); i++) {
			if(name.equals(rooms.get(i).name)) {
				return i;
			}
		}
		return -1;
	}
	
	public void show(int iR, int iM) {
		System.out.print("날짜: " + this.day + " ");
		rooms.get(iR).show(iM);
	}
}
