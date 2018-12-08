package items;

public class Intercepor {
	private String name;
	private String class_path;
	private String predo;
	private String afterdo;
	
	public Intercepor(String name, String class_path, String predo, String afterdo) {
//		super();
		this.name = name;
		this.class_path = class_path;
		this.predo = predo;
		this.afterdo = afterdo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClass_path() {
		return class_path;
	}
	public void setClass_path(String class_path) {
		this.class_path = class_path;
	}
	public String getPredo() {
		return predo;
	}
	public void setPredo(String predo) {
		this.predo = predo;
	}
	public String getAfterdo() {
		return afterdo;
	}
	public void setAfterdo(String afterdo) {
		this.afterdo = afterdo;
	}
	@Override
	public String toString() {
		return "Intercepor [name=" + name + ", class_path=" + class_path + ", predo=" + predo + ", afterdo=" + afterdo
				+ "]";
	}
	

}
