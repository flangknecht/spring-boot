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


import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JerseyResourceConfigAutoConfiguration} to verify correct registration
 * and operability of the registered JAX-RS resource.
 *
 * @author Florian Langknecht
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class JerseyAutoConfigurationResourceRegistrationTests {

	public static final String TEST_STRING = "World";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ResourceConfig resourceConfig;

	@Test
	public void contextLoads() {
		final ResponseEntity<String> entity = this.restTemplate.getForEntity("/hello", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Import({
		ServletWebServerFactoryAutoConfiguration.class,
		JerseyResourceConfigAutoConfiguration.class,
		JerseyAutoConfiguration.class,
		PropertyPlaceholderAutoConfiguration.class })
	@Configuration
	@Path("/hello")
	public static class Application {

		@GET
		public String testResource() {
			return TEST_STRING;
		}

	}
}
