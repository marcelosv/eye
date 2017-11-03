package br.com.eye.messaing.annotations;

import br.com.eye.messaing.config.EyeConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EyeConfig.class)
public @interface EnableEye {
}
