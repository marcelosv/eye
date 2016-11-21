package br.com.eye.monitor.process;

import br.com.eye.monitor.data.SoftwareBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataSaveProcessLoadSoftwareImpl implements DataSaveProcessLoadSoftware {

    @Autowired
    private RedisOperations<String, Object> redis;

    @Autowired
    private DbSystensProcess dbSystensProcess;

    @Override
    public String[] getSoftware() {
        Map<String, SoftwareBean> software = dbSystensProcess.getDB();

        if (software == null) {
            return new String[]{};
        }

        String[] itens = new String[software.size()];

        int cont = 0;
        for (String sis : software.keySet()) {
            itens[cont++] = sis;
        }

        return itens;
    }

}
