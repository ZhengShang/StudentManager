package model;

public class Course {
	private String cid;
	private String name;
	private float credit;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}
	public Course(String cid, String name, float credit) {
		super();
		this.cid = cid;
		this.name = name;
		this.credit = credit;
	}
	public Course() {
		super();
	}
	
	
}
