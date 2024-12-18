package com.ferruml.manualtests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ferruml.exceptions.ShellException;
import com.ferruml.system.hardware.Win32_CacheMemory;

public class Cache {
	public static void main(String[] args)
			throws ShellException, InterruptedException, IndexOutOfBoundsException, IOException {
		List<String> cacheIDs = null;
		Map<String, String> cache = null;

		cacheIDs = Win32_CacheMemory.getCacheID();
		System.out.println(cacheIDs);

		for (String currentCacheID : cacheIDs) {
			cache = Win32_CacheMemory.getCPUCache(currentCacheID);
			for (Map.Entry<String, String> entry : cache.entrySet())
				System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println();

		}
	}
}
