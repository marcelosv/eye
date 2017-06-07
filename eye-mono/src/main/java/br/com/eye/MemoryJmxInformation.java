package br.com.eye;

import java.lang.management.ManagementFactory;

public class MemoryJmxInformation {

	public static Long[] getMemory() {
		Runtime runtime = Runtime.getRuntime();
		
		return new Long[] {
				runtime.totalMemory() + getTotalNonHeapMemoryIfPossible(),
				runtime.freeMemory()
		};
	}

	public static long getTotalNonHeapMemoryIfPossible() {
		try {
			return ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage()
					.getUsed();
		} catch (Throwable ex) {
			return 0;
		}
	}

	public static Long newMemoryMetric(long bytes) {
		if (bytes != 0) {
			return bytes / 1024;
		}
		return bytes;
	}

}
