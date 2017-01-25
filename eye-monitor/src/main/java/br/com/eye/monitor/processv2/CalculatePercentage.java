package br.com.eye.monitor.processv2;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class CalculatePercentage {

	public double calculate(int total, int valor) {
        BigDecimal bdTotal = new BigDecimal(total);
        BigDecimal bdValor = new BigDecimal(valor);

        BigDecimal bdCalc1 = bdValor.multiply(BigDecimal.valueOf(100));

        return bdCalc1.divide(bdTotal, 2, RoundingMode.HALF_UP).doubleValue();
    }
	
}
