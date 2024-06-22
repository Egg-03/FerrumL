package com.ferruml.system.hardware;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ferruml.formatter.wmic.WMIC;

public class HWID {
	protected HWID() {
		throw new IllegalStateException("Utility Class");
	}
	
	private static String getDiskSerials() throws IndexOutOfBoundsException, IOException {
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

