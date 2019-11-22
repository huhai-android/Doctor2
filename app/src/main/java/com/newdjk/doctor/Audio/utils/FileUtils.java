package com.newdjk.doctor.Audio.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class FileUtils {
    /**
     * Encode a byte array into a base 64 encoded string.
     *
     * @param bytes Byte array to convert.
     * @return Converted string.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Reads a file content as a byte array.
     *
     * @param filePath The path of the destination file.
     * @return File content as a byte array.
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Reads a file content and convert it into a base 64 encoded string.
     *
     * @param filePath The path of the destination file.
     * @return File content as a base 64 encoded string.
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String readFileAsBase64String(String filePath) throws IOException {
        byte[] content = FileUtils.readFile(filePath);
        return FileUtils.encodeBase64String(content);
    }
}
