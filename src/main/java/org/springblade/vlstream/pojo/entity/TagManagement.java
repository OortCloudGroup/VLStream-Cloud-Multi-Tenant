package org.springblade.vlstream.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;

/**
 * Tag management table Entity class
 *
 * @author Oort
 * @since 2025-12-23
 */
@Data
@TableName("vls_tag_management")
@Schema(description = "VlsTagManagementEntityobject")
@EqualsAndHashCode(callSuper = true)
public class TagManagement extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Tag name
	 */
	@Schema(description = "Tag name")
	private String tagName;
	/**
	 * Tag categories: own-private label, public-public tags
	 */
	@Schema(description = "Tag categories: own-private label, public-public tags")
	private String categoryType;
	/**
	 * Hierarchy: 1-Tag type, 2-specific tags
	 */
	@Schema(description = "Hierarchy: 1-Tag type, 2-specific tags")
	private Integer level;
	/**
	 * parentID, level=1time isNULL, level=2is the label typeID
	 */
	@Schema(description = "parentID, level=1time isNULL, level=2is the label typeID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * sort order
	 */
	@Schema(description = "sort order")
	private Integer sortOrder;
	/**
	 * Label color
	 */
	@Schema(description = "Label color")
	private String tagColor;
	/**
	 * label icon
	 */
	@Schema(description = "label icon")
	private String tagIcon;
	/**
	 * Tag description
	 */
	@Schema(description = "Tag description")
	private String description;
	/**
	 * Whether to enable: 1-enable, 0-Disable
	 */
	@Schema(description = "Whether to enable: 1-enable, 0-Disable")
	private Integer isActive;
	/**
	 * Number of uses
	 */
	@Schema(description = "Number of uses")
	private Integer usageCount;

}
