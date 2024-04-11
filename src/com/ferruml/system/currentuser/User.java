package com.ferruml.system.currentuser;

public class User {
	private User(){
		throw new IllegalStateException("Utility Class");
	}
	
	public static String getUsername() {
		return System.getProperty("user.name");
	}
	
	public static String getHome() {
		return System.getProperty("user.home");
	}
	
	public static String getDirectory() {
		return System.getProperty("user.dir");
	}
}
