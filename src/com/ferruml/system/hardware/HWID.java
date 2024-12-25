package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	 * {@link com.ferrumx.formatter.wmic.WMIC#getIDWhere(String, String, String, String)}
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
		List<String> ideInterface = WMIC.getIDWhere("Win32_DiskDrive", "InterfaceType", "IDE", "SerialNumber");
		List<String> scsiInterface = WMIC.getIDWhere("Win32_DiskDrive", "InterfaceType", "SCSI", "SerialNumber");
		
		StringBuilder ideDrives = new StringBuilder("");
		StringBuilder scsiDrives = new StringBuilder("");
		
		for(String ide:ideInterface)
			ideDrives.append(ide);
		
		for(String scsi:scsiInterface)
			scsiDrives.append(scsi);
		
		return ideDrives.toString()+scsiDrives.toString();
	}
	
	/**
	 * Uses {@link java.util.concurrent.ExecutorService} to spawn four threads with
	 * each thread calling the
	 * {@link com.ferruml.formatter.wmic.WMIC#get(String, String)} directly or
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
		String cpuName = "";
		String cpuId = "";
		String motherBoardName = "";
		String driveId = "";
		
		try (ExecutorService EXEC = Executors.newFixedThreadPool(4);){
			Future<String> cpuNameTask = EXEC.submit(()-> WMIC.get("Win32_Processor", "Name").get("Name"));
			Future<String> cpuIdTask = EXEC.submit(()-> WMIC.get("Win32_Processor", "ProcessorId").get("ProcessorId"));
			Future<String> motherBoardNameTask = EXEC.submit(()-> WMIC.get("Win32_BaseBoard", "Product").get("Product"));
			Future<String> driveIdTask = EXEC.submit(HWID::getDiskSerials);
			
			cpuName = cpuNameTask.get();
			cpuId = cpuIdTask.get();
			motherBoardName = motherBoardNameTask.get();
			driveId = driveIdTask.get();
			}
		
		return cpuName+"/"+cpuId+"/"+motherBoardName+"/"+driveId;
	}
}

