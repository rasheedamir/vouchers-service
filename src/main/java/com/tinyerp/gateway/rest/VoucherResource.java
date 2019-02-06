package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.config.security.ActivitiSecurityUtil;
import com.tinyerp.gateway.json.request.ApiVoucherRequest;
import com.tinyerp.gateway.json.response.ApiVoucherResponse;
import com.tinyerp.gateway.service.VoucherService;
import com.tinyerp.gateway.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import static com.tinyerp.gateway.util.RestPaths.API_VERSION_1;
import static com.tinyerp.gateway.util.RestPaths.API_VERSION_1_VOUCHER;
import static com.tinyerp.gateway.util.RestPaths.VOUCHER;

@RestController
@RequestMapping(API_VERSION_1)
public class VoucherResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResource.class);

    private final VoucherService voucherService;
    private final ActivitiSecurityUtil activitiSecurityUtil;

    private final String ENTITY_NAME = "voucher";

    @Autowired
    public VoucherResource(VoucherService voucherService, ActivitiSecurityUtil activitiSecurityUtil) {
        this.voucherService = voucherService;
        this.activitiSecurityUtil = activitiSecurityUtil;
    }

    @PostMapping(VOUCHER)
    @Timed
    public ResponseEntity<ApiVoucherResponse> createVoucher(@Valid @RequestBody ApiVoucherRequest apiVoucherRequest,
                                                            OAuth2Authentication oAuth2Authentication,
                                                            @AuthenticationPrincipal Principal principal) throws URISyntaxException {
        // TODO: this must be handled somewhere else more globally! as this creates the link between spring security and activiti
        activitiSecurityUtil.logInAs("system");
        LOGGER.debug("REST request to save Voucher : {}", apiVoucherRequest);
        LOGGER.debug("REST request to save Voucher sent By: {}", oAuth2Authentication);
        ApiVoucherResponse result = voucherService.save(apiVoucherRequest);
        LOGGER.debug("REST response for save Voucher : {}", result);
        return ResponseEntity.created(new URI(API_VERSION_1_VOUCHER + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
