package test1;
import java.util.ArrayList;
import java.util.List;

public class Room {
	int limit;
	String name;
	List<Meeting> meetings;
	
	public Room(String name, int limit) {
		meetings = new ArrayList<Meeting>();
		this.name = name;
		this.limit = limit;
	};
	public void addMeeting(Meeting r) {
		meetings.add(r);
	}
	public void show(int iM) {
		System.out.print("  장소: " + this.name + " ");
		meetings.get(iM).show();
	}
}
