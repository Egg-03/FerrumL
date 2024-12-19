package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class contains methods that query Win32_DiskDrive class of WMI to fetch
 * Disk Information.
 * <p>
 * The class fetches the values of the following properties: Caption, Model,
 * Size, FirmwareRevision, SerialNumber, Partitions, Status, InterfaceType,
 * PNPDeviceID
 *
 * @author Egg-03
 * @version 1.3.1
 */
public class Win32_DiskDrive {
	
	private static String classname = "Win32_DiskDrive";
	private static String attributes = "Caption, Model, Size, FirmwareRevision, SerialNumber, Partitions, Status, InterfaceType, PNPDeviceID";
	
	private Win32_DiskDrive() {
		throw new IllegalStateException("Utility Class");
	}
	
	
	/**
	 * Fetches a list of Disk Drives based on their Caption property [see the warning below]. This method
	 * internally calls {@link com.ferruml.formatter.wmic.WMIC#getID(String, String)}
	 * and passes the class_name and the attributes mentioned in the class
	 * description as parameters
	 *
	 * @return a {@link java.util.List} of disk drive IDs
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
	 *                                   <p>
	 * WARNING - Caption, instead of DeviceID or PNPDeviceID has been chosen as an identifier
	 * This is limiting because Captions can be same for two drives of the same model
	 * I couldn't get the real IDs to work with WMIC and I don't know how LinkedHashMap will react to duplicates
	 * This can potentially cause only one drive info to be repeated
	 */
	public static List<String> getDriveID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getID(classname, "Index"); //DeviceID does not work, but Index does
	}
	
	/**
	 * Fetches the Disk Details whose IDs were fetched using the
	 * {@link Win32_DiskDrive#getDriveID()} method
	 *
	 * @param driveID this parameter takes one Drive ID at a time from the drive ID
	 *                list
	 * @return a {@link java.util.Map} of Drive Details which contain the attributes
	 *         mentioned in the class description, as a key-value pair
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
	public static Map<String, String> getDrive(String driveID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException{
		return WMIC.getWhere(classname, "Index", driveID, attributes);
	}
}
