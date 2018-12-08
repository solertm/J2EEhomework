package readXML;

import java.io.File;
import java.net.URL;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import items.*;
import items.Result;

public class readXML {
	public SAXReader reader=new SAXReader();
	public Document document;
	public URL xmlPath;
	public String xmlpath;
	public ArrayList<items.Action> actionList=new ArrayList<>();
	public ArrayList<Result> resultList=new ArrayList<>();
	public ArrayList<Intercepor> interceptorList=new ArrayList<>();
	
	public readXML(URL xmlPath) throws Exception{
		document=reader.read(xmlPath);
	}
	public readXML(String xmlpath)throws Exception{
		document=reader.read(xmlpath);
	}
//	public void openXML(URL path) throws Exception{
////		File file=new File(path);
//		document=reader.read(path);
//	}
	public List<Element> getactionelement(){
		List<Element> list;
		list=document.getRootElement().elements();
//		List<Element> elements;
		for(Element e:list) {
			System.out.println(e.getName());
			if(e.getName().equals("controller")) {
			List<Element> elements=e.elements();
			return elements;
			}
		}
		
		return list;
	}
	
	public ArrayList<Intercepor> getinterceptorelement(){
		List<Element> list;
		list=document.getRootElement().elements();
		Intercepor inter;
		for(Element e:list) {
			System.out.println(e.getName());
			if(e.getName().equals("interceptor")) {
				inter=new Intercepor(e.attributeValue("name"), e.attributeValue("class"), e.attributeValue("predo"), e.attributeValue("afterdo"));
				System.out.println(inter);
				interceptorList.add(inter);
				System.out.println(interceptorList);
				return interceptorList;
			}
		}
		return interceptorList;
	}
	
	public ArrayList<Action> getActions(){
		List<Element> list ;
		list=getactionelement();
		Result ress;
		Action a;
		for(Element e:list) {
			List<Element> res=e.elements();
			ArrayList<String> interceptors=new ArrayList<>();
			ArrayList<Result> resultlist=new ArrayList<>();
			for(Element e1:res) {
				if(e1.getName().equals("interceptor-ref")) {
					interceptors.add(e1.attributeValue("name"));
					System.out.println(e1.attributeValue("name"));
					
				}
				if(e1.getName().equals("result")) {
				ress= new Result(e1.attributeValue("name"), e1.attributeValue("type"), e1.attributeValue("value"));
				System.out.println(ress);
				resultList.add(ress);
				resultlist.add(ress);
				}
			}
			
			a=new Action(e.attributeValue("name"), e.attributeValue("class"),e.attributeValue("method"),resultlist ,interceptors);
			
			actionList.add(a);
			System.out.println(a);
		}
		System.out.println(actionList);
		return actionList;
	}
	
//	public ArrayList<Intercepor> getInterceptor(){
//		List<Element> list;
//		list=getinterceptorelement();
//		
//	}
	
	
}
