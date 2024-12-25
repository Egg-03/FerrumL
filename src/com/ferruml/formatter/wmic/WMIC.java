package com.ferruml.formatter.wmic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;

/**
 * This class queries all the WMI Classes based on the attributes passed to it's
 * one of the four methods called by the methods in other packages.
 * 
 * @author Egg-03
 * @version 1.3.1
 */
public class WMIC {
	private static String systemDriveLetter = System.getenv("SystemDrive").toLowerCase().substring(0, 1);

	private WMIC() {
		throw new IllegalStateException("Utility Class");
	}

	/**
	 * Internally runs the command "wmic path {@literal win32Class} get
	 * {@literal Key} / format:list" where the parameters are provided by the
	 * calling methods
	 * <p>
	 * This essentially returns a List of the values provided by the {@literal key}
	 * present in the {@literal Win32_Class}
	 * <p>
	 * Example Usage: WMIC.getID(Win32_Processor, "DeviceID"); This will fetch a
	 * list of CPU IDs provided by the instances of the Win32_Processor class
	 * 
	 * @param WMIC_Class the class name passed to by the method calling it
	 * @param Key        passed to by the method calling it
	 * @return a list of values requested by the method calling it. The values
	 *         returned are of the property {@literal Key}
	 * @throws IOException               in case of general I/O errors
	 * @throws IndexOutOfBoundsException in case of text parsing issues from command
	 *                                   prompt
	 * @throws ShellException            if any internal command used in the command
	 *                                   prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	public static List<String> getID(String WMIC_Class, String Key) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		String[] command = { "cmd", "/" + systemDriveLetter,
				"wmic path " + WMIC_Class + " get " + Key + " /format:list" };
		Process process = Runtime.getRuntime().exec(command);

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			errorCapture(process, exitCode);
		}

		return attributeValues(process);
	}

	/**
	 * Internally runs the command "wmic path {@literal Win32_Class} where
	 * {@literal determinantProperty} = {@literal determinantValue} get
	 * {@literal extractProperty} /format:list" where the parameters are provided by
	 * the calling methods
	 * <p>
	 * This essentially returns a List of the values provided by the
	 * {@literal extractProperty} present in the {@literal Win32_Class} but only
	 * when another property {@literal determinantProperty} equals to
	 * {@literal determinantValue}
	 * <p>
	 * Example Usage: WMIC.getIDWhere("Win32_NetworkAdapter", "NetEnabled", "true",
	 * "DeviceID"); This will fetch a list of DeviceIDs for Network Adapters from
	 * the Win32_NetworkAdapter class provided that their NetEnabled property is
	 * true
	 * 
	 * @param WMIC_Class          the classname passed to by the calling method
	 * @param determinantProperty a filtering parameter, passed to by the calling
	 *                            method
	 * @param determinantValue    the value of the filtering parameter, also passed
	 *                            to by the calling method
	 * @param extractProperty     the property to be extracted, provided by the
	 *                            calling method.
	 * @return a list of values requested by the method calling it. The values
	 *         returned are the values of the property {@literal extractProperty}
	 * @throws IOException               in case of general I/O errors
	 * @throws IndexOutOfBoundsException in case of text parsing issues from command
	 *                                   prompt
	 * @throws ShellException            if any internal command used in the command
	 *                                   prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	public static List<String> getIDWhere(String WMIC_Class, String determinantProperty, String determinantValue, String extractProperty) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		String[] command = { "cmd", "/" + systemDriveLetter, "wmic path " + WMIC_Class + " where " + determinantProperty
				+ "=" + "\"" + determinantValue + "\"" + " get " + extractProperty + " /format:list" };
		Process process = Runtime.getRuntime().exec(command);
		
		int exitCode = process.waitFor();
		if (exitCode != 0) {
			errorCapture(process, exitCode);
		}

		return attributeValues(process);
	}

	/**
	 * Internally runs the command "wmic path {@literal WMIC_Class} get
	 * {@literal WMIC_Attributes} /format:list" where the parameters are provided by
	 * the calling methods
	 * <p>
	 * This returns a Map of attributes defined by {@literal WMIC_Attributes} for
	 * and their values for the class {@literal WMIC_Class} The
	 * {@literal WMIC_Attributes}a re stored as keys and their values are stored as
	 * values in a key-value pair in the map
	 * <p>
	 * Example Usage: WMIC.get("Win32_Baseboard, "Manufacturer, Model, Product,
	 * SerialNumber, Version"); This fetches a {@link java.util.Map} of attributes
	 * and their values from the Baseboard class
	 * 
	 * @param WMIC_Class      the class name passed to by the calling method
	 * @param WMIC_Attributes a list of properties requested for a particular class.
	 *                        The properties requested by the calling methods can be
	 *                        found in their respective class descriptions
	 * @return a {@link java.util.Map} of the attribute values requested by the
	 *         calling method
	 * @throws IOException               in case of general I/O errors
	 * @throws IndexOutOfBoundsException in case of text parsing issues from command
	 *                                   prompt
	 * @throws ShellException            if any internal command used in the command
	 *                                   prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	public static Map<String, String> get(String WMIC_Class, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {

		String[] command = { "cmd", "/" + systemDriveLetter,
				"wmic path " + WMIC_Class + " get " + WMIC_Attributes + " /format:list" };
		Process process = Runtime.getRuntime().exec(command);

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			errorCapture(process, exitCode);
		}

		return attributesAndTheirValues(process);
	}

	/**
	 * Internally runs the command "wmic path {@literal WMIC_Class} where
	 * {@literal determinantProperty} = {@literal determinantValue} get
	 * {@literal WMIC_Attributes} /format:list" where the parameters are provided by
	 * the calling methods
	 * <p>
	 * This returns a {@link java.util.Map} of attributes defined by
	 * {@literal WMIC_Attributes} for and their values for the class
	 * {@literal WMIC_Class} but this time the attributes you get are determined by
	 * {@literal determinantProperty} and its value {@literal determinantValue} The
	 * {@literal WMIC_Attributes}a re stored as keys and their values are stored as
	 * values in a key-value pair in the map
	 * <p>
	 * Example Usage: WMIC.getWhere("Win32_BIOS", "PrimaryBIOS", "true",
	 * attributes); This fetches the attributes and their values of the BIOS if it's
	 * a Primary BIOS.
	 * 
	 * @param WMIC_Class          the class name passed to by the calling method
	 * @param WMIC_Attributes     a list of properties requested for a particular
	 *                            class. The properties requested by the calling
	 *                            methods can be found in their respective class
	 *                            descriptions
	 * @param determinantProperty a filtering parameter, passed to by the calling
	 *                            method
	 * @param determinantValue    the value of the filtering parameter, also passed
	 *                            to by the calling method
	 * @return a {@link java.util.Map} of the attribute values requested by the
	 *         calling method
	 * @throws IOException               in case of general I/O errors
	 * @throws IndexOutOfBoundsException in case of text parsing issues from command
	 *                                   prompt
	 * @throws ShellException            if any internal command used in the command
	 *                                   prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */

	public static Map<String, String> getWhere(String WMIC_Class, String determinantProperty, String determinantValue, String WMIC_Attributes) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {

		String[] command = { "cmd", "/" + systemDriveLetter, "wmic path " + WMIC_Class + " where " + determinantProperty
				+ "=" + "\"" + determinantValue + "\"" + " get " + WMIC_Attributes + " /format:list" };
		Process process = Runtime.getRuntime().exec(command);

		int exitCode = process.waitFor();
		if (exitCode != 0) {
			errorCapture(process, exitCode);
		}

		return attributesAndTheirValues(process);
	}
	
	/**
	 * 
	 * @param process
	 * @param exitCode
	 * @throws IOException
	 * @throws ShellException
	 */
	private static void errorCapture(Process process, int exitCode) throws IOException, ShellException {
		try(BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
			String errorLine;
			StringBuilder errorLines = new StringBuilder();

			while ((errorLine = error.readLine()) != null)
				if (!errorLine.isBlank() || !errorLine.isEmpty())
					errorLines.append(errorLine);

			throw new ShellException(errorLines.toString()+ "\nProcess Exited with code:" + exitCode + "\n");
		}
		
	}
	
	/**
	 * Captures command prompt errors and throw them as ShellExceptions in case the process exit code is not 0
	 * @param process
	 * @return
	 * @throws IOException
	 */
	private static List<String> attributeValues(Process process) throws IOException {
		try(BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String currentLine = "";
			String value = "";
			
			List<String> ID = new ArrayList<>();

			while ((currentLine = stream.readLine()) != null)
				if (!currentLine.isBlank() || !currentLine.isEmpty()) {
					if (currentLine.contains("=")) {
						value = currentLine.substring(currentLine.indexOf("=") + 1).strip();
						ID.add(value);
					} else {
						value = value.concat(currentLine);
						ID.add(ID.indexOf(ID.getLast()), value);
					}
				}
			return ID;
		}
	}
	
	/**
	 * Returns a map of attribute names and their values as a key-value pair with attribute name being the key and the attribute value being the value
	 * @param process
	 * @return
	 * @throws IOException
	 */
	private static Map<String, String> attributesAndTheirValues(Process process) throws IOException {
		try(BufferedReader stream = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String currentLine = "";
			String key = "";
			String value = "";
			Map<String, String> attributesAndValues = new LinkedHashMap<>();

			while ((currentLine = stream.readLine()) != null)
				if (!currentLine.isBlank() || !currentLine.isEmpty()) {
					if (currentLine.contains("=")) {
						key = currentLine.substring(0, currentLine.indexOf("=")).strip();
						value = currentLine.substring(currentLine.indexOf("=") + 1).strip();
						attributesAndValues.put(key, value);
					} else {
						value = value.concat(currentLine);
						attributesAndValues.replace(key, value);
					}
				}
			return attributesAndValues;
		}
	}
}