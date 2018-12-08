package water.ustc.interceptor;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogInterceptor {
//	private String actionname;
	private String starttime;
	private String endtime; 
	private Date stime;
	private Date etime;
	
	public void getAname() {
		
	}
	
	private void setstime() {
		stime=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		starttime=df.format(stime);
		
	}
	

	private void setetime() {
		etime=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		endtime=df.format(etime);
		
	}
	public void getStime() {
		System.out.println(starttime);
	}
	
	public void getEtime() {
		System.out.println(endtime);
	}
	public void preAction() {
		setstime();
		getStime();
	}
	public void afterAction(String result) {
		setetime();
		getEtime();
		String path=this.getClass().getClassLoader().getResource("/").getPath();
		System.out.println(path);
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n"+"<log>"+"\n"+"    "+"\n"
		+"      <name>log</name>"+"\n"+"	  <s-time>"+starttime+"</s-time>"+
				"	  <e-time>"+endtime+"</e-time>"+"\n"+
		"	  <result>"+result+"</result>"+"	</acton>"+"\n"+"</log>";
		try {
		File file=new File("log.xml");
		if(!file.exists()) {
			file.createNewFile();
			System.out.println("log.xml is not found!");
		}
		System.out.println("file is opened");
		FileWriter fileWriter=new FileWriter(file.getName());
		fileWriter.write(xml);
		System.out.println("file is writed");
		fileWriter.close();
		System.out.println(file.getAbsolutePath());
		System.out.println(xml);
		System.out.println("Done");
	}catch (Exception e){
		e.printStackTrace();
	}
	}

}
