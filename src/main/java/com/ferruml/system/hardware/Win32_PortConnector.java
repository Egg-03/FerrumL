package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
* This class queries the Win32_PortConnector class of WMI and represents the
* I/O Ports of your system's motherboard.
* <p>
* Fetches the following properties: Tag, ExternalReferenceDesignator,
* InternalReferenceDesignator
*
* @author Egg-03
*/
public class Win32_PortConnector {
	
	private static String classname = "Win32_PortConnector";
	private static String attributes = "Tag, ExternalReferenceDesignator, InternalReferenceDesignator";
	
	private Win32_PortConnector() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * Gathers a list of port IDs based on their Tag property
	 *
	 * @return a {@link java.util.List} of port IDs
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
	public static List<String> getBaseboardPortID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getID(classname, "Tag");
	}
	
	/**
	 * Gathers the port details based on the given port ID
	 *
	 * @param portID fetched from {@link Win32_PortConnector#getBaseboardPortID()}
	 * @return a {@link java.util.Map} of the properties and values mentioned in the
	 *         class description for the given port ID
	 * @throws IOException               re-throws the exception thrown by
	 *                                   {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
	 *                                   when there are I/O Errors during streaming
	 *                                   of data from and to Command Prompt and other
	 *                                   generated files
	 * @throws IndexOutOfBoundsException re-throws the exception thrown by
	 *                                  {@link com.ferruml.formatter.wmic.WMIC#getWhere(String, String, String, String)}
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
	public static Map<String, String> getBaseboardPorts(String portID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getWhere(classname, "Tag", portID, attributes);
	}
}
