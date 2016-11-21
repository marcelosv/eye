package br.com.eye.monitor.process;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataSaveProcessAbstract {

    protected double calcPorcentagem(int total, int valor) {
        BigDecimal bdTotal = new BigDecimal(total);
        BigDecimal bdValor = new BigDecimal(valor);

        BigDecimal bdCalc1 = bdValor.multiply(BigDecimal.valueOf(100));

        return bdCalc1.divide(bdTotal, 2, RoundingMode.HALF_UP).doubleValue();
    }

}
