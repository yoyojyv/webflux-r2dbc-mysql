package me.jerry.example.webflux.constant;

public final class SecurityConstants {

    public static final String PASSWORD_PREFIX_NOOP = "{noop}";

    // ==================== Roles ====================
    public static final String ROLE_SYSTEM = "SYSTEM";       // system 상에서 정보를 주고받을 때 사용
    public static final String ROLE_ADMIN = "ADMIN";         // dashboard 등을 볼때 사용
    public static final String ROLE_ACTUATOR = "ACTUATOR";   // actuator 관련
    public static final String ROLE_API_DOCS = "DOCS";    // API Document 조회

}
