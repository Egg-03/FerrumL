package com.ferruml.examples;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ferruml.system.hardware.Win32_CacheMemory;

public class Cache {
	public static void main(String[] args) {
		List<String> cacheIDs = null;
		Map<String, String> cache = null;
		
		try {
			cacheIDs = Win32_CacheMemory.getCacheID();
			System.out.println(cacheIDs);
		
			for(String currentCacheID : cacheIDs) {
				cache = Win32_CacheMemory.getCPUCache(currentCacheID);
				for(Map.Entry<String, String> entry: cache.entrySet())
					System.out.println(entry.getKey()+": "+entry.getValue());
				System.out.println();
			}
		}catch(IOException | IndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			
			cache = Collections.emptyMap();
			for(Map.Entry<String, String> entry: cache.entrySet())
				System.out.println(entry.getKey()+": "+entry.getValue());
			System.out.println();
		}
	}
}
