package com.futuremove.cacheServer.test;

public class Food {
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	


	public Food() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Food(String name) {
		super();
		this.name = name;
	}


	public String name(){
		return "Food is good";
	}

}
