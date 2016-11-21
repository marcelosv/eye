package br.com.eye.monitor.process;

import br.com.eye.monitor.data.VersionsTotal;

import java.util.Map;

public interface DataSaveProcessVersionsAndTotal {
    Map<String, VersionsTotal> getVersionsAndTotal();
}
