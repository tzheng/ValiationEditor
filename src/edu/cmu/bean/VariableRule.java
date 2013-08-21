package edu.cmu.bean;

public class VariableRule {
	private String form;
	private String variableName;
	private int required;
	private String ruleName;
	private int catetory;
	private String description;
	private String rangeFrom;
	private String rangeTo;
	private String unit;
	private int invokeType;
	
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public int isRequired() {
		return required;
	}
	public void setRequired(int required) {
		this.required = required;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public int getCatetory() {
		return catetory;
	}
	public void setCatetory(int catetory) {
		this.catetory = catetory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRangeFrom() {
		return rangeFrom;
	}
	public void setRangeFrom(String rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	public String getRangeTo() {
		return rangeTo;
	}
	public void setRangeTo(String rangeTo) {
		this.rangeTo = rangeTo;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getInvokeType() {
		return invokeType;
	}
	public void setInvokeType(int invokeType) {
		this.invokeType = invokeType;
	}
	
	
	
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String toString() {
		return variableName + " " + required + " " + ruleName+ " " + catetory+ " " + description+ " " + rangeFrom+ " " + rangeTo+ " " + unit+ " " + invokeType;
	}
}
