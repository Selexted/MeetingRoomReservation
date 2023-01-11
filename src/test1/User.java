package test1;

public class User {
    String name;
    String id;
    String pw;
    int rank; //0:사원, 1:임원, 2:관리자
    int value;

    User(String name, String id, String pw, int rank, int value){
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.rank = rank;
        this.value = value;
    }
}