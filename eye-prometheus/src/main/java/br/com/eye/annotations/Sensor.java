package br.com.eye.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.eye.data.TypesData;

/**
 *
 * Annotation of eYe to mark which method is going to be monitored.
 *
 * @author Marcelo de Souza Vieira
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sensor {
	public String description() default "";
	public String[] tags() default "";
	public TypesData type() default TypesData.UNINFORMED;
}
