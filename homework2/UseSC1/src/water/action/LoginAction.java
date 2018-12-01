package water.ustc.action;

import java.util.Random;

public class LoginAction {
	public String handleLogin() {
		
		double i=Math.random();
		if(i>=0.5) {
		return "success";
	}
		else return "failure";
	}
}
