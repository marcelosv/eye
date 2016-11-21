package br.com.eye.monitor.process;

import br.com.eye.monitor.data.SoftwareBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class DataSaveProcessNumbersOfErrorImpl implements DataSaveProcessNumbersOfError {

    @Autowired
    private RedisOperations<String, Object> redis;

    @Autowired
    private DbSystensProcess dbSystensProcess;

    @Override
    public Map<String, String> getNumbersOfError() {
        Map<String, String> retorno = new TreeMap<String, String>();

        Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();

        if (softwares == null) {
            return retorno;
        }

        for (Map.Entry<String, SoftwareBean> entry : softwares.entrySet()) {
            retorno.put(entry.getKey(), entry.getValue().getNroErros());
        }

        return retorno;
    }

}
