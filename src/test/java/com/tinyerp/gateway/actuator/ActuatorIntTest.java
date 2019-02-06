package com.tinyerp.gateway.actuator;

import com.tinyerp.gateway.util.RestPaths;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reference: https://github.com/spring-projects/spring-boot/blob/2.1.x/spring-boot-samples/spring-boot-sample-actuator/src/test/java/sample/actuator/SampleActuatorApplicationTests.java
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActuatorIntTest {

    // Remember: TestRestTemplate is only auto-configured when @SpringBootTest has been configured with a webEnvironment that
    // means it starts the web container and listens for HTTP requests.
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void health() {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(RestPaths.ACTUATOR_HEALTH, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("\"status\":\"UP\"");
        assertThat(entity.getBody()).doesNotContain("\"hello\":\"1\"");
    }

    @Test
    public void info() {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(RestPaths.ACTUATOR_INFO, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
