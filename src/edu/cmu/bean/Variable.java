package edu.cmu.bean;

public class Variable {
	private int id;
	private String name;
	private String form;
	private String question;
	private String type;
	private int defined;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getForm() {
		return form;
	}
	public String getQuestion() {
		return question;
	}
	public String getType() {
		return type;
	}
	public int getDefined() {
		return defined;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDefined(int defined) {
		this.defined = defined;
	}
}
