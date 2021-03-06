package org.funtester.plugin.selenium.model;

public class CodeGenerationException extends Exception {
	
	private static final long serialVersionUID = 7552333235915301270L;

	public CodeGenerationException() {
		super();
	}

	public CodeGenerationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public CodeGenerationException(String message, Throwable cause) {
		super( message, cause );
	}

	public CodeGenerationException(String message) {
		super( message );
	}

	public CodeGenerationException(Throwable cause) {
		super( cause );
	}
	
}
