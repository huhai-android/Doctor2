package com.newdjk.doctor.Audio.requests;

import com.newdjk.doctor.Audio.api.ClientException;
import com.newdjk.doctor.Audio.api.ErrorCode;
import com.newdjk.doctor.Audio.model.ShortSpeechRequest;
import com.newdjk.doctor.Audio.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class RequestsFactory {
    /**
     * Generates a short speech request from given audio file content.
     *
     * @param content           Audio file content.
     * @param aue               Aue.
     * @param useCustomWordsIds Custom word pack ids. Custom word packs shall be registered through web portal.
     * @return Short speech request.
     * @throws ClientException
     */
    public ShortSpeechRequest generateShortSpeechRequestWithContent(String content, ShortSpeechRequest.AueEnum aue, List<String> useCustomWordsIds) throws ClientException {
        // Assert content not null
        if (null == content || content.isEmpty())
            throw new ClientException(ErrorCode.SAAS_SPEECH_BAD_SPEECH);

        // Set default
        if (null == useCustomWordsIds)
            useCustomWordsIds = new ArrayList<>();

        // Initialize
        ShortSpeechRequest shortSpeechRequest = new ShortSpeechRequest();

        // Set
        shortSpeechRequest.setAue(aue);
        shortSpeechRequest.setAudioBase64(content);
        // 默认值，更多信息请参见开发文档https://speech.yitutech.com/devdoc/shortaudio 第5部分 请求参数
        shortSpeechRequest.setLang(ShortSpeechRequest.LangEnum.NUMBER_1);
        // 默认值，更多信息请参见开发文档https://speech.yitutech.com/devdoc/shortaudio 第5部分 请求参数
        shortSpeechRequest.setScene(0);
        shortSpeechRequest.setUseCustomWordsIds(useCustomWordsIds);

        // Return
        return shortSpeechRequest;
    }

    /**
     * Generates a short speech request from given audio file path.
     *
     * @param filePath          Audio file path.
     * @param aue               Aue.
     * @param useCustomWordsIds Custom word pack ids. Custom word packs shall be registered through web portal.
     * @return Short speech request.
     * @throws ClientException
     */
    public ShortSpeechRequest generateShortSpeechRequestWithFile(String filePath, ShortSpeechRequest.AueEnum aue, List<String> useCustomWordsIds) throws ClientException {
        // Assert input
        if (null == filePath || filePath.isEmpty() || !new File(filePath).isFile())
            throw new ClientException(ErrorCode.SAAS_SPEECH_BAD_SPEECH);

        // Read file content
        String content = null;
        try {
            content = FileUtils.readFileAsBase64String(filePath);
        } catch (IOException exception) {
            throw new ClientException(ErrorCode.SAAS_SPEECH_BAD_SPEECH);
        }

        // Return
        return this.generateShortSpeechRequestWithContent(content, aue, useCustomWordsIds);
    }
}
