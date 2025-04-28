package com.ferruml.system.hardware;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.ferruml.exceptions.ShellException;
import com.ferruml.formatter.wmic.WMIC;

/**
 * Hardware ID generation class based on the following format :
 * "CPUName/CPUID/MotherboardName/DriveIDs"
 *
 */
public class HWID {
	
	private HWID() {
		throw new IllegalStateException("Utility Class");
	}
	
	/**
	 * Uses
	 * {@link com.ferrumx.formatter.wmic.WMIC#getValueWhere(String, String, String, String)}
	 * to fetch IDE and SCSI Interface Type Disk IDs
	 *
	 * @return a concatenated list of all IDE and SCSI drive IDs currently installed
	 * @throws IndexOutOfBoundsException if there is a parsing error incurred during
	 *                                   extracting the IDs
	 * @throws IOException               in case of any IOException thrown by the
	 *                                   underlying parser
	 * @throws ShellException            if any internal command used in the
	 *                                   Command Prompt throws errors
	 * @throws InterruptedException      if the thread waiting for the process to
	 *                                   exit, gets interrupted. When catching this
	 *                                   exception, you may re-throw it's
	 *                                   interrupted status by using
	 *                                   Thread.currentThread().interrupt();
	 */
	private static String getDiskSerials() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		// get IDE interface drive ids first
		List<String> allDiskDriveIds = WMIC.getValueWhere("Win32_DiskDrive", "InterfaceType", "IDE", "SerialNumber");
		// add SCSI interface drive ids to the list
		allDiskDriveIds.addAll(WMIC.getValueWhere("Win32_DiskDrive", "InterfaceType", "SCSI", "SerialNumber"));
		
		return StringUtils.join(allDiskDriveIds, null);
	}
	
	/**
	 * Uses {@link java.util.concurrent.ExecutorService} to spawn threads as required with
	 * each thread calling the
	 * {@link com.ferruml.formatter.wmic.WMIC#getPropertiesAndTheirValues(String, String)} directly or
	 * through the Win32 Classes to get specific parts of HWID which is then
	 * ultimately combined to form the final ID
	 *
	 * @return the HWID of type {@link java.lang.String} in the format shown in the
	 *         class description
	 * @throws ExecutionException   when the underlying functions defined in
	 *                              {@link java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)}
	 *                              throw an exception
	 * @throws InterruptedException when any of the threads get interrupted
	 */
	public static String getHardwareID() throws ExecutionException, InterruptedException {
		
		List<String> id = new ArrayList<>();
		
		try (ExecutorService EXEC = Executors.newFixedThreadPool(4);){
			
			Future<String> cpuIdTask = EXEC.submit(()-> StringUtils.join(WMIC.getValue("Win32_Processor", "ProcessorID"), null));
			Future<String> motherboardIdTask = EXEC.submit(()-> StringUtils.join(WMIC.getValue("Win32_Baseboard", "SerialNumber"), null));
			Future<String> driveIdTask = EXEC.submit(HWID::getDiskSerials);
			
			id.add(cpuIdTask.get());
			id.add(motherboardIdTask.get());
			id.add(driveIdTask.get());
			
			id.removeIf(s -> s == null || StringUtils.isBlank(s));
			
			}
		
		return DigestUtils.sha256Hex(StringUtils.join(id, null).getBytes(StandardCharsets.UTF_8)).toUpperCase();
	}
}

