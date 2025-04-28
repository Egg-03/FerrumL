package com.ferruml.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;


/**
 * The methods of this class will query the WMIC class methods with wrong parameters to see if the error received is thrown as a ShellException
 */
class ShellExceptionTest {

	@Test
	void getTest() throws IndexOutOfBoundsException, IOException, InterruptedException {
		try {
			WMIC.getPropertiesAndTheirValues("FalseAttribute", "FalseValue");
			fail("Expected exception was not thrown");
		} catch (ShellException e) {
			Logger.debug(e.getMessage());
		} 
	}
	
	@Test
	void getWhereTest() throws IndexOutOfBoundsException, IOException, InterruptedException {
		try {
			WMIC.getPropertiesAndTheirValuesWhere(null, null, null, null);
			fail("Expected exception was not thrown");
		} catch (ShellException e) {
			Logger.debug(e.getMessage());
		} 
	}
	
	@Test
	void getIdTest() throws IndexOutOfBoundsException, IOException, InterruptedException {
		try {
			WMIC.getValue(null, null);
			fail("Expected exception was not thrown");
		} catch (ShellException e) {
			Logger.debug(e.getMessage());
		} 
	}
	
	@Test
	void getIdWhereTest() throws IndexOutOfBoundsException, IOException, InterruptedException {
		try {
			WMIC.getValueWhere(null, null, null, null);
			fail("Expected exception was not thrown");
		} catch (ShellException e) {
			Logger.debug(e.getMessage());
		} 
	}

}
