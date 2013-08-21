package edu.cmu.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dao.CRFDAO;
import dao.CommonUseDAO;
import dao.VariableDAO;
import dao.VariableRuleDAO;
import edu.cmu.bean.CRF;
import edu.cmu.bean.CommonUseScript;
import edu.cmu.bean.GrammarError;
import edu.cmu.bean.SampleTestLine;
import edu.cmu.bean.Variable;
import edu.cmu.bean.VariableRule;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("beans-config.xml");
	CommonUseDAO 	commonly = (CommonUseDAO) context.getBean("commonUse");
	CRFDAO 			crfdao = (CRFDAO) context.getBean("crfs");
	VariableRuleDAO vDao = (VariableRuleDAO) context.getBean("variableRule");
	VariableDAO variableDAO = (VariableDAO) context.getBean("variable");
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value= "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		
		List<CommonUseScript> slist = initScripts();
		List<CRF> cList = initCRFs();
		
		HttpSession session = request.getSession();
		session.setAttribute("scriptlist", slist);
		session.setAttribute("formlist", cList);
		session.setAttribute("activeform", "AE");
		model.addAttribute("displayoption", "display: none;");
		return "validation";
	}
		//get commonly use script as Init
		public List<CommonUseScript> initScripts() {
			List<CommonUseScript> slist = new ArrayList<CommonUseScript>();
			slist = commonly.findall();
			return slist;
		}
		
		public List<CRF> initCRFs() {
			List<CRF> cList = new ArrayList<CRF>();
			cList = crfdao.findCRF();
			return cList;
		}
	
	@RequestMapping(value = "/getform", method = RequestMethod.GET) 
	public String getFormVariable(Model model,
			@RequestParam(value="formname") String formName,
			HttpServletRequest request) {
		
		List<Variable> vlist = crfdao.getFormVariables(formName);
		
		HttpSession session = request.getSession();
		session.setAttribute("activeform", formName);
		session.setAttribute("variablelist", vlist);
		CRF crf = crfdao.getFormbyName(formName);
		session.setAttribute("code", crf.getScript());
		model.addAttribute("displayoption", "display: none");
		return "validation";
	}
	
	@RequestMapping(value = "/getrule", method = RequestMethod.GET) 
	public String getRule(Model model,
			@RequestParam(value="variable") String variable,
			HttpServletRequest request) {
		
		VariableRule v = vDao.findRule(variable);
		model.addAttribute("variablerule",v);
		model.addAttribute("displayoption", "display: none");
		return "validation";
	}
	
	//grammar check and save script function
	@RequestMapping(value = "/grammarcheck", method = RequestMethod.POST) 
	public String grammarCheck(Model model, 
			@RequestParam(value="submittype", required = false) String type, 
			@RequestParam(value="code", required = false) String code,
			@RequestParam(value="scriptid", required = false) String scriptid,
			@RequestParam(value="formname", required = false) String formname,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		
		if (type.equals("CommonUse")) {
			return commonUse(model, code, scriptid);
		}
		
		model.addAttribute("code", code);
		
		if (type.equals("GrammarCheck")) {
			//perform grammer check function, and return a list of error
			List<GrammarError> errors = performGrammarCheck(code, formname);
			if (errors.size() > 0) {
				model.addAttribute("errorlist", errors);
			} else {
				model.addAttribute("hasnoerror", "1");
			}
			model.addAttribute("displayoption", "");
		}
		
		if (type.equals("SaveScript")) {
			crfdao.saveScript(formname, code);
			model.addAttribute("hasnoerror", "2");
		}
		
		return "validation";
	}
	
	/**
	 * add common script function
	 */
	@RequestMapping(value = "/addcommonscript", method = RequestMethod.POST)
	public String addCommonlyUseScript(Model model, 
			@RequestParam(value="scriptname", required = false) String scriptname,
			@RequestParam(value="scriptcontent", required = false) String scriptcontent,
			HttpServletRequest request) {
		CommonUseScript c = new CommonUseScript();
		c.setName(scriptname);
		c.setScript(scriptcontent);
		commonly.add(c);
		List<CommonUseScript> slist = initScripts();
		HttpSession session = request.getSession();
		session.setAttribute("scriptlist", slist);
		model.addAttribute("displayoption", "display: none;");
		return "validation";
	}
	
	/**
	 * delete commonly use script function
	 */
	@RequestMapping(value = "/deletescript", method = RequestMethod.GET)
	public String deleteCommonlyUseScript(Model model, 
			@RequestParam(value="id") int id,
			HttpServletRequest request) {
		commonly.delete(id);
		List<CommonUseScript> slist = initScripts();
		HttpSession session = request.getSession();
		session.setAttribute("scriptlist", slist);
		model.addAttribute("displayoption", "display: none;");
		return "validation";
	}
	
	/** add rule function
	 * 
	 */
	@RequestMapping(value = "/addrule", method = RequestMethod.POST) 
	public String addRule(Model model, HttpServletRequest request) {
		VariableRule rule = new VariableRule();
		rule.setForm(request.getParameter("currentform"));
		rule.setVariableName(request.getParameter("variableName"));
		rule.setRequired(Integer.parseInt(request.getParameter("required")));
		rule.setRuleName(request.getParameter("ruleName"));
		rule.setCatetory(Integer.parseInt(request.getParameter("category")));
		rule.setDescription(request.getParameter("description"));
		rule.setRangeFrom(request.getParameter("rangeFrom"));
		rule.setRangeTo(request.getParameter("rangeTo"));
		rule.setUnit(request.getParameter("unit"));
		rule.setInvokeType(Integer.parseInt(request.getParameter("invokeType")));
		String currentCode = request.getParameter("code_embed");
		
		Variable var = variableDAO.findById(rule.getVariableName(), rule.getForm());
		variableDAO.updateSet(var);
		var = variableDAO.findById(rule.getVariableName(), rule.getForm());
		
		String varName = rule.getVariableName();
		String temp = "";
		if (rule.isRequired() > 0) {
			temp += "if (" + varName + " == null || " + varName + ".isEmpty()) {\n\treturn false;\n} \n";
		}
		
		if (rule.getRangeFrom() != null && rule.getRangeFrom().length() > 0) {
			temp += "if (" + varName + ".compareTo(\"" + rule.getRangeFrom().trim() + "\") < 0 || " + varName + ".compareTo(\"" + rule.getRangeTo().trim() + "\") >0 ) {\n\treturn false;\n}";
		}
		
		//System.out.println(temp);		
		
		vDao.add(rule);
		model.addAttribute("code", currentCode + "\n" + temp);
		List<Variable> vlist = crfdao.getFormVariables(rule.getForm());
		HttpSession session = request.getSession();
		session.setAttribute("variablelist", vlist);
		return "validation";
	}
	
	//sample test function
	@RequestMapping(value = "/sampletest", method = RequestMethod.POST) 
	public String sampleTest(Model model,
			@RequestParam(value = "variablenum") int variableNum,
			HttpServletRequest request) {
		
		if (variableNum == 0) return "validation";
		List<SampleTestLine> variableList = new ArrayList<SampleTestLine>();
		String testVar = "testvar";
		String testVal = "testval";
		for (int i = 0; i < variableNum; i++) {
			SampleTestLine temp = new SampleTestLine();
			temp.setName(request.getParameter(testVar+i));
			temp.setValue(request.getParameter(testVal+i));
			variableList.add(temp);
		}
		
		boolean testResult = false;
		
		//get test result, here call the Perform Sample Test function, return value is True or False
		//testResult = performSampleTest(variableList);
		
		if (testResult) {
			model.addAttribute("testresult", "True"); 
		} else  {
			model.addAttribute("testresult", "False"); 
		}
		return "validation";
	}
	
	/**
	 * common used script function
	 */
	public String commonUse(Model model, String currentCode, String scriptid) {
		CommonUseScript script;
		CommonUseDAO commonly = (CommonUseDAO) context.getBean("commonUse");
		script = commonly.findById(scriptid);
		currentCode = currentCode + "\n" +  script.getScript();
		model.addAttribute("code", currentCode);
		model.addAttribute("displayoption", "");
		return "validation";
	}
	
	/**
	 * perform grammar check
	 * @param code
	 * @return
	 */
	public List<GrammarError> performGrammarCheck(String code, String formname) { 
		List<GrammarError> errors = new ArrayList<GrammarError>();
		if (code.trim().length() == 0) return errors;
		GrammarCheck.GenerateFile(formname, code);
		errors = GrammarCheck.GetErrors(formname);
		
		return errors;
	}
}
