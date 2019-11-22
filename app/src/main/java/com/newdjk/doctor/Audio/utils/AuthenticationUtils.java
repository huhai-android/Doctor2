package com.newdjk.doctor.Audio.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationUtils {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts bytes into hex string.
     *
     * @param bytes Input bytes.
     * @return A hex string.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Calculates the hmac sha256 signature.
     *
     * @param toSign The string to sign.
     * @param key    The secret key to sign with.
     * @return Signature.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String computeSignatureHmacSha256(String toSign, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmacSha256.init(secretKey);
        return AuthenticationUtils.bytesToHex(hmacSha256.doFinal(toSign.getBytes())).toLowerCase();
    }
}
