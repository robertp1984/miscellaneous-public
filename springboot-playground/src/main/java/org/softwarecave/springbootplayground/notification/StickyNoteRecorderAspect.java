package org.softwarecave.springbootplayground.notification;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class StickyNoteRecorderAspect {


    @AfterReturning(pointcut = "@annotation(rec)",
            returning = "returnValue")
    public void addStickyNote(JoinPoint joinPoint, Recordable rec, Object returnValue) {
        if (rec.modelType() == ModelType.STICKY_NOTE) {
            if (rec.actionType() == ActionType.DELETE) {
                log.info("Deleted StickyNote with ID " + joinPoint.getArgs()[0]);
            } else if (rec.actionType() == ActionType.ADD) {
                log.info("Added StickyNote " + returnValue);
            } else if (rec.actionType() == ActionType.UPDATE) {
                log.info("Updated StickyNote " + returnValue);
            }
        }
    }

}
