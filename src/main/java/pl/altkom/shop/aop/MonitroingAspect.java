package pl.altkom.shop.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MonitroingAspect {
	Logger log = Logger.getLogger(MonitroingAspect.class);

	@Pointcut("@within(org.springframework.stereotype.Service)")
	public void allServices() {
	}

	@Pointcut("@within(org.springframework.stereotype.Repository)")
	public void allRepos() {
	}

	@Around("@annotation(pl.altkom.shop.aop.Monitoring)")
	public Object monitpr(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method m = ms.getMethod();

		Object target = pjp.getTarget();
		Method declaredMethod = target.getClass().getDeclaredMethod(m.getName());

		long currentTimeMillis = System.currentTimeMillis();
		Object obj = pjp.proceed();
		long end = System.currentTimeMillis() - currentTimeMillis;

		Monitoring annotation = declaredMethod.getAnnotation(Monitoring.class);
		if (annotation != null && end > annotation.maxTime()) {
			log.error(m + " took: " + end);
		} else {
			log.info(m + " took: " + end);
		}
		return obj;
	}

}
