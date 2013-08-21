package edu.cmu.validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.CRFDAO;
import edu.cmu.bean.GrammarError;
import edu.cmu.bean.Variable;


public class GrammarCheck {
	static ApplicationContext context = new ClassPathXmlApplicationContext("beans-config.xml");
	static CRFDAO 			crfdao = (CRFDAO) context.getBean("crfs");
	private static String header = "import java.util.Date;" + "\n" + "public class Test {" + "\n";
	private static String methodName = "	public boolean Script() { " + "\n";
	private static String footer = "	return true; }" + "\n" + "}";
	
	public static void GenerateFile(String form, String code) {
		String path = "Test.java";
		
		try {
			File f = new File(path);
			f.createNewFile();
			
			List<Variable> vlist = crfdao.getFormVariables(form);
			StringBuilder sb = new StringBuilder();
			sb.append(header);
			for (int i = 0; i < vlist.size(); i++) {
				Variable v = vlist.get(i);
				String tmp = "private ";
				tmp += v.getType() + " ";
				tmp += v.getName() + ";" + "\n";
				sb.append(tmp);		
			}
			sb.append(methodName);
			sb.append(code);
			sb.append("\n");
			sb.append(footer);
			
			//System.out.println(sb.toString());
			
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(sb.toString());
			output.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<GrammarError> GetErrors(String form) {
		List<Variable> vlist = crfdao.getFormVariables(form);
		int linenum = vlist.size() + 3;
		List<GrammarError> gList = JavaSyntaxChecker.check("Test.java", linenum);
		
		/**
		System.out.println(gList.size());
    	for (int i = 0; i < gList.size(); i++) {
    		System.out.println(gList.get(i).getLine() + " " + gList.get(i).getContent());
    	} 
    	**/
    	return gList;
	}
}
