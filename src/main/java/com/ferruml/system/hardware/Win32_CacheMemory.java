package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query the Win32_CacheMemory class of WMI to
 * fetch CPU Cache information.
 * <p>
 * Fetches the following properties and their values: DeviceID, Purpose,
 * InstalledSize, Associativity
 *
 * @author Egg-03
 */
public class Win32_CacheMemory {
	
	private static String classname = "Win32_CacheMemory";
	
	private Win32_CacheMemory() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * This method returns the Cache Memory IDs of all the CPUs installed in the system
	 * @return a {@link java.util.List} of cacheIDs for all the CPUs
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
	public static List<String> getCacheID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		String attribute = "DeviceID";
		return WMIC.getID(classname, attribute);
	}
	
	/**
	 * This method returns the Cache Memory details of a CPU
	 *
	 * @param currentCacheID this ID is from the list of cacheIDs produced by
	 *                {@link Win32_CacheMemory#getCacheID()}
	 *                
	 * @return a particular level of cache memory details of a particular CPU at a
	 *         time
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
	public static Map<String, String> getCPUCache(String currentCacheID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		String attributes = "DeviceID, Purpose, InstalledSize, Associativity";
		return WMIC.getWhere(classname, "DeviceID", currentCacheID, attributes);
	}
}