package org.softwarecave.springbootplayground.notification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Recordable {

    ModelType modelType() default ModelType.STICKY_NOTE;
    ActionType actionType();
}
