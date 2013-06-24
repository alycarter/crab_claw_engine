package com.alycarter.crabClawEngine.state;

import java.util.Stack;


public class StateMachine {
	private Stack<State> states = new Stack<State>();
	
	public StateMachine() {
		
	}
	
	public State getCurrentState(){
		if(!states.empty()){
			return states.peek();			
		}else{
			return null;
		}
	}
	
	public void pop(){
		if(!states.empty()){
			states.pop();
		}
	}
	
	public void push(State state){
		states.push(state);
	}

}
