package com.hypers.hm.api;

/**
 * Constants for Hypers Bridge API.
 * Package: com.hypers.hm
 */
public final class HypersApiConstants {

    private HypersApiConstants() {}

    public static final String BINDER_DESCRIPTOR = "com.hypers.hm.server.IHypersService";

    public static final int SERVER_VERSION = 13;

    // Transaction codes
    public static final int BINDER_TRANSACTION_transact = 1;

    // attachApplication bundle keys
    public static final String ATTACH_APPLICATION_API_VERSION     = "api_version";
    public static final String ATTACH_APPLICATION_PACKAGE_NAME    = "package_name";

    // bindApplication bundle keys
    public static final String BIND_APPLICATION_SERVER_UID                          = "uid";
    public static final String BIND_APPLICATION_SERVER_VERSION                      = "version";
    public static final String BIND_APPLICATION_SERVER_PATCH_VERSION               = "patch_version";
    public static final String BIND_APPLICATION_SERVER_SECONTEXT                   = "secontext";
    public static final String BIND_APPLICATION_PERMISSION_GRANTED                 = "permission_granted";
    public static final String BIND_APPLICATION_SHOULD_SHOW_REQUEST_PERMISSION_RATIONALE = "should_show_rationale";

    // requestPermission reply keys
    public static final String REQUEST_PERMISSION_REPLY_ALLOWED    = "allowed";
    // FIX: konstanta ini hilang — dipakai di RequestPermissionActivity & HypersManagerProvider
    public static final String REQUEST_PERMISSION_REPLY_IS_ONETIME = "is_onetime";

    // UserService bundle keys
    public static final String USER_SERVICE_ARG_COMPONENT            = "component";
    public static final String USER_SERVICE_ARG_DEBUGGABLE           = "debuggable";
    public static final String USER_SERVICE_ARG_VERSION_CODE         = "version_code";
    public static final String USER_SERVICE_ARG_DAEMON               = "daemon";
    public static final String USER_SERVICE_ARG_USE_32_BIT_APP_PROCESS = "use_32_bit";
    public static final String USER_SERVICE_ARG_PROCESS_NAME         = "process_name";
    public static final String USER_SERVICE_ARG_TAG                  = "tag";
    public static final String USER_SERVICE_ARG_REMOVE               = "remove";
    public static final String USER_SERVICE_ARG_NO_CREATE            = "no_create";
    // FIX: konstanta ini hilang — dipakai di HypersManagerProvider
    public static final String USER_SERVICE_ARG_TOKEN                = "token";

    // Permission
    public static final String HYPERS_PERMISSION = "com.hypers.hm.manager.permission.API_V23";

    // Provider authority suffix
    public static final String PROVIDER_AUTHORITY_SUFFIX = ".hypers";

    // Provider methods
    public static final String METHOD_SEND_BINDER = "sendBinder";
    public static final String METHOD_GET_BINDER  = "getBinder";

    // Provider extras
    public static final String EXTRA_BINDER = "com.hypers.hm.privileged.api.intent.extra.BINDER";

    // Broadcast action
    public static final String ACTION_BINDER_RECEIVED = "com.hypers.hm.api.action.BINDER_RECEIVED";

    // Config flags
    public static final int FLAG_ALLOWED     = 1 << 1;
    public static final int FLAG_DENIED      = 1 << 2;
    public static final int MASK_PERMISSION  = FLAG_ALLOWED | FLAG_DENIED;

    // Pairing / start modes
    public static final String START_MODE_ROOT     = "root";
    public static final String START_MODE_ADB      = "adb";
    public static final String START_MODE_COMPUTER = "computer";

    // Command actions for bridge
    public static final String CMD_START_PAIRING   = "com.hypers.hm.action.START_PAIRING";
    public static final String CMD_START_ROOT      = "com.hypers.hm.action.START_ROOT";
    public static final String CMD_START_ADB       = "com.hypers.hm.action.START_ADB";
    public static final String CMD_START_COMPUTER  = "com.hypers.hm.action.START_COMPUTER";
    public static final String CMD_STOP_SERVICE    = "com.hypers.hm.action.STOP_SERVICE";

    // FIRST_CALL_TRANSACTION value (dari android.os.IBinder)
    int BINDER_TRANSACTION_VALUE = 1;
}
