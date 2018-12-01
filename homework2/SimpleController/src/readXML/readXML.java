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
	public ArrayList<items.Action> actionList=new ArrayList<>();
	public ArrayList<Result> resultList=new ArrayList<>();
	
	public readXML(URL xmlPath) throws Exception{
		document=reader.read(xmlPath);
	}
//	public void openXML(URL path) throws Exception{
////		File file=new File(path);
//		document=reader.read(path);
//	}
	public ArrayList<Action> getActions(){
		List<Element> list ;
		list=document.getRootElement().elements();
		Result ress;
		Action a;
		for(Element e:list) {
			List<Element> res=e.elements();
			for(Element e1:res) {
				ress= new Result(e1.attributeValue("name"), e1.attributeValue("type"), e1.attributeValue("value"));
				System.out.println(ress);
				resultList.add(ress);
			}
			
			a=new Action(e.attributeValue("name"), e.attributeValue("class"),e.attributeValue("method"),resultList );
			
			actionList.add(a);
			System.out.println(a);
		}
		return actionList;
	}
	
	
}
