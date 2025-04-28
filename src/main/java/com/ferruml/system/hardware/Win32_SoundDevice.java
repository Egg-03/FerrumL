package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * This class queries the Win32_SoundDevice class of WMI and represents the
 * Audio Driver details of your system.
 * <p>
 * Fetches the following properties: ProductName, Status, Caption, PNPDeviceID,
 * Manufacturer
 *
 * @author Egg-03
 */
public class Win32_SoundDevice {
	
	private static String classname = "Win32_SoundDevice";
	private static String attributes = "ProductName, Status, Caption, PNPDeviceID, Manufacturer";
	
	private Win32_SoundDevice() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * This method fetches a list of Audio Device Captions found in the system
	 *
	 * @return a {@link java.util.List} of Audio Device IDs
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
	public static List<String> getSoundDeviceID() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getValue(classname, "Caption"); //fallback to Caption since DeviceID does not work
	}
	
	/**
	 * This method fetches the Audio Device Driver details based on the Audio Device Captions
	 *
	 * @param deviceID fetched from {@link Win32_SoundDevice#getSoundDeviceID()}
	 * @return a {@link java.util.Map} of Audio Driver/Device properties mentioned
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
	public static Map<String, String> getCurrentAudioDevice(String deviceID) throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		return WMIC.getPropertiesAndTheirValuesWhere(classname, "Caption", deviceID, attributes);
	}
}
