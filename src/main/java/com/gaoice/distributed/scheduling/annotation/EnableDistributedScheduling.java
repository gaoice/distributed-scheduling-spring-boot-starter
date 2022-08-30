package com.gaoice.distributed.scheduling.annotation;

import com.gaoice.distributed.scheduling.DistributedSchedulingAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author gaoice
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DistributedSchedulingAutoConfigure.class)
public @interface EnableDistributedScheduling {
}
