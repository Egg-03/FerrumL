package com.ferruml.system.hardware;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ferruml.formatter.wmic.WMIC;
import com.ferruml.system.currentuser.User;

public class HWID {
	private HWID() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static String getHardwareID() throws ExecutionException, InterruptedException {
		
		String cpuName = "";
		String cpuId = "";
		String motherBoardName = "";
		String deviceName = "";
		String userName = "";
		int countRAM = -1;
		int countStorage = -1;
		
		ExecutorService EXEC = null;
		
		try {
			EXEC = Executors.newFixedThreadPool(7);
			Future<String> cpuNameTask = EXEC.submit(()-> WMIC.get("Win32_Processor", "Name").get("Name"));
			Future<String> cpuIdTask = EXEC.submit(()-> WMIC.get("Win32_Processor", "ProcessorId").get("ProcessorId"));
			Future<String> motherBoardNameTask = EXEC.submit(()-> WMIC.get("Win32_BaseBoard", "Product").get("Product"));
			Future<String> deviceNameTask = EXEC.submit(()->WMIC.get("Win32_OperatingSystem", "CSName").get("CSName"));
			Future<String> userNameTask = EXEC.submit(User::getUsername);
			Future<Integer> countRAMTask = EXEC.submit(()-> Win32_PhysicalMemory.getTag().size());
			Future<Integer> countStorageTask = EXEC.submit(()-> Win32_DiskDrive.getDriveID().size());
			
			cpuName = cpuNameTask.get();
			cpuId = cpuIdTask.get();
			motherBoardName = motherBoardNameTask.get();
			deviceName = deviceNameTask.get();
			userName = userNameTask.get();
			countRAM = countRAMTask.get();
			countStorage = countStorageTask.get();
		} catch(NullPointerException w){
			System.err.println(w.getMessage());
		}finally {
			EXEC.shutdown();
		}
		
		return userName+"/"+deviceName+"/"+cpuName+"/"+cpuId+"/"+motherBoardName+"/"+countRAM+"/"+countStorage;
	}
}

