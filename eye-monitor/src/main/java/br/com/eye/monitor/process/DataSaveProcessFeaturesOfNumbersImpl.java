package br.com.eye.monitor.process;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import br.com.eye.monitor.data.SoftwareBean;

@Component
public class DataSaveProcessFeaturesOfNumbersImpl implements DataSaveProcessFeaturesOfNumbers {

    @Autowired
    private RedisOperations<String, Object> redis;

    @Autowired
    private DbSystensProcess dbSystensProcess;

    @Override
    public Map<String, SoftwareBean> getFeaturesAndNo() {

        Map<String, SoftwareBean> softwares = dbSystensProcess.getDB();

/*        if (softwares == null) {
            return retorno;
        }

        for (Map.Entry<String, SoftwareBean> entry : softwares.entrySet()) {
            retorno.put(entry.getKey(), entry.getValue());
        }*/

        return softwares;

    }

}
