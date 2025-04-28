package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query the Win32_PhysicalMemory class of WMI
 * to fetch RAM info.
 * <p>
 * The following properties are fetched: Name, Manufacturer, Model,
 * OtherIdentifyingInfo, PartNumber, Tag, FormFactor, BankLabel, Capacity,
 * DataWidth, Speed, ConfiguredClockSpeed, DeviceLocator, SerialNumber
 *
 * @author Egg-03
 */
public class Win32_PhysicalMemory {
	
	private static String classname = "Win32_PhysicalMemory";
	private static String attributes = "Name, Manufacturer, Model, OtherIdentifyingInfo, PartNumber, Tag, FormFactor, BankLabel, Capacity, DataWidth, Speed, ConfiguredClockSpeed, DeviceLocator, SerialNumber";
	
	private Win32_PhysicalMemory() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * Fetches a list of DeviceIDs of the memory sticks that are currently installed
	 * based on their Tag property
	 *
	 * @return a {@link java.util.List} of the Tags of the memory sticks
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getValue(String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getValue(String, String)}
	 *                                   when there is a parsing error of data
	 *                                   fetched from Windows Command Prompt
	 * @throws ShellException            if any internal command used in the
	 *                                   Command Prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	public static List<String> getTag() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getValue(classname, "Tag");
	}
	
	/**
	 * Fetches the properties mentioned in the class description based on the
	 * current memory stick Tag passed onto it
	 *
	 * @param memoryID the memory Tag fetched from {@link Win32_PhysicalMemory#getTag()}
	 * @return a {@link java.util.Map} of the properties and their values mentioned
	 *         in the class description
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getPropertiesAndTheirValuesWhere(String, String, String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getPropertiesAndTheirValuesWhere(String, String, String, String)}
	 *                                   when there is a parsing error of data
	 *                                   fetched from Windows Command Prompt
	 * @throws ShellException            if any internal command used in the
	 *                                   Command Prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	public static Map<String, String> getMemory(String memoryID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getPropertiesAndTheirValuesWhere(classname, "Tag", memoryID, attributes);
	}
}
