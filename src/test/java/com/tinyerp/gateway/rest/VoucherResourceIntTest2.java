package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.mapper.VoucherMapper;
import com.tinyerp.gateway.util.RestPaths;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("ALL")
public class VoucherResourceIntTest2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResourceIntTest2.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Voucher voucher;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createEntity() {
        return Voucher.newBuilder()
                .id(1L)
                .description("First voucher.")
                .build();
    }

    @Autowired
    private TokenStore tokenStore;

    private static final String TOKEN = "FOO";

    @Before
    public void setupAuth() {
        final OAuth2AccessToken token = new DefaultOAuth2AccessToken(TOKEN);
        final ClientDetails client = new BaseClientDetails("client", null, "read", "client_credentials", "ROLE_CLIENT");
        final OAuth2Authentication authentication = new OAuth2Authentication(
                new TokenRequest(null, "client", null, "client_credentials").createOAuth2Request(client), null);
        tokenStore.storeAccessToken(token, authentication);
    }

    @Before
    public void cleanUp() {
        for (ProcessInstance instance : runtimeService.createProcessInstanceQuery().list()) {
            runtimeService.deleteProcessInstance(instance.getId(), "Reset Processes");
        }
    }

    @Before
    public void init() {
        voucher = createEntity();
    }

    @Test
    public void testGivenPathVouchersWhenGettingForEntityThenStatusCodeIsOk() {

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Given Path Vouchers
        final UriComponentsBuilder uri = UriComponentsBuilder.fromPath(RestPaths.API_VERSION_1_VOUCHER);

        // When Getting For Entity
        final ResponseEntity<String> response = testRestTemplate.exchange(uri.build().toUri(), HttpMethod.GET,
                new HttpEntity<>(headers), String.class);

        // Then Status Code Is Ok
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}