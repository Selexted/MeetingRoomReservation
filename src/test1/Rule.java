package test1;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class Rule {
	public static boolean checkID(String str) {
		String idString = str;
		if(idString.contains("\t") || idString.contains(" ")) {
			System.out.println("ID에는 영어, 숫자만 사용할 수 있습니다. 다시 입력해주세요.");
			return false;
		}
		char[] id = idString.toCharArray();
		if(id.length==0) {
			System.out.println("ID는 1~10자 사이로 입력해야 합니다. 다시 입력해주세요.");
			return false;
		}
		if (id.length>10) {		//길이 10자 검사
			System.out.println("ID는 1~10자 사이로 입력해야 합니다. 다시 입력해주세요.");
			return false;
		}
		for(int i=0; i<id.length; i++) {		//영어, 숫자, 공백, 개행 검사
			if((id[i]>=65 && id[i]<=90) || (id[i]>=97 && id[i]<=122) || (id[i]>=48 && id[i]<=57)) {
			}else {
				System.out.println("ID에는 영어, 숫자만 사용할 수 있습니다. 다시 입력해주세요.");
				return false;
			}
		}
		return true;

	}

	public static boolean checkPass(String str) {
		String passString = str;
		char[] pass = passString.toCharArray();
		if(passString.contains("\t") || passString.contains(" ")) {
			System.out.println("PASSWORD에는 영어, 숫자만 사용할 수 있습니다. 다시 입력해주세요.");
			return false;
		}
		if (pass.length==0) {		//길이 0자 검사
			System.out.println("PASSWORD는 1~10자 사이로 입력해야 합니다. 다시 입력해주세요.");
			return false;
		}
		if (pass.length>10) {		//길이 10자 검사
			System.out.println("PASSWORD는 1~10자 사이로 입력해야 합니다. 다시 입력해주세요.");
			return false;
		}
		for(int i=0; i<pass.length; i++) {		//영어, 숫자, 공백, 개행 검사
			if((pass[i]>=65 && pass[i]<=90) || (pass[i]>=97 && pass[i]<=122) || (pass[i]>=48 && pass[i]<=57)) {
			}else {
				System.out.println("PASSWORD에는 영어, 숫자만 사용할 수 있습니다. 다시 입력해주세요.");
				return false;
			}
		}
		//System.out.println("입력성공");
		return true;
	}
	
    /*
     * changeDate 역할
     * 1. 사용자 입력값(String)을 인자로 받음
     * 2. 공백 확인
     * 3. 숫자, 구분자 확인
     * 4. 년월일 자리수 확인
     * 5. 입력 받은 날짜를 "0000/00/00"의 형태로 변환하여 리턴
     */
    public static String changeDate(String input) {

        String original_input = input;                  // 최초 입력값 저장
        String[] date_arr;                              // 날짜가 저장될 배열
        String date_builder;                            // 날짜가 "0000/00/00"의 형태로 저장될 스트링
        String error = "0000/00/00";

        if (input.contains(" ") || input.contains("\t") ) {          // 공백, 탭 포함 시 모두 오류 리턴
            System.out.println("Wrong date format: 공백 포함");
            return error;
        }

        if (Pattern.matches("(^[0-9/-]*$)", input)) {           // 숫자, '/', '-' 이외의 문자가 포함될 경우 오류 리턴
            if (original_input.contains("/")) {                         // 최초 입력값에 '/'있으면 '-' 지우기
                input = input.replace("-", "");
            }
            if (original_input.contains("-")) {                         // 최초 입력값에 '-'있으면 '/' 지우기
                input = input.replace("/", "");
            }
        } else {
            System.out.println("Wrong date format: 문자");
            return error;
        }

        if (input.contains("/")) {                                  // '/' 있으면 '/'기준으로 나눠서 3개인지 확인
            date_arr = input.split("/");

            if (date_arr.length != 3) {
                System.out.println("Wrong date format: 구분자");
                return error;
            }
        } else if (input.contains("-")) {                           // '-' 있으면 '-'기준으로 나눠서 3개인지 확인
            date_arr = input.split("-");

            if (date_arr.length != 3) {
                System.out.println("Wrong date format: 구분자");
                return error;
            }
        } else {                                                    // '/' 또는 '-' 없으면 오류 리턴
            System.out.println("Wrong date format: 구분자");
            return error;
        }


        if (date_arr[0].length() == 4) {                        // 년이 4자리인지 확인

        } else if (date_arr[0].length() == 2) {                 // 년이 2자리일 경우 앞에 20 추가
            date_arr[0] = "20" + date_arr[0];
        } else {
            System.out.println("Wrong date format: 년");    // 2 또는 4 자리가 아닐 경우 오류 리턴
            return error;
        }

        if (date_arr[1].length() == 2) {                        // 달이 2자리인지 확인

        } else if (date_arr[1].length() == 1) {                 // 월이 1자리일 경우 앞에 0 추가
            date_arr[1] = "0" + date_arr[1];
        } else {
            System.out.println("Wrong date format: 월");    // 1 또는 2 자리가 아닐 경우 오류 리턴
            return error;
        }

        if (date_arr[2].length() == 2) {                        // 일이 2자리인지 확인

        } else if (date_arr[2].length() == 1) {                 // 일이 1자리일 경우 앞에 0 추가
            date_arr[2] = "0" + date_arr[2];
        } else {
            System.out.println("Wrong date format: 일");    // 1 또는 2 자리가 아닐 경우 오류 리턴
            return error;
        }

        date_builder = date_arr[0] + "/" + date_arr[1] + "/" + date_arr[2];     // "0000/00/00" 형태로 저장
        return date_builder;
    }
    /*
     * checkDate 역할
     * 1. changeDate()의 리턴값(String)과 예약여부(boolean)를 인자로 받음
     * 2. 실제 날짜인지 확인
     * 3. 원하는 범위 내 날짜인지 확인
     * 4. true / false 리턴
     */
    public static boolean checkDate(String input, boolean isReserve) {
        try {
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd");     // 이 형식의 날짜를 기준으로 실제 존재하는 날짜인지 판단
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(input);
        } catch (Exception e) {
            System.out.println("Wrong date format: 잘못된 날짜");
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");                // 이 형식으로 날짜 비교
        LocalDate input_date = LocalDate.parse(input, formatter);                               // 입력값 날짜 형식으로 변환

        LocalDate today = LocalDate.now();                                                      // 오늘
        LocalDate end_date = today.plusDays(6);                                                 // 오늘+6일

        if (input_date.isAfter(today.minusDays(1)) && input_date.isBefore(end_date)) {          // ('오늘'-1 < 입력값 < '오늘'+6일) 판단
            if (isReserve && input_date.isEqual(today)) {                                       // 만약 'isReserve = true' 이고 입력값이 오늘일 경우, 오류 반환
                System.out.println("Wrong date range: 예약은 당일에 불가능");
                return false;
            } else
                return true;
        } else {
            System.out.println("Wrong date range: 정해진 기간 외 날짜");
            return false;
        }
    }

	public String change_minute_to_hour(String k) {

		String standard_time = null;

		String start = null;
		String finish = null;
		int minute;
		String[] time=k.split("~");

		String a[]=time[0].split(":");
		String b[]=time[1].split(":");

		start=a[0]+":00";

		minute=Integer.parseInt(b[1]);
		if(minute>0&&minute<60) {
			int temp= Integer.parseInt(b[0])+1;
			if(temp==24) {
				finish="00:00";
			}
			else {
				finish=Integer.toString(temp)+":00";
			}

		}
		else {
			finish=b[0]+":00";
		}

		standard_time=start+"~"+finish;

		return standard_time;

	}
    
    public static  boolean checkTime(String input) {
		

		if(time_rule(input)==false)
			return false;
	
		else if(time_number_length(input)==false)
			return false;
		
		else if (time_range(input)==false)
			return false;

		
		else {
			
			
			//change_standard_time(input);// 시간을 보기 좋은 형태로 바꿈 (0:4 ->00:04)
			
			return true;
		}
	
		
	}
	



	public static  boolean time_rule(String a) {
		
		if(a.contains(":")) {
			boolean count=true;
			for(int i=0; i<a.length();i++) {
				
				if(a.charAt(i)==':') {
					count=true;
				}
			}
			
			if(count==true) {
				return true;
			}
			else {
		
				System.out.println("문자 : 가 2개 들어갔습니다.");
				 return false;
			}
				
		   
		}
		
		else {
			System.out.println("올바르지 않은 형식입니다.");
			return false;
		}
			
		
	} // 시간에 :가 한개 들어갔는지 확인한다.
	
	
	
	public  static boolean time_number_length(String a) {

		boolean check=true;
		
		if(a.equals(":")) {
			System.out.println("시와 분이 공백입니다.");
			return false;
		}
		
		String hour;
		String minute;
		String[] time=a.split(":");
		if(time.length==1) {
			System.out.println("분은 공백입니다.");
			
			return false;   //분이 공백인지 확인.
		}
		if(a.contains("::")) {
			System.out.println("올바르지 않은 입력입니다");
			return false;
		}
		hour=time[0];
		minute=time[1];	
		
		if(hour.length()==0) {
			System.out.println("시는 공백입니다.");
			return false;
		}
		
	
		
		for(int i=0; i<hour.length();i++) {
			
			if(hour.charAt(i)<'0'||hour.charAt(i)>'9') {
				System.out.println("시에 숫자외의 문자가 들어갔습니다.");
				check= false;
				break;
				
			}
		}
		
		for(int i=0; i<minute.length();i++) {
			
			if(minute.charAt(i)<'0'||minute.charAt(i)>'9') {
				System.out.println("분에 숫자외의 문자가 들어갔습니다.");
				check= false;
				break;
				
			}
		}
		
		
		if(hour.length()>2) {
			System.out.println("시의 길이가 올바르지 않습니다.");
			check=false;
		}
		
		if(minute.length()>2) {
			System.out.println("분의 길이가 올바르지 않습니다.");
			check= false;
		}
		
		return check;
	}// 시와 분이 숫자인지 확인한다. // 시와 분이 올바른 길이인지 확인한다.
	

	

	private static boolean time_range(String a) {
		
		boolean check=true;
		
		String hour;
		String minute;
		String[] time=a.split(":");
		
		if(time[0].charAt(0)=='0'&& time[0].length()==2) {
			hour=time[0].substring(1);
		}
		
		else {
			hour=time[0];
		}
		
		int hour2=Integer.valueOf(hour);
		if(hour2 <0 || hour2>23) {
			System.out.println("시의 범위가 올바르지 않습니다.");
			check=false;
		}
		
		
		if(time[1].charAt(0)=='0'&& time[1].length()==2) {
			minute=time[1].substring(1);
		}
		
		else {
			minute=time[1];
		}
		
		int minute2=Integer.valueOf(minute);
		if(minute2 <0 || minute2>59) {
			System.out.println("분의 범위가 올바르지 않습니다.");
			check=false;
		}
		
		return check;
	}//분과 시간 범위가 0~23인지 0~59인지 확인 
	
	
	public String change_standard_time(String a) {
		
		String standard_time = null;
		
		String hour;
		String minute;
		String[] time=a.split(":");
		
		if(time[0].length()==1) {
			hour="0"+time[0];
		}
		else
			hour=time[0];
		
		if(time[1].length()==1) {
			minute="0"+time[1];
		}
		else
			minute=time[1];
		
		standard_time=hour+":"+minute;

		
		
		return standard_time;
		
	}// 시간을 보기좋은 형태로 바꾸는 함수
    

    public boolean checkRoomName(String input) { // 회의실 이름 검사
        if (input.contains("\t") || input.contains(" ")) { // 탭, 띄어쓰기 있으면 false
            System.out.println("회의실 이름의 탭 또는 개행을 빼주세요.");
            return false;
        }
        if (input.length() < 3 || !input.substring(0, 3).equals("회의실")) { // 한글이 아니든, 길이가 짧든 '회의실'로 시작하지 않으면 false
            System.out.println("회의실 이름은 \"회의실\"로 시작되어야 합니다.");
            return false;
        }
        if (input.length() < 4 || !Pattern.matches("^[0-9]*$", input.substring(3))) { // '회의실' 뒤에 자연수가 아니면 false
            System.out.println("\"회의실\" 뒤에는 자연수여야 합니다.");
            return false;
        }
        if (input.charAt(3) == '0') { // '회의실0' false
        	
        		 System.out.println("\"회의실\" 뒤에 수는 0이나 선행 0을 허용하지 않습니다.");
        	
            return false;
        }
        if (Integer.parseInt(input.substring(3)) > 10) { // 기획서에 일단 적혀있음, 수정되면 뺼 것 (10 이상 false)
            System.out.println("\"회의실\" 뒤에 수는 10이하로 입력해주세요.");
            return false;
        }

        return true;
    }

    public boolean checkName(String input) { // 사용자 이름 검사
        if (input.length() < 1) { // 길이검사 (1~10)
            System.out.println("이름은 적어도 한 자 이상 입력해야 합니다. 다시 입력해주세요.");
            return false;
        }
        if (input.length() > 10) { // 길이검사 (1~10)
            System.out.println("이름은 10자 이내여야 합니다. 다시 입력해주세요.");
            return false;
        }
        
        if (input.contains("\t") || input.contains(" ")) { // 탭, 띄어쓰기 있으면 false
            System.out.println("이름은 한글 또는 영어로만 작성할 수 있습니다. 다시 입력해주세요.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z가-힣]*$", input)) { // 영어나 한글이 아닌 문자있으면 false
            System.out.println("이름은 한글 또는 영어로만 작성할 수 있습니다. 다시 입력해주세요.");
            return false;
        }
        if (input.contains("회의실")) { // 회의실 포함되어 있으면 false
            System.out.println("이름에는 \'회의실\'이라는 단어가 포함될 수 없습니다. 다시 입력해주세요.");
            return false;
        }
        return true;
    }

}
