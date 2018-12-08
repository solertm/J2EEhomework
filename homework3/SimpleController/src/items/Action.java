package items;

import java.util.ArrayList;
import java.util.List;

public class Action extends java.awt.List{
	public static final String ACTION_TAG = "action";
	
	public static final String NAME = "name";
	public static final String CLASS_NAME = "class";
	public static final String METHOD_NAME = "method";

	private String name;
	private String className;
	private String methodName;
	private ArrayList<String> interceptors=new ArrayList<>();
	private List<Result> result;
	

	public Action(String name, String className, String methodName,List<Result> result,ArrayList<String> interceptors) {
		this.name = name;
		this.className = className;
		this.methodName = methodName;
		this.result = result;
		this.interceptors=interceptors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

	public ArrayList<String> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(ArrayList<String> interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public String toString() {
		return "Action [name=" + name + ", className=" + className + ", methodName=" + methodName + ", interceptors="
				+ interceptors + ", result=" + result + "]";
	}

//	@Override
//	public String toString() {
//		return "Action [name=" + name + ", className=" + className + ", methodName=" + methodName + ", result=" + result
//				+ "]";
//	}
	
	
}
