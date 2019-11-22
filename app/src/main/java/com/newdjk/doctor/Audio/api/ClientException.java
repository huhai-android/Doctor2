package com.newdjk.doctor.Audio.api;

public class ClientException extends Throwable {
    private int code = 0;
    private String message;

    /**
     * Constructs a client exception instance.
     * @param code Rtn code.
     */
    public ClientException(int code) {
        this.code = code;
        this.message = ErrorCode.getDesc(this.code);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }
}
