package org.springframework.boot.autoconfigure.jersey;

import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for JAX-RS resource registrations in the context of Jersey.
 *
 * @author Florian Langknecht
 */
@Configuration
@ConditionalOnClass(name = { "org.glassfish.jersey.server.spring.SpringComponentProvider",
	"javax.servlet.ServletRegistration" })
@ConditionalOnMissingBean(type = "org.glassfish.jersey.server.ResourceConfig")
@AutoConfigureBefore(JerseyAutoConfiguration.class)
public class JerseyResourceConfigAutoConfiguration extends ResourceConfig {

	public JerseyResourceConfigAutoConfiguration(ConfigurableApplicationContext context) {
		for (String jaxRsResourceBeanName : context.getBeanNamesForAnnotation(Path.class)) {
			register(context.getType(jaxRsResourceBeanName));
		}
	}
}
