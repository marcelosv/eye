package br.com.eye.monitor.process;

import br.com.eye.monitor.data.CountVersion;
import br.com.eye.monitor.data.SoftwareBean;
import br.com.eye.monitor.data.VersionsTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DataSaveProcessVersionsAndTotalImpl extends DataSaveProcessAbstract implements DataSaveProcessVersionsAndTotal{

    @Autowired
    private RedisOperations<String, Object> redis;

    @Autowired
    private DbSystensProcess dbSystensProcess;

    @Override
    public Map<String, VersionsTotal> getVersionsAndTotal() {

        Map<String, VersionsTotal> versoes = new TreeMap<String, VersionsTotal>();

        Map<String, SoftwareBean> Software = dbSystensProcess.getDB();

        if (Software == null) {
            return versoes;
        }

        for (Map.Entry<String, SoftwareBean> entry : Software.entrySet()) {

            if (!versoes.containsKey(entry.getKey())) {
                versoes.put(entry.getKey(), new VersionsTotal());
            }

            int total = 0;
            for (String entryVersao : entry.getValue().getVersoes().keySet()) {

                Map<String, CountVersion> itemVersaoCont = entry.getValue()
                        .getVersoes().get(entryVersao);
                for (String versao : itemVersaoCont.keySet()) {

                    total = total + itemVersaoCont.get(versao).getTotal();

                    if (!versoes.get(entry.getKey()).getLista()
                            .containsKey(entryVersao)) {
                        versoes.get(entry.getKey()).getLista()
                                .put(entryVersao, new CountVersion());
                    }
                    versoes.get(entry.getKey())
                            .getLista()
                            .get(entryVersao)
                            .setTotal(
                                    versoes.get(entry.getKey()).getLista()
                                            .get(entryVersao).getTotal()
                                            + itemVersaoCont.get(versao)
                                            .getTotal());
                }

            }
            versoes.get(entry.getKey()).setTotal(total);

        }

        for (String chave : versoes.keySet()) {
            VersionsTotal versoesTotais = versoes.get(chave);
            for (String chaveVerTotais : versoesTotais.getLista().keySet()) {
                CountVersion cv = versoesTotais.getLista().get(chaveVerTotais);
                double perc = calcPorcentagem(versoesTotais.getTotal(),
                        cv.getTotal());

                BigDecimal bd = new BigDecimal(perc);
                bd = bd.setScale(2, RoundingMode.HALF_UP);

                cv.setPerc(bd.toString());
            }
        }

        return versoes;
    }

}
