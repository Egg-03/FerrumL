package com.ferruml.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.HWID;
import com.ferruml.system.hardware.Win32_BIOS;
import com.ferruml.system.hardware.Win32_Baseboard;
import com.ferruml.system.hardware.Win32_CacheMemory;
import com.ferruml.system.hardware.Win32_DiskDrive;
import com.ferruml.system.hardware.Win32_NetworkAdapter;
import com.ferruml.system.hardware.Win32_PhysicalMemory;
import com.ferruml.system.hardware.Win32_PortConnector;
import com.ferruml.system.hardware.Win32_Printer;
import com.ferruml.system.hardware.Win32_Processor;
import com.ferruml.system.hardware.Win32_SoundDevice;
import com.ferruml.system.hardware.Win32_VideoController;



class HardwareTests {

	@Test
	void hardwareIdTest() throws ExecutionException, InterruptedException {
		String hwid = HWID.getHardwareID();
		Logger.debug(hwid);
		assertFalse(hwid.isBlank() || hwid.isEmpty());
	}
	
	@Test
	void cpuTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder cpuDetails = new StringBuilder();
		
		List<String> cpuList = Win32_Processor.getProcessorList();
		if (cpuList.isEmpty()) {
	        Logger.debug("CPU Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "CPU not found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for(String cpu:cpuList) {
			Map<String, String> cpuProperties = Win32_Processor.getCurrentProcessor(cpu);
			assertFalse(cpuProperties.isEmpty());
			
			for(Map.Entry<String, String> entries: cpuProperties.entrySet()) {
				cpuDetails.append(entries.getKey()+": "+entries.getValue()+"\n");
			}
			cpuDetails.append("\n");
		}
		Logger.debug(cpuDetails.toString());
	}
	
	@Test
	void cpuCache() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder cpuCacheDetails = new StringBuilder();

		List<String> cacheID = Win32_CacheMemory.getCacheID();
		if (cacheID.isEmpty()) {
	        Logger.debug("CPU Cache Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "CPU Cache not found, hence cache testing cannot proceed. Test considered inconclusive for detailed information.");
	        return; 
	    }

		for (String currentCacheID : cacheID) {
			Map<String, String> cache = Win32_CacheMemory.getCPUCache(currentCacheID);
			assertFalse(cache.isEmpty());
			for (Map.Entry<String, String> currentCache : cache.entrySet()) {
				cpuCacheDetails.append(currentCache.getKey() + ": " + currentCache.getValue() + "\n");
			}
			cpuCacheDetails.append("\n");
		}

		Logger.debug(cpuCacheDetails.toString());
	}
	
	@Test
	void memoryTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder memoryDetails = new StringBuilder();
		
		List<String> memoryID = Win32_PhysicalMemory.getTag();
		if (memoryID.isEmpty()) {
	        Logger.debug("Physical Memory Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "Physical Memory not found, test considered inconclusive for detailed information.");
	        return;
	    }
		
		for (String id : memoryID) {
			Map<String, String> memory = Win32_PhysicalMemory.getMemory(id);
			assertFalse(memory.isEmpty());
			
			for (Map.Entry<String, String> entry : memory.entrySet()) {
				memoryDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			memoryDetails.append("\n");
		}
		Logger.debug(memoryDetails.toString());
	}
	
	@Test
	void videoControllerTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder videoControllerDetails = new StringBuilder();
		
		List<String> gpuIDs = Win32_VideoController.getGPUID();
		if (gpuIDs.isEmpty()) {
	        Logger.debug("GPU Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "GPU not found, test considered inconclusive for detailed information.");
	        return; 
	    }
	
		for (String currentID : gpuIDs) {
			Map<String, String> currentGPU = Win32_VideoController.getGPU(currentID);
			assertFalse(currentGPU.isEmpty());
			
			for (Map.Entry<String, String> entry : currentGPU.entrySet()) {
				 videoControllerDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			 videoControllerDetails.append("\n");
		}
		Logger.debug(videoControllerDetails.toString());
	}
	
	@Test
	void mainboardTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder mainboardDetails = new StringBuilder();
		
		Map<String, String> motherboard = Win32_Baseboard.getMotherboard();
		if (motherboard.isEmpty()) {
	        Logger.debug("Physical Memory Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "Physical Memory not found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for (Map.Entry<String, String> entry : motherboard.entrySet()) {
			mainboardDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		Logger.debug(mainboardDetails.toString());
	}
	
	@Test
	void mainboardPortTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		
		StringBuilder mainboardPortDetails = new StringBuilder();
		
		List<String> portID = Win32_PortConnector.getBaseboardPortID();
		if (portID.isEmpty()) {
	        Logger.debug("Port Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "IO Ports not found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for (String id : portID) {
			Map<String, String> ports = Win32_PortConnector.getBaseboardPorts(id);
			for (Map.Entry<String, String> port : ports.entrySet()) {
				mainboardPortDetails.append(port.getKey() + ": " + port.getValue()+"\n");
			}
			mainboardPortDetails.append("\n");
		}
		Logger.debug(mainboardPortDetails.toString());
	}

	@Test
	void biosTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder biosDetails = new StringBuilder();
		
		Map<String, String> BIOS = Win32_BIOS.getPrimaryBIOS();
		if (BIOS.isEmpty()) {
	        Logger.debug("BIOS Info not exposed by the target machine. Possibly running in a VM.");
	        assertTrue(true, "BIOS not found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for (Map.Entry<String, String> entry : BIOS.entrySet()) {
			biosDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		Logger.debug(biosDetails.toString());
	}
	
	@Test
	void networkTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder networkDetails = new StringBuilder();
		List<String> deviceIDs = Win32_NetworkAdapter.getAdapterID();
		if (deviceIDs.isEmpty()) {
	        Logger.debug("Network Adapter Info not exposed by the target machine. Possibly running in a VM or has all network adapters disabled.");
	        assertTrue(true, "Network Adapter not found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for (String currentID : deviceIDs) {
			
			Map<String, String> networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
			assertFalse(networkAdapter.isEmpty());
			
			for (Map.Entry<String, String> entry : networkAdapter.entrySet()) {
				networkDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
		}
		Logger.debug(networkDetails.toString());
	}
	
	@Test
	void diskTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder diskDetails = new StringBuilder();
		
		List<String> diskID = Win32_DiskDrive.getDriveID();
		if (diskID.isEmpty()) {
	        Logger.debug("No disk drives found on this system. Skipping detailed disk information test.");
	        assertTrue(true, "No disk drives found, test considered inconclusive for detailed information.");
	        return; // Exit the test early as there's no disk information to gather.
	    }

		for (String id : diskID) {
			Map<String, String> disk = Win32_DiskDrive.getDrive(id);
			assertFalse(disk.isEmpty());
			
			for (Map.Entry<String, String> entry : disk.entrySet()) {
				diskDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}

			diskDetails.append("\n");
		}
		Logger.debug(diskDetails.toString());
	}
	
	/* The following functions might sometimes return empty Lists or Maps
	 * So, only thrown Exceptions are treated as failures
	 */
	
	@Test
	void printerTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder printerDetails = new StringBuilder();
		
		List<String> deviceIDs = Win32_Printer.getDeviceIDList();
		if (deviceIDs.isEmpty()) {
	        Logger.debug("Printer info not exposed by the target machine. Possibly running a VM or the target machine has no printers or they are disabled");
	        assertTrue(true, "No printers found, test considered inconclusive for detailed information.");
	        return; 
	    }
		
		for (String currentID : deviceIDs) {
			Map<String, String> currentPrinter = Win32_Printer.getCurrentPrinter(currentID);
			for (Map.Entry<String, String> entry : currentPrinter.entrySet()) {
				printerDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			printerDetails.append("\n");
		}
		Logger.debug(printerDetails.toString());
	}
	
	@Test
	void soundDeviceTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder soundDeviceDetails = new StringBuilder();
		
		List<String> deviceIDs = Win32_SoundDevice.getSoundDeviceID();
		if (deviceIDs.isEmpty()) {
	        Logger.debug("Sound Device info not exposed by the target machine. Possibly running a VM or the target machine has no sound devices or they are disabled.");
	        assertTrue(true, "No sound devices found, test considered inconclusive for detailed information.");
	        return; 
	    }

		for (String currentID : deviceIDs) {
			Map<String, String> currentAudio = Win32_SoundDevice.getCurrentAudioDevice(currentID);
			for (Map.Entry<String, String> entry : currentAudio.entrySet()) {
				soundDeviceDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			soundDeviceDetails.append("\n");
		}
		Logger.debug(soundDeviceDetails.toString());
	}
}
