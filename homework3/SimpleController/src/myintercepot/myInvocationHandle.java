package myintercepot;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

import items.Intercepor;
import items.Result;
import items.interceptorimpl;
import water.ustc.interceptor.LogInterceptor;

public class myInvocationHandle implements InvocationHandler {

	private Object target;
	private ArrayList<String> InterceptorRef;
	private ArrayList<Intercepor> InterceptorList;
	private ArrayList<interceptorimpl> interceptorStack;
	public myInvocationHandle(Object target,ArrayList<String>InterceptorRef,ArrayList<Intercepor>InterceptorList) {
		// TODO Auto-generated constructor stub
		this.target=target;
		this.InterceptorRef=InterceptorRef;
		this.InterceptorList=InterceptorList;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		adjustIntercepor();
		InterceptorInstance();
		preInvoke();
		System.out.println("predo is finished");
		String result=(String)method.invoke(target);
		System.out.println(method+" is finished");
		afterInvoke(result);
		System.out.println("afterdo is finished");
		return null;
	}
	
	private void adjustIntercepor() {
		ArrayList<Intercepor> newinterceptorlist=new ArrayList<>();
		for(String s:InterceptorRef) {
			for(Intercepor e:InterceptorList) {
				if(s.equals(e.getName())) {
					newinterceptorlist.add(e);
				}
			}
		}
		InterceptorList=newinterceptorlist;
	}
	
	private void InterceptorInstance()throws Exception {
		interceptorimpl impl;
		ArrayList<interceptorimpl> implStack=new ArrayList<>();
		for(Intercepor e:InterceptorList){
			Class clazz=Class.forName(e.getClass_path());
			Object obj=clazz.newInstance();
			Method start=clazz.getDeclaredMethod(e.getPredo());
			Method after=clazz.getDeclaredMethod(e.getAfterdo(), String.class);
			impl=new interceptorimpl(start, after, obj);
			implStack.add(impl);
		}
		interceptorStack=implStack;
	}
	private void preInvoke() throws Exception{
		for(interceptorimpl e:interceptorStack) {
			e.dopre();
		}
	}
	private void afterInvoke(String res)throws Exception{
		for(int i=interceptorStack.size()-1;i>=0;i--) {
			interceptorStack.get(i).doafter(res);
		}
		
	}
}
