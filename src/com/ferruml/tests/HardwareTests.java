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
		assertFalse(cpuList.isEmpty());
		
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
		assertFalse(cacheID.isEmpty());

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
		assertFalse(memoryID.isEmpty());
		
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
		assertFalse(gpuIDs.isEmpty());
	
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
		assertFalse(motherboard.isEmpty());
		for (Map.Entry<String, String> entry : motherboard.entrySet()) {
			mainboardDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		Logger.debug(mainboardDetails.toString());
	}
	
	@Test
	void mainboardPortTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		
		StringBuilder mainboardPortDetails = new StringBuilder();
		
		List<String> portID = Win32_PortConnector.getBaseboardPortID();
		assertFalse(portID.isEmpty());
		
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
		assertFalse(BIOS.isEmpty());
		
		for (Map.Entry<String, String> entry : BIOS.entrySet()) {
			biosDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		Logger.debug(biosDetails.toString());
	}
	
	@Test
	void networkTest() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder networkDetails = new StringBuilder();
		List<String> deviceIDs = Win32_NetworkAdapter.getAdapterID();
		assertFalse(deviceIDs.isEmpty());
		
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
		assertFalse(diskID.isEmpty());

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
