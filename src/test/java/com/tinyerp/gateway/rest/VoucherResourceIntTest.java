package com.tinyerp.gateway.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tinyerp.gateway.config.security.GatewayKeycloakProperties;
import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.json.request.ApiVoucherRequest;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import com.tinyerp.gateway.mapper.VoucherMapper;
import com.tinyerp.gateway.util.RestPaths;
import com.tinyerp.gateway.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
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
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoucherResourceIntTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResourceIntTest.class);

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private GatewayKeycloakProperties gatewayKeycloakProperties;

    // TestRestTemplate is only a wrapper of RestTemplate which offers you convenient approach, it doesn't throw exception, but wrap it with json response, such behavior should be implemented by yourself in the real application, but you may doesn't care in the test.
    @Autowired
    private TestRestTemplate restTemplate;

    private Voucher voucher;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createEntity() {
        return Voucher.newBuilder()
                .id(VoucherId.generate())
                .description(new VoucherDescription("Test voucher"))
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
    public void init() {
        voucher = createEntity();
    }

    @Test
    public void givenProcess_whenStartProcess_thenIncreaseInProcessInstanceCount() throws Exception {

        // Get access token for local secretary banquet
        String token = obtainAccessToken("ls-banquet", "password");
        printAccessToken(token);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the Voucher
        ApiVoucherRequest apiVoucherRequest = ApiVoucherRequest.newBuilder().description(voucher.getDescription()).build();

        // Given Path Vouchers
        final UriComponentsBuilder uri = UriComponentsBuilder.fromPath(RestPaths.API_VERSION_1_VOUCHER);

        // When Posting For Entity
        final ResponseEntity<ApiVoucherResponse> response = restTemplate.exchange(uri.build().toUri(), HttpMethod.POST,
                new HttpEntity<>(apiVoucherRequest, headers), ApiVoucherResponse.class);

        // Then Status Code Is Ok
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        // Post the Voucher
        /*this.mockMvc.perform(post(API_VERSION_1_VOUCHER)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .content(TestUtil.convertObjectToJsonBytes(apiVoucherRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();*/
    }

    /**
     * Obtains a real access token from KeyCloak!
     */
    private String obtainAccessToken(String username, String password) throws IOException {

        String url = String.format("%s/realms/%s/protocol/openid-connect/token", gatewayKeycloakProperties.getServerUrl(), gatewayKeycloakProperties.getRealm());
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add(OAuth2Utils.GRANT_TYPE, TestUtil.GRANT_TYPE_PASSWORD);
        params.add(OAuth2Utils.CLIENT_ID, gatewayKeycloakProperties.getClientId());
        params.add(TestUtil.USERNAME, username);
        params.add(TestUtil.PASSWORD, password);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);

        String body = response.getBody();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(body);
        LOGGER.info("Response {}", json);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(body).get(TestUtil.ACCESS_TOKEN).toString();
    }


    private void printAccessToken(String accessToken) throws JWTDecodeException {
        DecodedJWT jwt = JWT.decode(accessToken);
        LOGGER.info("JWT Issuer {}", jwt.getIssuer());
        LOGGER.info("JWT Audience {}", jwt.getAudience());
        // Other's can be printed as well! look into jwt.getClaims() which has everything!
    }
}