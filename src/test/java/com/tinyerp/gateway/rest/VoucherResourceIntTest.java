package com.tinyerp.gateway.rest;

import com.tinyerp.gateway.GatewayApplication;

import com.tinyerp.gateway.domain.Voucher;
import com.tinyerp.gateway.json.ApiVoucher;
import com.tinyerp.gateway.mapper.VoucherMapper;
import com.tinyerp.gateway.util.TestUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.tinyerp.gateway.util.Consts.API_VERSION_1_VOUCHER;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class VoucherResourceIntTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherResourceIntTest.class);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RuntimeService runtimeService;

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

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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
    public void givenProcess_whenStartProcess_thenIncreaseInProcessInstanceCount() throws Exception {

        // Create the Voucher
        ApiVoucher apiVoucher = VoucherMapper.from(voucher);

        // Post the Voucher
        this.mockMvc.perform(MockMvcRequestBuilders.post(API_VERSION_1_VOUCHER)
                .with(authentication(new UsernamePasswordAuthenticationToken("user", "password")))
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apiVoucher)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }
}