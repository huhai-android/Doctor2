/*
 * Yitu Speech REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.newdjk.doctor.Audio.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



/**
 * ShortSpeechRequest
 */

public class ShortSpeechRequest{

  /**
   * 音频编码格式，根据音频格式，单选对应的编码格式。
   */
  @JsonAdapter(AueEnum.Adapter.class)
  public enum AueEnum {
    PCM("pcm"),
    AAC("aac"),
    MPEG2("mpeg2");

    private String value;

    AueEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static AueEnum fromValue(String text) {
      for (AueEnum b : AueEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<AueEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AueEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AueEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AueEnum.fromValue(String.valueOf(value));
      }
    }
  }
  @SerializedName("aue")
  private AueEnum aue = AueEnum.PCM;
  /**
   * 语言模型选择。目前仅支持中文普通话。
   */
  @JsonAdapter(LangEnum.Adapter.class)
  public enum LangEnum {
    NUMBER_1(1);

    private Integer value;

    LangEnum(Integer value) {
      this.value = value;
    }
    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static LangEnum fromValue(String text) {
      for (LangEnum b : LangEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<LangEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LangEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LangEnum read(final JsonReader jsonReader) throws IOException {
        Integer value = jsonReader.nextInt();
        return LangEnum.fromValue(String.valueOf(value));
      }
    }
  }
  @SerializedName("lang")
  private LangEnum lang = LangEnum.NUMBER_1;

  @SerializedName("scene")
  private Integer scene = 0;

  @SerializedName("useCustomWordsIds")
  private List<String> useCustomWordsIds = null;

  @SerializedName("audioBase64")
  private String audioBase64 = null;
  public ShortSpeechRequest aue(AueEnum aue) {
    this.aue = aue;
    return this;
  }

  

  /**
  * 音频编码格式，根据音频格式，单选对应的编码格式。
  * @return aue
  **/
  public AueEnum getAue() {
    return aue;
  }
  public void setAue(AueEnum aue) {
    this.aue = aue;
  }
  public ShortSpeechRequest lang(LangEnum lang) {
    this.lang = lang;
    return this;
  }

  

  /**
  * 语言模型选择。目前仅支持中文普通话。
  * @return lang
  **/

  public LangEnum getLang() {
    return lang;
  }
  public void setLang(LangEnum lang) {
    this.lang = lang;
  }
  public ShortSpeechRequest scene(Integer scene) {
    this.scene = scene;
    return this;
  }

  

  /**
  * 情景模式，针对不同的应用场景可定制模型。
  * @return scene
  **/

  public Integer getScene() {
    return scene;
  }
  public void setScene(Integer scene) {
    this.scene = scene;
  }

  public ShortSpeechRequest useCustomWordsIds(List<String> useCustomWordsIds) {
    this.useCustomWordsIds = useCustomWordsIds;
    return this;
  }

  public ShortSpeechRequest addUseCustomWordsIdsItem(String useCustomWordsIdsItem) {
    if (this.useCustomWordsIds == null) {
      this.useCustomWordsIds = new ArrayList<String>();
    }
    this.useCustomWordsIds.add(useCustomWordsIdsItem);
    return this;
  }

  /**
  * 使用已经上传的自定义词库，填写词库ID。
  * @return useCustomWordsIds
  **/

  public List<String> getUseCustomWordsIds() {
    return useCustomWordsIds;
  }
  public void setUseCustomWordsIds(List<String> useCustomWordsIds) {
    this.useCustomWordsIds = useCustomWordsIds;
  }
  public ShortSpeechRequest audioBase64(String audioBase64) {
    this.audioBase64 = audioBase64;
    return this;
  }



  /**
  * 短语音音频Base64编码
  * @return audioBase64
  **/

  public String getAudioBase64() {
    return audioBase64;
  }
  public void setAudioBase64(String audioBase64) {
    this.audioBase64 = audioBase64;
  }
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShortSpeechRequest shortSpeechRequest = (ShortSpeechRequest) o;
    return Objects.equals(this.aue, shortSpeechRequest.aue) &&
        Objects.equals(this.lang, shortSpeechRequest.lang) &&
        Objects.equals(this.scene, shortSpeechRequest.scene) &&
        Objects.equals(this.useCustomWordsIds, shortSpeechRequest.useCustomWordsIds) &&
        Objects.equals(this.audioBase64, shortSpeechRequest.audioBase64);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public int hashCode() {
    return Objects.hash(aue, lang, scene, useCustomWordsIds, audioBase64);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShortSpeechRequest {\n");

    sb.append("    aue: ").append(toIndentedString(aue)).append("\n");
    sb.append("    lang: ").append(toIndentedString(lang)).append("\n");
    sb.append("    scene: ").append(toIndentedString(scene)).append("\n");
    sb.append("    useCustomWordsIds: ").append(toIndentedString(useCustomWordsIds)).append("\n");
    sb.append("    audioBase64: ").append(toIndentedString(audioBase64)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}