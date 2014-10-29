package org.funtester.plugin.selenium.model;

public class TranslatedStep {
	
	private String action;
	private String actionType;
	private String actionCode;
	private String value;
	private String element;
	private String internalTypeReference;
	private String codeLine;
	
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodeLine() {
		return codeLine;
	}
	public void setCodeLine(String codeLine) {
		this.codeLine = codeLine;
	}
	public String getInternalTypeReference() {
		return internalTypeReference;
	}
	public void setInternalTypeReference(String internalTypeReference) {
		this.internalTypeReference = internalTypeReference;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
