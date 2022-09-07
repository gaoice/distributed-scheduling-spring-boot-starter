package com.gaoice.distributed.scheduling.annotation;

import java.lang.annotation.*;

/**
 * @author gaoice
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonDistributed {
}
