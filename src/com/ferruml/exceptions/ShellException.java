package com.ferruml.exceptions;

/**
 * This is a custom exception class that is thrown when there are errors from the powershell's output
 *
 * @author Egg-03
 * @version 1.3.0
 */
public class ShellException extends Exception{
	private static final long serialVersionUID = -1084296875845871945L;

	public ShellException(String message) {
		super(message);
	}
}
