package com.tinyerp.gateway.util;

/**
 Collected constants of general utility.

 <P>All members of this class are immutable.

 */
public final class RestPaths {

    public final static String API = "/api";
    public final static String VERSION_1 = "/v1";

    public final static String API_VERSION_1 = API + VERSION_1;

    public final static String VOUCHER = "/voucher";
    public final static String VOUCHER_ID = "voucherId";
    public final static String VOUCHER_EVENT = "voucherEvent";
    public final static String VOUCHER_ID_EVENT = VOUCHER + "/{" + VOUCHER_ID + "}/{"+ VOUCHER_EVENT + "}";
    public final static String API_VERSION_1_VOUCHER = API_VERSION_1 + VOUCHER;
    public final static String API_VERSION_1_VOUCHER_EVENT = API_VERSION_1 + VOUCHER_EVENT;

    public final static String ACTUATOR_HEALTH = "/actuator/health";
    public final static String ACTUATOR_INFO = "/actuator/info";

    public final static String SWAGGER_UI = "/swagger-ui.html";

    // PRIVATE //

    /**
     The caller references the constants using <tt>RestPaths.EMPTY_STRING</tt>,
     and so on. Thus, the caller should be prevented from constructing objects of
     this class, by declaring this private constructor.
     */
    private RestPaths() {
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
