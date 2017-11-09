package cc.foxtail.peripheral.annotations;

import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:myis1000@gmail.com">guan xiangHuan</a>
 * @version 0.0.1 20171109
 * @since JDK8.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Source {
    Class<?> source() default OutputStream.class;
}
