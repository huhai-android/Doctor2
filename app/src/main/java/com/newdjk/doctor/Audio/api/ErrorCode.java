package com.newdjk.doctor.Audio.api;

import java.util.HashMap;

public class ErrorCode {
    public final static Integer SAAS_SUCC = 0;
    public final static Integer SAAS_TIME_OUT = 1000;
    public final static Integer SAAS_UNEXPECTED_ERROR = 1999;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_USER_ID_NOT_EXIST_ERROR = 1001;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_USER_PASSWORD_NOT_MATCH_ERROR = 1002;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_SIGNATURE_NOT_VALID = 1003;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_SIGNATURE_HTTP_BODY_NOT_MATCHED = 1004;
    public final static Integer SAAS_JSON_CONFIG_HELPER_READ_ERROR = 1101;
    public final static Integer SAAS_AUTHENTICATE_API_INVALID_PARAMETER_ERROR = 1102;
    public final static Integer SAAS_JSON_CONFIG_HELPER_GIVEN_KEY_NOT_EXIST_ERROR = 1103;
    public final static Integer SAAS_HTTP_REQUEST_TOO_LARGE_ERROR = 1104;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_IMAGE_LOAD_ERROR = 1105;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_IMAGE_NO_FACE_DETECTED_ERROR = 1106;
    public final static Integer SAAS_AUTHENTICATE_SERVICE_NO_INFORMATION_ERROR = 1107;
    public final static Integer SAAS_NOT_IMPLEMENTED_FUNCTION_ERROR = 1108;
    public final static Integer SAAS_SPEECH_CUSTOMWORDS_RESOURCE_NOT_FOUND_ERROR = 4101;
    public final static Integer SAAS_SPEECH_ALL_CUSTOMWORDS_NUMBER_QUOTA_LIMIT_ERROR = 4102;
    public final static Integer SAAS_SPEECH_QUOTA_LIMIT_ERROR = 4103;
    public final static Integer SAAS_SPEECH_BAD_SPEECH = 4104;

    public static String getDesc(Integer code) {
        if (ErrorCode.APP_DESC_MAP == null) {
            ErrorCode.APP_DESC_MAP = new HashMap<Integer, String>();
            APP_DESC_MAP.put(ErrorCode.SAAS_SUCC, "success");
            APP_DESC_MAP.put(ErrorCode.SAAS_TIME_OUT, "connection timed out");
            APP_DESC_MAP.put(ErrorCode.SAAS_UNEXPECTED_ERROR, "unexpected error");
            APP_DESC_MAP.put(ErrorCode.SAAS_AUTHENTICATE_API_INVALID_PARAMETER_ERROR, "invalid parameter");
            APP_DESC_MAP.put(ErrorCode.SAAS_SPEECH_CUSTOMWORDS_RESOURCE_NOT_FOUND_ERROR, "invalid custom words resource");
            APP_DESC_MAP.put(ErrorCode.SAAS_AUTHENTICATE_SERVICE_USER_ID_NOT_EXIST_ERROR, "user not found");
            APP_DESC_MAP.put(ErrorCode.SAAS_AUTHENTICATE_SERVICE_SIGNATURE_NOT_VALID, "invalid signature");
            APP_DESC_MAP.put(ErrorCode.SAAS_SPEECH_QUOTA_LIMIT_ERROR, "user quota used up");
            APP_DESC_MAP.put(ErrorCode.SAAS_SPEECH_BAD_SPEECH, "failed to load audio file");
        }
        return ErrorCode.APP_DESC_MAP.get(code);
    }

    private static HashMap<Integer, String> APP_DESC_MAP;
}
