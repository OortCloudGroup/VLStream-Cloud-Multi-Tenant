package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Video recording record sheet Entity class
 *
 * @author Oort
 * @since 2025-12-25
 */
@Data
@TableName("vls_video_record")
@Schema(description = "VlsVideoRecordEntityobject")
@EqualsAndHashCode(callSuper = true)
public class VideoRecord extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * stream
	 */
	@Schema(description = "stream")
	private String stream;
	/**
	 * equipmentID
	 */
	@Schema(description = "equipmentID")
	private Long deviceId;
	/**
	 * Device name
	 */
	@Schema(description = "Device name")
	private String deviceName;
	/**
	 * Video file name
	 */
	@Schema(description = "Video file name")
	private String fileName;
	/**
	 * Video file path
	 */
	@Schema(description = "Video file path")
	private String filePath;
	/**
	 * file size(byte)
	 */
	@Schema(description = "file size(byte)")
	private Long fileSize;
	/**
	 * on demandurl
	 */
	@Schema(description = "on demandurl")
	private String url;
	/**
	 * Video duration(Second)
	 */
	@Schema(description = "Video duration(Second)")
	private Integer duration;
	/**
	 * video format
	 */
	@Schema(description = "video format")
	private String format;
	/**
	 * Recording start time
	 */
	@Schema(description = "Recording start time")
	private LocalDateTime recordStartTime;
	/**
	 * Recording end time
	 */
	@Schema(description = "Recording end time")
	private LocalDateTime recordEndTime;
	/**
	 * recording date(for grouping by date)
	 */
	@Schema(description = "recording date(for grouping by date)")
	private LocalDate recordDate;
	/**
	 * Recording status
	 */
	@Schema(description = "Recording status")
	private String recordStatus;
	/**
	 * thumbnail path
	 */
	@Schema(description = "thumbnail path")
	private String thumbnailPath;

}
