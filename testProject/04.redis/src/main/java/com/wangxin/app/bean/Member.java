package com.wangxin.app.bean;

public class Member extends BaseModel{

	/**
	 *
	 */
	private static final long serialVersionUID = -1959528436584592183L;
	
	
	private String id;
	private Object nickname;
	
	public Member(){}
	
	public Member(String id, String nickname){
		this.setId(id);
		this.setNickname(nickname);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getNickname() {
		return nickname;
	}
	public void setNickname(Object nickname) {
		this.nickname = nickname;
	}
	
}