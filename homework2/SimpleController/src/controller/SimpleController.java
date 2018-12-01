package sc.ustc.controller;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;

import items.Action;
import items.Result;
import readXML.readXML;

import org.dom4j.*;
public class SimpleController extends HttpServlet {
		private String action;
		private String result;
		private String type;
		private String value;
		public static final String XML_PATH = "controller.xml";
		public ArrayList<Action> ActionsList;
		public java.util.List<Result> ResultsList;
		private boolean isAction=false;
		private boolean isResult=false;
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doPost(req, resp);
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//			将网址输出为字符串
		String url=req.getRequestURL().toString();
		System.out.println("url:"+url);
//		获得/之后的action
		String actionstring=url.substring(url.lastIndexOf("/")+1);
		actionstring=actionstring.substring(0, actionstring.indexOf("."));
		
//		action=actionstring.substring(0, actionstring.indexOf("."));
		System.out.println("action:"+actionstring);
		URL xmlPath = SimpleController.class.getClassLoader().getResource(XML_PATH);
//	
		System.out.println(xmlPath);
		String class_path=null;
		String method=null;
		try {
//			

			readXML reader=new readXML(xmlPath);
			System.out.println("XML is opened");
			ActionsList=reader.getActions();
			for(Action name:ActionsList) {
				if(name.getName().equals(actionstring)) {
					isAction=true;
					System.out.println(actionstring+" is action\n");
					class_path=name.getClassName();
					method=name.getMethodName();
					ResultsList=name.getResult();
					System.out.println(name);
				}
			}
			result=doaction(class_path,method);
			
			for(Result  resul: ResultsList) {
				if(resul.getName().equals(result)) {
					System.out.println(result+" is result!");
					isResult=true;
//					Result=resul.getName();
					type=resul.getType();
					value=resul.getValue();
					System.out.println(resul);
				}
			}
			doresult(req,resp,type, value);				
		}catch(Exception e1) {
			e1.printStackTrace();
		}finally{
			
		}
		dofailed(resp);
		}

		
//		
		private String doaction(String path,String method) throws Exception {
			Class actionclass=Class.forName(path);
			Object action=actionclass.newInstance();
			if(actionclass == null)System.out.println("is null!");
			
			Method m=actionclass.getDeclaredMethod(method);
			System.out.println(method);
			if(m==null)System.out.println("method is null!");
			else System.out.println("method not null");
			String result = (String)m.invoke(action);
			if(result == null)System.out.print("result is null!");
			return result;
		}
		
		private void doresult(HttpServletRequest req,HttpServletResponse resp,String type,String value) throws ServletException, IOException{
		if(type.equals("forward")) {
			System.out.print("result is forward!");
			req.getRequestDispatcher(value).forward(req, resp);
			
		}
		if(type.equals("redirect")) {
			resp.sendRedirect(value);;
		}
		}

		private void dofailed(HttpServletResponse resp)throws IOException{
			if(isAction||isResult) {
				resp.setContentType("text/html;charset=UTF-8");
				
		        PrintWriter out = resp.getWriter();
		        out.println("<html>\n"+ "<head><title>no result"+"</title></head>\n"+"<body>"+"没有请求的资源"+"</body>\n"+"</html>");
			}
			
		}
}
