package executhelp;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import items.Intercepor;
import items.Result;

public class ExecutHelperImpl implements ExecutHelp {
	private String classpath=null;
	private String method=null;
	private String type;
	private String value;
	public java.util.List<Result> ResultsList;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	public  ExecutHelperImpl(String classpath,String method,List<Result> ResultsList,HttpServletRequest req,HttpServletResponse resp) {
		// TODO Auto-generated constructor stub
		this.classpath=classpath;
		this.method=method;
		this.ResultsList=ResultsList;
		this.req=req;
		this.resp=resp;
	}

	@Override
	public String doaction() throws Exception {
		// TODO Auto-generated method stub
		Class actionclass=Class.forName(classpath);
		Object action=actionclass.newInstance();
		if(actionclass == null)System.out.println("is null!");
		
		Method m=actionclass.getDeclaredMethod(method);
		System.out.println(method);
		if(m==null)System.out.println("method is null!");
		else System.out.println("method not null");
		String result = (String)m.invoke(action);
		if(result == null)System.out.print("result is null!");
		if(searchResult(result)) {
		doresult(req, resp, type, value);
		}else {
			System.out.println(result+" is not result!");
		}
		return result;
	}
	
	public void doresult(HttpServletRequest req,HttpServletResponse resp,String type,String value) throws ServletException, IOException{
		
		if(type.equals("forward")) {
			System.out.print("result is forward!");
			req.getRequestDispatcher(value).forward(req, resp);
			
		}
		if(type.equals("redirect")) {
			resp.sendRedirect(value);;
		}
	}
	
	private boolean searchResult(String result) {
		boolean isresult=false;
		for(Result  resul: ResultsList) {
			if(resul.getName().equals(result)) {
				System.out.println(result+" is result!");
				isresult=true;
//				Result=resul.getName();
				type=resul.getType();
				value=resul.getValue();
				System.out.println(resul);
				return isresult;
			}
		}
		return isresult;
	}
	
	

}
