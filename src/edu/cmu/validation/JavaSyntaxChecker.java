package edu.cmu.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.jndi.url.ldaps.ldapsURLContextFactory;

import edu.cmu.bean.GrammarError;

public class JavaSyntaxChecker {
	private static String suffix = "location";
	private static String path;
	
	/**
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getSuffix() {
		return suffix;
	} 
	**/
	
    public static List<GrammarError> check(String file, int linenum) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        List<GrammarError> messages = new ArrayList<GrammarError>();
        
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            messages.add(getDiagnosticInfo(diagnostic, linenum));
        }

        return messages;
    }
    
    private static GrammarError getDiagnosticInfo(Diagnostic<? extends JavaFileObject> diagnostic, int linenum) {
    	GrammarError gError = new GrammarError();
    	String localMessage = diagnostic.getMessage(Locale.ROOT);
    	int index = localMessage.lastIndexOf(suffix);
    	String content = (index == -1 ?localMessage :localMessage.substring(0, index));
    	int offset = content.indexOf(' ');
    	if (offset > -1)
    		content = content.substring(offset + 1);
    	gError.setContent(content);
    	gError.setLine("Line " + (diagnostic.getLineNumber() - linenum));
    	
    	return gError;
    }

}