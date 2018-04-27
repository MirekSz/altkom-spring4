package pl.altkom.shop.www;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import net.bull.javamelody.MonitoringSpringAdvisor;
import pl.altkom.shop.lib.Profiles;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Profile(Profiles.WEB)
@EnableAspectJAutoProxy
@ImportResource("classpath:net/bull/javamelody/monitoring-spring-aspectj.xml")
public class JavaMelodyConfiguration {
	@Bean
	public MonitoringSpringAdvisor springRepoMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(createAnnotationMatchingPointcut(Repository.class));
		return interceptor;
	}

	@Bean
	public MonitoringSpringAdvisor springServiceMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(createAnnotationMatchingPointcut(Service.class));
		return interceptor;
	}

	@Bean
	public MonitoringSpringAdvisor springControllerMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(createAnnotationMatchingPointcut(Controller.class));
		return interceptor;
	}

	@Bean
	public MonitoringSpringAdvisor springRestControllerMonitoringAdvisor() {
		final MonitoringSpringAdvisor interceptor = new MonitoringSpringAdvisor();
		interceptor.setPointcut(createAnnotationMatchingPointcut(RestController.class));
		return interceptor;
	}

	private AnnotationMatchingPointcut createAnnotationMatchingPointcut(Class annotation) {
		return new AnnotationMatchingPointcut(annotation) {
			@Override
			public ClassFilter getClassFilter() {
				AnnotationClassFilter annotationClassFilter = new AnnotationClassFilter(annotation) {
					@Override
					public boolean matches(Class<?> clazz) {
						if (!clazz.getName().startsWith("pl")) {
							return false;
						}
						return super.matches(clazz);
					}
				};
				return annotationClassFilter;
			}
		};
	}
}
