package top.huangguaniu.youcan.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hyx on 17-10-25.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    int layout() default 0;
}
