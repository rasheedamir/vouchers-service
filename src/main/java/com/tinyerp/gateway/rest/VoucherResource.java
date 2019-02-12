package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.domain.VoucherEvent;
import com.tinyerp.gateway.domain.VoucherId;
import com.tinyerp.gateway.json.request.ApiClaimVoucher;
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
import org.springframework.web.bind.annotation.PathVariable;
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
import static com.tinyerp.gateway.util.RestPaths.VOUCHER_EVENT;

@RestController
@RequestMapping(API_VERSION_1)
public class VoucherResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResource.class);

    private final VoucherService voucherService;

    private final String ENTITY_NAME = "voucher";

    @Autowired
    public VoucherResource(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping(VOUCHER)
    @Timed
    public ResponseEntity<ApiVoucherResponse> claimVoucher(@Valid @RequestBody ApiClaimVoucher apiClaimVoucher,
                                                           @AuthenticationPrincipal OAuth2Authentication principal) throws URISyntaxException {
        LOGGER.debug("REST request to claim Voucher : {}", apiClaimVoucher);
        LOGGER.debug("REST request to claim Voucher sent By: {}", principal);
        ApiVoucherResponse result = voucherService.claim(apiClaimVoucher);
        LOGGER.debug("REST response for claim Voucher : {}", result);
        return ResponseEntity.created(new URI(API_VERSION_1_VOUCHER + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    // TODO: where to handle if user is allowed to do this transition? might be need to use spring security with ssm
    @SuppressWarnings("MVCPathVariableInspection")
    @PostMapping(VOUCHER_EVENT)
    @Timed
    public ResponseEntity<ApiVoucherResponse> onEvent(@PathVariable("id") VoucherId id, @PathVariable("event") VoucherEvent event) {
        LOGGER.info("VoucherId {}", id);
        LOGGER.info("VoucherEvent {}", event);
        ApiVoucherResponse response = voucherService.onEvent(id, event);
        return ResponseEntity.ok(response);
    }

}
