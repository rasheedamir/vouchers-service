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
    public final static String API_VERSION_1_VOUCHER = API_VERSION_1 + VOUCHER;

    public final static String ACTUATOR_HEALTH = "/actuator/health";
    public final static String ACTUATOR_INFO = "/actuator/info";

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
