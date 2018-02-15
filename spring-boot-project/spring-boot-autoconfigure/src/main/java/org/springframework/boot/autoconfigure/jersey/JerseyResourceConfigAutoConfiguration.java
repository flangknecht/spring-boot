/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
