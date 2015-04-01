package model;

public class Student {
	private String sid;
	private String name;
	private String sex;
	private int age;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Student(String sid, String name, String sex, int age) {
		super();
		this.sid = sid;
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
