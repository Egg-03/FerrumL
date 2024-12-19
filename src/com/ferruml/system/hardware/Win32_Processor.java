package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class queries the Win32_Processor class of WMI and represents the CPU
 * details of your system.
 * <p>
 * Fetches the following properties: Name, NumberOfCores, ThreadCount,
 * NumberOfLogicalProcessor, Manufacturer, AddressWidth, L2CacheSize,
 * L3CacheSize, MaxClockSpeed, ExtClock, SocketDesignation, Version, Caption,
 * Family, Stepping, VirtualizationFirmwareEnabled, ProcessorID
 *
 * @author Egg-03
 * @version 1.3.0
 */
public class Win32_Processor{
	
	private static String alias = "Win32_Processor";
	private static String precedent = "DeviceID";
	
	private Win32_Processor() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * This method fetches a list of CPUs present in the system based on their IDs
	 *
	 * @return a {@link java.util.List} of CPU IDs
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getID(String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getID(String, String)}
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
	public static List<String> getProcessorList() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getID(alias, precedent);
	}
	
	/**
	 * This method fetches the CPU properties based on the given CPU ID
	 *
	 * @param currentProcessorID fetched from {@link Win32_Processor#getProcessorList()}
	 * @return a {@link java.util.Map} of CPU properties and their values mentioned
	 *         in the class description
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
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
	public static Map<String, String> getCurrentProcessor(String currentProcessorID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		String attributes = "Name, NumberOfCores, NumberOfLogicalProcessors, ThreadCount, Manufacturer, AddressWidth, L2CacheSize, L3CacheSize, MaxClockSpeed, ExtClock, SocketDesignation, Version, Caption, Family, Stepping, VirtualizationFirmwareEnabled, ProcessorID";
		return WMIC.getWhere(alias, precedent, currentProcessorID, attributes);
	}
}

