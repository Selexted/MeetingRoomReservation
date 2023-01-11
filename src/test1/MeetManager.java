package test1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class MeetManager {
	List<Date> dates;
	List<User> users;
	Scanner sc;
	File dateInfo;
	Rule rules;
	User currentUser;

	public void clear() { //화면 초기화 함수
		for(int i=0;i<2;i++) {
			System.out.println("");
		}
	}
	
	public void waitInput() {
		System.out.print("계속하려면 아무키나 입력하세요.");
		try {
			System.in.read();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addDate(Date d) {
		dates.add(d);
	}
	
	public void loginMenu() {//로그인 메뉴 출력
        clear();
        System.out.println("#로그인 메뉴#");//추후 삭제나 수정
        System.out.println("1.로그인\n2.회원가입\n3.종료");
        System.out.print("메뉴를 선택하세요 : ");
        String k = null ;
        try {
//        	sc.nextLine();
            k = sc.nextLine();
        }catch(InputMismatchException e) {
            
            sc.nextLine();
        }
        switch(k) {
            case "1":
            	//sc.nextLine();
                signIn();
                break;
            case "2":
            	//sc.nextLine();
                signUp();
                 break;
            case "3":
                exit();
                break;
            default:
                System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
                loginMenu();
        }
    }
	
	public void signIn(){ //로그인
			
		int success=0;
		
		String id, pw;
	
		while(true) {
		System.out.print("ID: ");
		
		id = sc.nextLine();
		if(rules.checkID(id)==true) {
			break;
		}
		
		}
		
		while(true) {
		System.out.print("PASSWORD: ");
	
		pw = sc.nextLine();
		if(rules.checkPass(pw)==true) {
			break;
		}
		
		}
		
		
		Iterator<User> it =users.iterator();
		
		for(int i=0;i<users.size();i++) {
			if(it.hasNext()) {
				User u =it.next();
				if(u.id.equals(id)&&u.pw.equals(pw)) {
					System.out.println("로그인 성공 ");
					success=1;
					currentUser=u;
					break;
				}
			}
			
			
		}
		if(success==0) {
			System.out.println("아이디, 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
			signIn();
		}
		else {
			
			mainMenu();//성공하면 메인메뉴로 넘어감. 일단 무조건 성공으로 작성
		}

	}
	
	public void signUp(){ //회원가입
		boolean check=true;
		int check2=0;
		
		String name = null, id, pw, rank;
		id="";
		pw="";
		if(check2==0) {
		System.out.print("이름: ");
		
		name = sc.nextLine();
		check= rules.checkName(name);
		}
		
		if(check==true) {
			
			check2=1;
			while(true) {
			boolean idcheck=true;
			System.out.print("ID: ");
			
			id = sc.nextLine();
			check=rules.checkID(id);
			
			if(check==true) {
				
			Iterator<User> it =users.iterator();
			for(int i=0;i<users.size();i++) {
				if(it.hasNext()) {
					User u =it.next();
					if(u.id.equals(id)) {
						System.out.println("이미 존재하는 ID입니다. 다시 입력해주세요.");
						idcheck=false;
						break;
						
					}
				}

			}
			if(idcheck==true) {
				break;
			}	
			
		}
		}
		
		}
		if(check==true) {
			while(true) {
			System.out.print("PASSWORD:  ");
			
			pw = sc.nextLine();
			check=rules.checkPass(pw);
			if(check==true) {
				break;
			}
			}
		}
	
		if(check==true) {
			
			while(true) {
				
				
				System.out.print("직급(사원, 임원, 관리자): ");
				
				rank = sc.nextLine();
				if(rank.equals("사원")) { //사원:0, 임원:1, 관리자:2
                    users.add(new User(name,id,pw,0,0));
                    break;
               } else if(rank.equals("임원")) {
                   users.add(new User(name,id,pw,1,0));
                    break;
               } else if(rank.equals("관리자")) {
                   users.add(new User(name,id,pw,2,0));
                    break;
               } else {
                   System.out.println("직급은 ‘사원’, ‘임원’, ‘관리자’ 중 하나만 입력할 수 있습니다. 다시 입력해주세요.");

               }
			}
		}
		if(check==false) {
		
			signUp();
		}
		
		else {
		File userInfo;
		BufferedWriter writer;
		userInfo = new File("data\\users.txt"); //회의실 정보 저장
		try {
			writer = new BufferedWriter(new FileWriter(userInfo));
			String str = "";
			for(int i=0; i<users.size();i++) {
				str += users.get(i).name + " " + users.get(i).id + " ";
                str += users.get(i).pw + " " + users.get(i).rank + " " + users.get(i).value +"\n";
			}
		
			writer.write(str);
			writer.close();
			
			System.out.println("회원 가입이 완료되었습니다.");
			loginMenu();
		} catch (IOException e) {
			System.out.println("사용자 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
			e.printStackTrace();
		}
		}
		
		}
	
	public void exit() { //프로그램 종료
		clear();
		saveData(); //텍스트 저장함수
		System.out.println("프로그램을 종료합니다.");
		sc.close();
		System.exit(0);
	}

	public void mainMenu() { //메인 메뉴 출력
		clear();
		System.out.println("#메인 메뉴#");//추후 삭제나 수정
		System.out.println("1.회의 예약\n2.회의 조회\n3.회의 취소\n4.회의실 관리\n5.유저 정보 변경 / 삭제\n6.데이터 저장 \n7.로그아웃\n8.종료");
		String k = null;
		System.out.print("메뉴를 선택하세요 : ");
		try {
			k = sc.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
			//waitInput();
			sc.nextLine();
			mainMenu();
		}
		switch(k) {
			case "1":
				reserve();
				break;
			case "2":
				lookup();
				break;
			case "3":
				cancel();
				break;
			case "4":
				admin();
				break;
			case "5":
				changeUserData();
				break;
			case "6":
				SaveDataMenu();
				break;
			case "7":
				logout();
				break;
			case "8":
				exit();
				break;
			default:
				System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
				//waitInput();
				mainMenu();

		}
	}
	
	private void logout() {
		currentUser = null;
		System.out.println("로그아웃합니다.");
		loginMenu();
	}

	public void SaveDataMenu(){ // 데이터 저장 메뉴
        saveData();
        System.out.println("\n변경사항이 모두 저장되었습니다.\n");
        System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
        try {
            System.in.read();
            return;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	int meeting_count = 0;
	boolean delete_meeting = false;
	boolean overwrite_meeting = false;
	List<int[]> meeting_to_delete = new ArrayList<int[]>();
	public void reserve() { //회의 예약

		if(currentUser.rank == 0) {
			System.out.println("예약 권한이 없는 사용자입니다.");
			return;
		}
		clear();
		String date, room, time, member;
		int DI, RI;
		String k;

		List<String> search_word = new ArrayList<String>();
		search_word.add(0, "");
		search_word.add(1, "");
		search_word.add(2, "");
		search_word.add(3, "");

		while(true) {
			System.out.print("회의실을 사용할 날짜를 입력하세요: ");
			date = sc.nextLine();
			// 날짜 문법 체크
			date = rules.changeDate(date);
			if (rules.checkDate(rules.changeDate(date), true)) {
				search_word.set(0, date);
				date = date.substring(search_word.get(0).length() - 5).replaceAll("/", "");
				DI = getIndexOfDate(date); //해당 날짜 클래스의 인덱스
				break;
			} else {
				// 에러
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				continue;
			}
		}

		while(true) {
			System.out.print("사용할 회의실을 입력하세요: ");
			room = sc.nextLine();
			if (rules.checkRoomName(room)) {
				boolean room_exist = false;
				for (int i = 0; i < dates.get(DI).rooms.size(); i++) {
					if (room.equals(dates.get(DI).rooms.get(i).name)) {
						room_exist = true;
					}
				}
				if (room_exist) {
					RI = dates.get(DI).getIndexOfRoom(room); //해당 날짜의 해당 회의실에 대한 인덱스
					search_word.set(1, room);
					break;
				} else {
					System.out.println("존재하지 않는 회의실입니다. 다시 입력 바랍니다.");
					continue;
				}
			} else {
				// 에러
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				continue;
			}

		}

		while(true) {
			search_word.set(2, "");
			overwrite_meeting = true;
			searchDate(true, search_word);
			overwrite_meeting = false;
			System.out.println();

			System.out.print("회의실을 이용할 시간을 입력하세요: ");
			time = sc.nextLine();

			if (!time.contains("~") || time.contains(" ")) {
				System.out.println("올바른 시간 입력 방법이 아닙니다.");
				continue;
			}

			String[] time_arr = time.split("~");

			if (rules.checkTime(time_arr[0]) && rules.checkTime(time_arr[1])) {
				String start_time = rules.change_standard_time(time_arr[0]);

				String a[]=start_time.split(":");
				start_time = rules.change_standard_time(a[0]+":00");

				String end_time = rules.change_standard_time(time_arr[1]);

				String b[]=end_time.split(":");
				int minute=Integer.parseInt(b[1]);
				if(minute>0&&minute<60) {
					int temp= Integer.parseInt(b[0])+1;
					if(temp==24) {
						end_time="24:00";
					}
					else {
						end_time=Integer.toString(temp)+":00";
					}

				}
				else {
					end_time=b[0]+":00";
				}
				end_time=rules.change_standard_time(end_time);

				if(Integer.parseInt(a[0])==Integer.parseInt(b[0])) {
					if(Integer.parseInt(a[1])>Integer.parseInt(b[1])) {
						System.out.println("시간 순서가 맞지않습니다.");
						continue;
					}
				}

				List<String> time_list_string = new ArrayList<String>();

				// 입력 받은 시간 ","로 연결하기
				int start_time_i = Integer.parseInt(start_time.substring(0,2));
				int end_time_i = Integer.parseInt(end_time.substring(0,2));

				if (start_time_i >= end_time_i) {
					System.out.println("올바른 시간 입력 방법이 아닙니다.");
					continue;
				}

				String timeString = "";
				for (int i = start_time_i; i < end_time_i; i++) {
					time_list_string.add(i + ":00");
					timeString = timeString + i + ",";
				}

				meeting_to_delete.clear();
				meeting_count = 0;
				delete_meeting = true;
				for (int i = 0; i < time_list_string.size(); i++) {
					search_word.set(2, time_list_string.get(i));
					searchDate(false, search_word);
				}
				delete_meeting = false;

				if (meeting_count > 0) {
					System.out.println("이미 회의가 존재하는 시간입니다.");
					continue;
				} else {

					time = timeString.substring(0, timeString.length()-1); //11:00~13:00 시 11,12로 저장
					//요일 확인 함수
					
					k =String.valueOf(date);
					int month = Integer.parseInt(k.substring(0,2));
					int day = Integer.parseInt(k.substring(2,4));
					LocalDate dated = LocalDate.of(2021, month, day);
					DayOfWeek dayOfWeek = dated.getDayOfWeek();
					int day_date = dayOfWeek.getValue();


					//월=1 화=2 수=3 목=4 금=5 토=6 일=7
					
					if(day_date==1) { //8~20
					   if(start_time_i>=8 &&end_time_i<=20) {

					   }
					   else {
						   System.out.println("월요일의 예약가능시간이 아닙니다.");
						   System.out.println("예약 가능 시간: 08:00~20:00");
						   continue;
					   }
						
					}
					
					else if(day_date==2) {//8~20
						 if(start_time_i>=8 &&end_time_i<=20) {

						   }
						   else {
							   System.out.println("화요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 08:00~20:00");
							   continue;
						   }

					}
					else if(day_date==3) {//8~20
						 if(start_time_i>=8 &&end_time_i<=20) {

						   }
						   else {
							   System.out.println("수요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 08:00~20:00");
							 continue;
						   }
						
					}
					else if(day_date==4) {//8~20
						 if(start_time_i>=8 &&end_time_i<=20) {

						   }
						   else {
							   System.out.println("목요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 08:00~20:00");
							 continue;
						   }
						
					}
					else if(day_date==5) {//8~20
						 if(start_time_i>=8 &&end_time_i<=20) {

						   }
						   else {
							   System.out.println("금요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 08:00~20:00");
							 continue;
						   }
						
					}
					else if(day_date==6) {//11~18
						 if(start_time_i>=11 &&end_time_i<=18) {

						   }
						   else {
							   System.out.println("토요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 11:00~18:00");
							 continue;
						   }
						
					}
					else if(day_date==7) {//11~18
						 if(start_time_i>=11 &&end_time_i<=18) {

						   }
						   else {
							   System.out.println("일요일의 예약가능시간이 아닙니다.");
							   System.out.println("예약 가능 시간: 11:00~18:00");
							   continue;

						   }
						
					}

					//// 중요: 겹치는 회의가 있을 때 우선 순위에 대한 처리
					for (int i = 0; i < meeting_to_delete.size(); i++) {
						show(meeting_to_delete.get(i)[0], meeting_to_delete.get(i)[1], meeting_to_delete.get(0)[2]);

						User hostUser = null;
						Iterator<User> it = users.iterator();
						for (int j = 0; j < users.size(); j++) {
							if (it.hasNext()) {
								User u = it.next();
								if (u.name.equals(dates.get(meeting_to_delete.get(i)[0]).rooms.get(meeting_to_delete.get(i)[1]).meetings.get(meeting_to_delete.get(0)[2]).host)) {
									hostUser = u;
									break;
								}
							}
						}
						if (hostUser != null && hostUser.value > 0) {
							hostUser.value--;
						}

						delete(meeting_to_delete.get(i)[0], meeting_to_delete.get(i)[1], meeting_to_delete.get(0)[2]);
					}
					if (meeting_to_delete.size() != 0) {
						System.out.println("해당 회의가 삭제됩니다.");
					}
					break;
				}

			} else {
				// 에러
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				continue;
			}
		}

		while(true) {
			System.out.print("회의실에 참가할 인원을 입력하세요(인원제한 " + dates.get(DI).rooms.get(RI).limit + "명): ");
			member = sc.nextLine();

			if (member.contains(" ")) {
				// 에러
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				continue;
			}

			meeting_count = 0;
			int member_error_count = 0;
			if (member.contains(",")) {
				String[] member_arr = member.split(",");

				if (member_arr.length==0) {
					System.out.println("참가 인원이 없습니다. 다시 입력바랍니다.");
					continue;
				}

				if (member_arr.length > dates.get(DI).rooms.get(RI).limit) {
					System.out.println("인원 초과입니다. 다시 입력 바랍니다.");
					continue;
				}

				for (int i = 0; i < member_arr.length; i++) {
					if (!rules.checkName(member_arr[i])) {
						member_error_count++;
					} else {
						search_word.set(1, "");
						search_word.set(3, member_arr[i]);
						searchDate(false, search_word);
					}
				}
			} else {
				if (!rules.checkName(member)) {
					member_error_count++;
				} else {
					search_word.set(1, "");
					search_word.set(3, member);
					searchDate(false, search_word);
				}
			}
			if (member_error_count > 0) {
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				continue;
			}
			if (meeting_count > 0) {
				System.out.println("참석 불가능한 인원이 존재합니다. 다시 입력 바랍니다.");
				continue;
			}

			dates.get(DI).rooms.get(RI).meetings.add(new Meeting(time, member, currentUser.name)); //일정 객체 생성
			currentUser.value += 1;
			System.out.println("회의가 추가되었습니다.");
			break;
		}
	}
	
	public void lookup() { //회의 조회

		String[] input_arr;
		List<String> input_list;

		String input_date = "";
		String input_room = "";
		String input_time = "";
		String input_attendant = "";

		int error_count = 1;

		while(error_count > 0) {
			error_count = 0;

			input_date = "";
			input_room = "";
			input_time = "";
			input_attendant = "";

			System.out.print("검색어: ");
			String input = sc.nextLine();

			if (input.length() == 0) {
				// 에러
				System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
				error_count++;
				continue;
			}

			input = remove_null(input);

			input_arr = input.split(" ");
			input_list = Arrays.asList(input_arr);

			for (int i = 0; i < input_list.size(); i++) {
				if (input_list.get(i).contains("/") || input_list.get(i).contains("-")) {
					// 날짜 문법 체크
					String date = rules.changeDate(input_list.get(i));
					if (rules.checkDate(rules.changeDate(input_list.get(i)), false)) {
						if (input_date.length() == 0) {
							input_date = date;
						} else {
							System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
							error_count++;
							break;
						}
					} else {
						// 에러
						System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
						error_count++;
						break;
					}
				} else if (input_list.get(i).contains("회의실")) {
					// 회의실 문법 체크
					if (rules.checkRoomName(input_list.get(i))) {
						if (input_room.length() == 0) {
							input_room = input_list.get(i);
						} else {
							System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
							error_count++;
							break;
						}
					} else {
						// 에러
						System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
						error_count++;
						break;
					}
				} else if (input_list.get(i).contains(":")) {
                    // 시간 문법 체크
                    if (rules.checkTime(input_list.get(i))) {
                        String time = rules.change_standard_time(input_list.get(i));
						if (input_time.length() == 0) {
							input_time = time;
						} else {
							System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
							error_count++;
							break;
						}
                    } else {
                        // 에러
                        System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
                        error_count++;
                        break;
                    }
				} else {
					// 참석자 문법 체크
					if (rules.checkName(input_list.get(i))) {
						if (input_attendant.length() == 0) {
							input_attendant = input_list.get(i);
						} else {
							System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
							error_count++;
							break;
						}
					} else {
						// 에러
						System.out.println("잘못된 입력입니다. 다시 입력 바랍니다.");
						error_count++;
						break;
					}
				}
			}
		}

		List<String> search_word = new ArrayList<String>();
		search_word.add(0, input_date);
		search_word.add(1, input_room);
		search_word.add(2, input_time);
		search_word.add(3, input_attendant);

		if (search_word.get(0).length()+search_word.get(1).length()+search_word.get(2).length()+search_word.get(3).length() != 0) {
			meeting_count = 0;
			searchDate(true, search_word);
			if (meeting_count == 0) {
				System.out.println("조회된 회의가 없습니다.");
			}
		}

		System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void searchDate(boolean show, List<String> search_word) {
		if (search_word.get(0).length() != 0) {
			String date = search_word.get(0).substring(search_word.get(0).length() - 5);
			date = date.replaceAll("/", "");
			int DI = getIndexOfDate(date);
			searchRoom(show, search_word, dates.get(DI), DI);
		} else {
			for (int i = 0; i < dates.size(); i++) {
				searchRoom(show, search_word, dates.get(i), i);
			}
		}
	}

	public void searchRoom(boolean show, List<String> search_word, Date date, int date_index) {
		if (search_word.get(1).length() != 0) {
			for (int i = 0; i < date.rooms.size(); i++) {
				if (search_word.get(1).equals(date.rooms.get(i).name)) {
					searchTime(show, search_word, date.rooms.get(i), date_index, i);
				} else {
//					System.out.println("no match room");
				}
			}
		} else {
			for (int i = 0; i < date.rooms.size(); i++) {
				searchTime(show, search_word, date.rooms.get(i), date_index, i);
			}
		}
	}

	public void searchTime(boolean show, List<String> search_word, Room room, int date_index, int room_index) {
		if (search_word.get(2).length() != 0) {
			String time = search_word.get(2).split(":")[0];
			time = Integer.toString(Integer.parseInt(time));
			for (int i = 0; i < room.meetings.size(); i++) {
				if (Arrays.asList(room.meetings.get(i).time).contains(time)) {
					searchMember(show, search_word, room.meetings.get(i), date_index, room_index, i);
				} else {
//					System.out.println("no match time");
				}
			}
		} else {
			for (int i = 0; i < room.meetings.size(); i++) {
				searchMember(show, search_word, room.meetings.get(i), date_index, room_index, i);
			}
		}
	}

	public void searchMember(boolean show, List<String> search_word, Meeting meeting, int date_index, int room_index, int meeting_index) {

		if (search_word.get(3).length() != 0) {
			if (Arrays.asList(meeting.member).contains(search_word.get(3))) {
				if (show) {
					show(date_index, room_index, meeting_index);
				}
				meeting_count++;
			}
		} else {
			if (delete_meeting) {
				User hostUser = null;
				Iterator<User> it = users.iterator();
				for (int i = 0; i < users.size(); i++) {
					if (it.hasNext()) {
						User u = it.next();
						if (u.name.equals(meeting.host)) {
							hostUser = u;
							break;
						}
					}
				}
				if (hostUser == null || currentUser.value < hostUser.value) {
					int meeting_del[] = {date_index, room_index, meeting_index};
					if (!meeting_to_delete.isEmpty()) {
						if (meeting_to_delete.get(meeting_to_delete.size()-1)[2] == meeting_index) {
							return;
						}
					}
					meeting_to_delete.add(meeting_del);
				} else {
					meeting_count++;
				}
			} else {
				if (show) {
					if (overwrite_meeting) {
						User hostUser = null;
						Iterator<User> it = users.iterator();
						for (int i = 0; i < users.size(); i++) {
							if (it.hasNext()) {
								User u = it.next();
								if (u.name.equals(meeting.host)) {
									hostUser = u;
									break;
								}
							}
						}
						if (hostUser == null || currentUser.value < hostUser.value) {
							System.out.print("O ");
						} else {
							System.out.print("X ");
						}
					}
					show(date_index, room_index, meeting_index);
				}
				meeting_count++;
			}
		}
	}

	public String remove_null(String input) {
		input = input.trim().replaceAll("\\s+", " ");
		return input;
	}

	
	public void cancel() { //회의 취소
		clear();
		class indexSet {
			int i,j,k;
			public indexSet(int i, int j, int k) {
				this.i = i;
				this.j = j;
				this.k = k;
			}
		}
		List<indexSet> cUserMeeting = new ArrayList<indexSet>();
		int num = 1;
		for(int i=0;i<dates.size();i++) { //전제 meeting 탐색
			for(int j=0;j<dates.get(i).rooms.size();j++) {
				for(int k = 0;k<dates.get(i).rooms.get(j).meetings.size();k++) {
					if(dates.get(i).rooms.get(j).meetings.get(k).host.equals(currentUser.name)){ //currentUser이름과 같은 host를 가진 회의의 인덱스 저장
						cUserMeeting.add(new indexSet(i,j,k));
						System.out.print(num + ". "); num++;
						show(i,j,k);
					}
				}
			}
		}
		if(cUserMeeting.size() == 0) {
			System.out.println("예약한 회의가 없습니다.");
			return;
		}
		String ss;
		int s;
		while(true) {
			System.out.print("취소할 회의의 번호를 입력하세요 : ");

			ss = sc.nextLine();
			if (!ss.matches("-?\\d+")) {
				System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
				//waitInput();
				continue;
			} else {
				s = Integer.parseInt(ss);
			}

			if(s > cUserMeeting.size() || s <=0) {
				System.out.println("올바르지 않은 입력입니다. 다시 입력해주세요.");
				//waitInput();
				continue;
			}
			break;

		}
		delete(cUserMeeting.get(s-1).i,cUserMeeting.get(s-1).j,cUserMeeting.get(s-1).k);
		System.out.println("회의가 취소되었습니다.");

		if(currentUser.value != 0)
			currentUser.value-=1;
	}

	public void admin() { //관리자
		Rule R = new Rule();
		boolean check=true;
		int l = 0;
		LocalDate date = LocalDate.now().plusDays(6);
		DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String changeDay = date.format(format);
		if(currentUser.rank == 2){ // 로그인된 아이디가 관리자인지 확인

			// 7일차 데이터파일에 저장되어있는 회의실 정보 출력
			System.out.println("[관리자 설정]");
			for(int i=0; i<dates.get(6).rooms.size();i++)
				System.out.println(dates.get(6).rooms.get(i).name + " " + dates.get(6).rooms.get(i).limit);

			System.out.print("명령어 : ");
			String command = sc.nextLine();
			command = remove_null(command);

			if(command.contains(" ") && command.length() > 6) {
				// 회의실 이름과 뒤에 명령어 " "로 구분
				String[] cs = command.split(" ");
				if(R.checkRoomName(cs[0])) {
					if (cs[1].equals("삭제") && dates.get(6).getIndexOfRoom(cs[0]) != -1) { // 삭제란단어가 들어가면
						// 회의실 삭제
						int index = dates.get(6).getIndexOfRoom(cs[0]);
						dates.get(6).rooms.remove(index); // .txt에 삭제???
						System.out.println(changeDay + " 이후로 변동사항이 적용됩니다.\n");
						System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
						try {
							System.in.read();
							return;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if (cs[1].contains("명")) {
						// 회의실 추가 혹은 수용 인원수 변경

						String a= cs[1].split("명")[0];

						for(int i=0; i<a.length();i++) {

							if(a.charAt(i)<'0'||a.charAt(i)>'9') {
								System.out.println("추가 및 수정과정에서 숫자외의 문자가 들어갔습니다.");
								check=false;
								break;

							}
						}

						if(check==true) {
							l = Integer.parseInt(cs[1].split("명")[0]);
							if(l==0) {
								check=false;
								System.out.println("회의실 참가인원은 최소 한명이여야합니다.");
							}
						}


						if(check==true) {
							if (dates.get(6).getIndexOfRoom(cs[0]) != -1) {//수정
								int index = dates.get(6).getIndexOfRoom(cs[0]);
								dates.get(6).rooms.get(index).limit = l;
								System.out.println(changeDay + " 이후로 변동사항이 적용됩니다.\n");
								System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
								try {
									System.in.read();
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if(dates.get(6).getIndexOfRoom(cs[0]) == -1) {
								dates.get(6).addRoom(new Room(cs[0],l));

								System.out.println(changeDay + " 이후로 변동사항이 적용됩니다.\n");
								System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
								try {
									System.in.read();
									return;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}else {
						System.out.println("올바르지 않은 입력입니다.");
					}
				} else {
					System.out.println("올바르지 않은 입력입니다.");
				}
			}
			else {
				System.out.println("올바르지 않은 입력입니다.");
			}
		} else { // 로그인된 아이디가 관리자가 아닐 때
			System.out.println("해당 메뉴는 관리자만 접근 가능합니다.");

			System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
			try {
				System.in.read();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void changeUserData() {

		clear();

		if (currentUser.rank != 2){									// 로그인된 아이디가 관리자인지 확인
			System.out.println("해당 메뉴는 관리자만 접근 가능합니다.");

			System.out.print("아무키나 눌러서 메인 메뉴로 돌아가세요.");
			try {
				System.in.read();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("[유저정보 변경 / 삭제]");

		String[] cs;
		User selectedUser = null;

		while (true) {                                                // 올바른 입력이 아닐 시 재입력을 받기 위한 반복문

			System.out.print("명령어 : ");                            // 입력 받기
			String command = sc.nextLine();

			if (command.contains("\t")) {							// 탭 허용하지 않음
				System.out.println("올바르지 않은 입력입니다.");
				System.out.println();
				continue;
			}

			command = remove_null(command);

			cs = command.split(" ");

			if (cs.length != 2) {                                    // 스플릿 결과로 2개만 허용
				System.out.println("올바르지 않은 입력입니다.");
				System.out.println();
				continue;
			}

			if (!rules.checkName(cs[0])) {                            // 사용자 이름 규칙 확인
				System.out.println("올바르지 않은 입력입니다.");
				System.out.println();
				continue;
			}

			Iterator<User> it = users.iterator();                    // 데이터파일에 존재하는 사용자 이름인지 확인
			boolean userExist = false;

			for (int i = 0; i < users.size(); i++) {
				if (it.hasNext()) {
					User u = it.next();
					if (u.name.equals(cs[0])) {
						userExist = true;
						selectedUser = u;
						break;
					}
				}
			}

			if (!userExist) {
				System.out.println("데이터에 존재하는 사용자가 아닙니다.");
				continue;
			}

			if (cs[1].equals("사원") || cs[1].equals("임원") || cs[1].equals("관리자") || cs[1].equals("삭제")) {

			} else {                                                // 사원, 임원, 관리자, 삭제 네 가지 단어만 허용
				System.out.println("올바르지 않은 입력입니다.");
				System.out.println();
				continue;
			}

			int inputRank = -1;

			if (cs[1].equals("사원") || cs[1].equals("임원") || cs[1].equals("관리자")) {        // 직급을 입력한 경우
				if (cs[1].equals("사원")) {
					inputRank = 0;
				}
				if (cs[1].equals("임원")) {
					inputRank = 1;
				}
				if (cs[1].equals("관리자")) {
					inputRank = 2;
				}

				if (selectedUser.rank == 2) {							// 관리자의 직급 수정을 시도할 경우
					System.out.println("관리자의 직급은 수정할 수 없습니다.");
					System.out.println();
					continue;
				}

				if (selectedUser.rank == inputRank) {					// 해당 사용자의 랭크와 같은 랭크로 수정을 시도할 경우
					System.out.println("이미 " + cs[1] + "인 사용자 입니다.");
					System.out.println();
					continue;
				}
				
				selectedUser.rank = inputRank;							// 사용자의 직급 수정

//				File userInfo;                                            // 변경사항에 대해 데이터파일에 입력 후 저장
//				BufferedWriter writer;
//				userInfo = new File("data\\users.txt");
//				try {
//					writer = new BufferedWriter(new FileWriter(userInfo));
//					String str = "";
//					for (int i = 0; i < users.size(); i++) {
//						str += users.get(i).name + " ";
//						str += users.get(i).id + " ";
//						str += users.get(i).pw + " ";
//						str += users.get(i).rank + "\n";
//					}
//					writer.write(str);
//					writer.close();
//
//					System.out.println("변동사항이 적용됩니다.");
//				} catch (IOException e) {
//					System.out.println("사용자 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
//					e.printStackTrace();
//				}
				System.out.println(cs[0] + " 사용자의 직급을 " + cs[1] + "(으)로 변경했습니다.");
			}

			if (cs[1].equals("삭제")) {                                    // 삭제를 입력한 경우

				if (selectedUser == currentUser) {							// 현재 로그인 되어있는 사용자에 대해 삭제 시도할 경우
					System.out.println("현재 로그인 된 사용자는 삭제할 수 없습니다.");
					System.out.println();
					continue;
				}

				users.remove(selectedUser);                                // 해당 사용자 삭제

//				File userInfo;                                            // 변경사항에 대해 데이터파일에 입력 후 저장
//				BufferedWriter writer;
//				userInfo = new File("data\\users.txt");
//				try {
//					writer = new BufferedWriter(new FileWriter(userInfo));
//					String str = "";
//					for (int i = 0; i < users.size(); i++) {
//						str += users.get(i).name + " ";
//						str += users.get(i).id + " ";
//						str += users.get(i).pw + " ";
//						str += users.get(i).rank + "\n";
//					}
//					writer.write(str);
//					writer.close();
//
//					System.out.println("사용자 정보가 삭제 되었습니다.");
//				} catch (IOException e) {
//					System.out.println("사용자 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
//					e.printStackTrace();
//				}
			}

			break;
		}
	}
	
	public void saveData() {//데이터 저장
        File roomInfo, meetInfo, userInfo;
        BufferedWriter writer;
        String[] today = setDate(); // 오늘부터 7일치 날짜를 반환
        for(int i=0;i<7;i++) {
            roomInfo = new File("data\\" + today[i] + "\\room.txt"); //회의실 정보 저장
            try {
                writer = new BufferedWriter(new FileWriter(roomInfo));
                String str = "";
                for(int j=0;j<dates.get(i).rooms.size(); j++) {
                    str += dates.get(i).rooms.get(j).name + " " + dates.get(i).rooms.get(j).limit + "\n";
                }
                writer.write(str);
                writer.close();
            } catch (IOException e) {
                System.out.println("회의실 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
                e.printStackTrace();
            }
            meetInfo = new File("data\\" + today[i] + "\\meet.txt"); //일정 정보 저장
            try {
                writer = new BufferedWriter(new FileWriter(meetInfo));
                String str = "";
                for(int j=0;j<dates.get(i).rooms.size(); j++) {
                    for(int k=0; k<dates.get(i).rooms.get(j).meetings.size(); k++) {
                        str += dates.get(i).rooms.get(j).name + " "; //회의실 이름
                        str += dates.get(i).rooms.get(j).meetings.get(k).getTime() + " ";
                        str += dates.get(i).rooms.get(j).meetings.get(k).getMember() + " ";
                        str += dates.get(i).rooms.get(j).meetings.get(k).host + "\n";
                    }
                }
                writer.write(str);
                writer.close();
            } catch (IOException e) {
                System.out.println("일정 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
                e.printStackTrace();
            }
        }
        userInfo = new File("data\\users.txt"); //유저 정보 저장
        try {
            writer = new BufferedWriter(new FileWriter(userInfo));
            String str = "";
            for(int i=0; i<users.size();i++) {
                str += users.get(i).name + " " + users.get(i).id + " ";
                str += users.get(i).pw + " " + users.get(i).rank + " " + users.get(i).value +"\n";
            }
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            System.out.println("사용자 데이터 저장 중 오류가 발생했습니다.\n오류내용 : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("데이터 저장이 완료되었습니다.");
    }
	
	public void showList(String str){//인자값에 따른 회의 리스트 출력
		
	}

	public void loadData() { //텍스트 파일을 불러와서 클래스로 저장하는 함수. 주소는 상대주소
		System.out.println("로딩 로그");
		File roomInfo, meetInfo, userInfo;
		Scanner reader; //파일 읽기용 스캐너
		String[] today = setDate(); // 오늘부터 7일치 날짜를 반환
		if(new File("data").mkdir()){// data 폴더 생성
			System.out.println("data 폴더 생성 완료");
		}
		else{
			System.out.println("data 폴더가 이미 존재");
		}

		File[] del = new File("data").listFiles();//지난 날짜의 파일을 삭제
		boolean flag = true;
		for(int i=0;i<del.length;i++) {
			if(del[i].isDirectory()) {
				for(int j=0;j<7;j++)
					if(del[i].getName().equals(today[j]))//7일 범위 안에 파일이 존재한다면
						flag = false;
				if(flag) {//7일 범위 안에 없다면
					File[] temp = del[i].listFiles();
					for(int k=0;k<temp.length;k++) {
						if(temp[k].delete()) {
							System.out.println(del[i].getName() + "폴더의 " +  temp[k].getName() + "파일을 삭제했습니다.");
						}
					}
					del[i].delete();
					System.out.println(del[i].getName() + "폴더를 삭제했습니다.");
				}
				flag = true;
			}
		}

		for(int i=0;i<7;i++) {
			dateInfo = new File("data\\" + today[i]); //날짜 폴더 접근
			try {
				if (dateInfo.mkdir()) {
					System.out.println(today[i] + " 폴더 생성 완료");
				} else {

				}
			} catch (Exception e) {
				System.out.println("날짜 폴더 접근 오류 : " + e.getMessage());
			}
			dates.add(new Date(today[i])); //날짜 클래스 생성
			///////////////////////////////////////////////////////////////////////////////////////
			try {
				roomInfo = new File("data\\" + today[i] + "\\room.txt"); //회의실 정보 생성, 접근
				if (roomInfo.createNewFile()) {
					System.out.println(roomInfo.getAbsolutePath() + "폴더에 회의실 정보 파일 생성 완료");
					if(i==6) {//마지막 날짜
						File copy = new File("data\\" + today[i-1] + "\\room.txt");
						Files.copy(copy.toPath(), roomInfo.toPath(), StandardCopyOption.REPLACE_EXISTING);
						System.out.println("새로 생성된 " + today[i] + "폴더의 회의실 정보를 " + today[i-1] + "폴더의 회의실 정보로 복사했습니다.");
					}
				} else {
					System.out.println(roomInfo.getAbsolutePath() + "폴더에 회의실 정보 파일 이미 존재");
				}
				FileInputStream readR = new FileInputStream(roomInfo);
				reader = new Scanner(readR);
				while(reader.hasNext()){ //회의실 클래스 생성
					String name = reader.next();
					int limit = reader.nextInt();
					dates.get(i).addRoom(new Room(name, limit));
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("회의실 정보 접근 오류 : " + e.getMessage());
			}

			///////////////////////////////////////////////////////////////////////////////////////

			try {
				meetInfo = new File("data\\" + today[i] + "\\meet.txt"); //회의 정보 생성, 접근
				if (meetInfo.createNewFile()) {
					System.out.println(meetInfo.getAbsolutePath() +  "폴더에 일정 파일 생성 완료");
				} else {
					System.out.println(meetInfo.getAbsolutePath() +  "폴더에 일정 파일 이미 존재");
				}
				FileInputStream readM = new FileInputStream(meetInfo);
				reader = new Scanner(readM);
				while(reader.hasNext()) { //회의실 클래스 생성
					String room = reader.next();
					String time = reader.next();
					String member = reader.next();
					String host = reader.next();
					int index = dates.get(i).getIndexOfRoom(room); //중요!! 회의실 이름으로 인덱스를 찾는 함수
					if(index == -1) {//존재하지 않는 회의실에 있는 예약
						//처리구문 추가
					}
					else { //회의실 객체 안에 일정 객체 추가
						dates.get(i).rooms.get(index).addMeeting(new Meeting(time,member,host));
					}
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("일정 추가 오류 : " + e.getMessage());
			}
		}

		try {
			userInfo = new File("data\\users.txt"); //유저 정보 생성, 접근
			FileInputStream readU = new FileInputStream(userInfo);
			if (userInfo.createNewFile()) {
				System.out.println(userInfo.getAbsolutePath() +  "사용자 데이터 파일 생성 완료");
			} else {
				System.out.println(userInfo.getAbsolutePath() +  "사용자 데이터 파일 이미 존재");
			}
			reader = new Scanner(readU);
			while(reader.hasNext()) { //유저 클래스 생성
				String name = reader.next();
				String id = reader.next();
				String password = reader.next();
				int rank = reader.nextInt();
				int value = reader.nextInt();
				if(today[0].substring(2).equals("01")) //매월 1일에 모든 value 0으로 초기화
					value = 0;
				users.add(new User(name, id, password, rank, value));
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("사용자 정보 불러오기 오류 : " + e.getMessage());
		}

		System.out.print("시작하려면 아무키나 누르세요.");
		try {
			System.in.read();
			sc.nextLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] setDate(){
		LocalDate today = LocalDate.now();
		DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String[] date_arr = new String[7];
		for(int i=0;i<7;i++) {
			LocalDate date = today.plusDays(i);
			String dayTemp = date.format(format);
			String[] temp = dayTemp.split("/");
			date_arr[i] = temp[1] + temp[2];
		}
		return date_arr;
	}
	
	public int getIndexOfDate(String day) {
		for(int i=0; i<dates.size(); i++) {
			if(day.equals(dates.get(i).day)) {
				return i;
			}
		}
		return -1; //예약 불가능한 날짜
	}
	
	public void show(int iD, int iR, int iM) {
		dates.get(iD).show(iR, iM);
	}
	
	public void delete(int iD, int iR, int iM) {
		dates.get(iD).rooms.get(iR).meetings.remove(iM);
	}
	
	public MeetManager() { //생성자 초기화
		dates = new ArrayList<Date>();
		users = new ArrayList<User>();
		sc = new Scanner(System.in);
		rules = new Rule();
		loadData(); //텍스트 파일 불러오기
		loginMenu();
	};
}
