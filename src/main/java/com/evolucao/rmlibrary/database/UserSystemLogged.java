package com.evolucao.rmlibrary.database;

public class UserSystemLogged {
	private String userName = null;
	String ipAddress = null;
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpAddress() {
		return this.ipAddress;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return this.userName;
	}
}
