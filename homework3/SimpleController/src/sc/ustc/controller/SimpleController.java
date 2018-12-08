package sc.ustc.controller;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

import executhelp.ExecutHelp;
import executhelp.ExecutHelperImpl;
import items.Action;
import items.Intercepor;
import items.Result;
import myintercepot.myInvocationHandle;
import readXML.readXML;

import org.dom4j.*;
public class SimpleController extends HttpServlet {
		private String action;
		private String result;
		private String type;
		private String value;
		private String class_path=null;
		private String method=null;
		public static final String XML_PATH = "controller.xml";
		public ArrayList<Action> ActionsList;
		public java.util.List<Result> ResultsList;
		public ArrayList<Intercepor> InterceptorList;
		public ArrayList<String> InterceptorRef;
		private boolean isAction=false;
		private boolean isResult=false;
		private boolean isInterceptor=false;
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
		System.out.println("action:"+actionstring);
		URL xmlPath = SimpleController.class.getClassLoader().getResource(XML_PATH);
//	
		System.out.println(xmlPath);
		try {
			readXML reader=new readXML(xmlPath);
			System.out.println("XML is opened");
			ActionsList=reader.getActions();
			InterceptorList=reader.getinterceptorelement();	
			if(searchAction(actionstring)) {
				searchInterceptor();
				ExecutHelp aAction=new ExecutHelperImpl(class_path, method, ResultsList, req, resp);
				myInvocationHandle handler=new myInvocationHandle(aAction,InterceptorRef,InterceptorList);
				ExecutHelp proxy=(ExecutHelp)Proxy.newProxyInstance(ExecutHelperImpl.class.getClassLoader(), new Class<?>[] {ExecutHelp.class}, handler);
				result=proxy.doaction();
				searchResult();
				System.out.println(SimpleController.class.getClassLoader().getResource("log.xml"));
			}else {dofailed(resp);}
		}catch(Exception e1) {
			e1.printStackTrace();
		}finally{
			
		}
		dofailed(resp);
		}

		
//		

		private void dofailed(HttpServletResponse resp)throws IOException{
			if(isAction||isResult) {
				resp.setContentType("text/html;charset=UTF-8");
				
		        PrintWriter out = resp.getWriter();
		        out.println("<html>\n"+ "<head><title>no result"+"</title></head>\n"+"<body>"+"没有请求的资源"+"</body>\n"+"</html>");
			}
			
		}
		private boolean searchAction(String actionstring) {
			for(Action name:ActionsList) {
				if(name.getName().equals(actionstring)) {
					isAction=true;
					System.out.println(actionstring+" is action\n");
					action=actionstring;
					class_path=name.getClassName();
					method=name.getMethodName();
					ResultsList=name.getResult();
					InterceptorRef=name.getInterceptors();
					System.out.println(name);
					return isAction;
				}
			}
			return isAction;
		}
		
		private void searchResult() {
			for(Result  resul: ResultsList) {
				if(resul.getName().equals(result)) {
					System.out.println(result+" is result!");
					isResult=true;
					type=resul.getType();
					value=resul.getValue();
					System.out.println(resul);
				}
			}
		}
		
		private void searchInterceptor() {
			for(String e:InterceptorRef) {
				for(Intercepor a:InterceptorList) {
					if(e.equals(a.getName())) {isInterceptor=true;System.out.println("ref is not null");}
				}
//				if(isInterceptor==false) {
//					System.out.println(e+"is not a interceptor");
//					InterceptorRef.remove(e);					
//				}
			}
			System.out.println(InterceptorRef);
		}
}
