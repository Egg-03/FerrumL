package com.ferruml.exceptions;

/**
 * This is a custom exception class that is thrown when there are errors from command prompt's output
 * @author Egg-03
 */
public class ShellException extends Exception{
	private static final long serialVersionUID = -1084296875845871945L;
	
	/**
	 * Constructor for ShellException
	 * @param message The message to be displayed when the exception is thrown
	 */
	public ShellException(String message) {
		super(message);
	}
}
