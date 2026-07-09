package org.springblade.vlstream.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * File storage response class
 * </p>
 *
 * @author Liu Xin
 * @since 2025-04-07
 */
@Data
@Schema(description = "File storage response class")
public class FileResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "file accessURL", example = "file accessURL:http://example.com/files/2024/04/01/audio.mp3")
    private String url;

    @Schema(description =  "documentMD5value", example = "documentMD5value:d41d8cd98f00b204e9800998ecf8427e")
    private String md5;

    @Schema(description =  "File storage path", example = "File storage path:/files/2024/04/01/audio.mp3")
    private String path;

    @Schema(description =  "File domain name", example = "File domain name:http://example.com")
    private String domain;

    @Schema(description =  "File scene", example = "File scene:audio")
    private String scene;

    @Schema(description =  "file size", example = "file size:1024000")
    private Long size;

    @Schema(description =  "File modification time", example = "File modification time:1711968000")
    private Long mtime;

    @Schema(description =  "File scene list", example = "File scene list:audio,video,image")
    private String scenes;

    @Schema(description =  "return message", example = "return message:200")
    private String retmsg;

    @Schema(description =  "return code", example = "return code:200")
    private Integer retcode;

    @Schema(description =  "Source file path", example = "Source file path:/upload/2024/04/01/audio.mp3")
    private String src;

    @Schema(description =  "File duration(Second)", example = "File duration(Second):300")
    private Integer duration;
}
