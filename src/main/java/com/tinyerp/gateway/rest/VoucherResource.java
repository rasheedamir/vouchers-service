package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.config.security.ActivitiSecurityUtil;
import com.tinyerp.gateway.json.ApiVoucher;
import com.tinyerp.gateway.service.VoucherService;
import com.tinyerp.gateway.util.HeaderUtil;

import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static com.tinyerp.gateway.util.Consts.API_VERSION_1;
import static com.tinyerp.gateway.util.Consts.API_VERSION_1_VOUCHER;
import static com.tinyerp.gateway.util.Consts.VOUCHER;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping(API_VERSION_1)
public class VoucherResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResource.class);

    private final VoucherService voucherService;
    private final ActivitiSecurityUtil activitiSecurityUtil;

    @Autowired
    public VoucherResource(VoucherService voucherService, ActivitiSecurityUtil activitiSecurityUtil) {
        this.voucherService = voucherService;
        this.activitiSecurityUtil = activitiSecurityUtil;
    }

    // TODO: OAuth2Authentication is null whereas it shouldn't be!
    @PostMapping(VOUCHER)
    @Timed
    public ResponseEntity<ApiVoucher> createVoucher(@Valid @RequestBody ApiVoucher voucher, OAuth2Authentication oAuth2Authentication) throws URISyntaxException {
        // TODO: this must be handled somewhere else more globally! as this creates the link
        activitiSecurityUtil.logInAs("system");
        LOGGER.debug("REST request to save Voucher : {}", voucher);
        LOGGER.debug("REST request to save Voucher sent By: {}", oAuth2Authentication);
        ApiVoucher result = voucherService.save(voucher);
        LOGGER.debug("REST response for save Voucher : {}", result);
        return ResponseEntity.created(new URI(API_VERSION_1_VOUCHER + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

}
