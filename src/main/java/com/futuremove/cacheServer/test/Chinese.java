package com.futuremove.cacheServer.test;

public class Chinese implements Human {
	private Food myFood;
	
    public Food getMyFood() {
		return myFood;
	}
	public void setMyFood(Food myFood) {
		this.myFood = myFood;
	}
	public void eat(){
    	System.out.println("i am chinese eat myFood, it is " + myFood.name());
    }
    public void walk(){
     	System.out.println("i am chinese walk"); 	
    }
}
