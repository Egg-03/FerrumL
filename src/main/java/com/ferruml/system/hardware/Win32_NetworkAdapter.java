package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query the Win32_NetworkAdapter class of WMI
 * to fetch the Network Adapters that a installed and active.
 * <p>
 * The following properties are fetched: Name, Description, PNPDeviceID,
 * MACAddress, Installed, NetEnabled, NetConnectionID, PhysicalAdapter,
 * TimeOfLastReset
 *
 * @author Egg-03
 */
public class Win32_NetworkAdapter {

	private static String classname = "Win32_NetworkAdapter";
	private static String attributes = "Name, Description, PNPDeviceID, MACAddress, Installed, NetEnabled, NetConnectionID, PhysicalAdapter, TimeOfLastReset";

	private Win32_NetworkAdapter() {
		throw new IllegalStateException("Utility Class");
	}

	/**
	 * Fetches a list of DeviceIDs of the Network Adapters that are active
	 * 
	 * @return a {@link java.util.List} of all the active Adapter DeviceIDs
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getIDWhere(String, String, String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getIDWhere(String, String, String, String)}
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
	public static List<String> getAdapterID()
			throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getIDWhere(classname, "NetEnabled", "true", "DeviceID");
	}

	/**
	 * Fetches the properties mentioned in the class description based on the
	 * DeviceID of the adapter passed onto it
	 *
	 * @param deviceID the network adapter ID fetched from
	 *                 {@link Win32_NetworkAdapter#getAdapterID()}
	 * @return a {@link java.util.Map} of the properties and their values mentioned
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
	public static Map<String, String> getNetworkAdapters(String deviceID)
			throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "deviceID", deviceID, attributes);
	}
}
