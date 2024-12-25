package com.ferruml.system.operatingsystem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class queries the Win32_OperatingSystem class of WMI and fetches the
 * current OS details.
 * <p>
 * The following properties are fetched: Caption, InstallDate, CSName,
 * LastBootUpTime, LocalDateTime, Distributed, NumberOfUsers, Version,
 * BootDevice, BuildNumber, BuildType, Manufacturer, OSArchitecture,
 * MUILanguages, PortableOperatingSystem, Primary, RegisteredUser, SerialNumber,
 * ServicePackMajorVersion, ServicePackMinorVersion, SystemDirectory,
 * SystemDrive, WindowsDirectory
 *
 * @author Egg-03
 */
public class Win32_OperatingSystem {
	
	private static String classname = "Win32_OperatingSystem";
	private static String attributes = "Caption, InstallDate, CSName, LastBootUpTime, LocalDateTime, Distributed, NumberOfUsers, Version, BootDevice, BuildNumber, BuildType, Manufacturer, OSArchitecture, MUILanguages, PortableOperatingSystem, Primary, RegisteredUser, SerialNumber, ServicePackMajorVersion, ServicePackMinorVersion, SystemDirectory, SystemDrive, WindowsDirectory";
	
	private Win32_OperatingSystem(){
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * Fetches a list of OS Names installed in the System. In case of Multi-Boot
	 * systems, more than one OS names may appear
	 *
	 * @return a {@link java.util.List} of OS Names found in the system
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
	public static List<String> getOSList() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getID(classname, "Caption");
		}
	
	/**
	 * Fetches a OS properties for a particular OS
	 *
	 * @param OSName the iterative List fetched from {@link #getOSList()}
	 * @return a {@link java.util.Map} of OS properties and their values mentioned
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
	public static Map<String, String> getOSInfo(String OSName) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getWhere(classname, "Caption", OSName, attributes);
	}
}