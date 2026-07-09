package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.node.INode;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Region;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrative division table view entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Administrative division table")
public class RegionVO extends Region implements INode<RegionVO> {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * primary keyID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * parent nodeID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	/**
	 * Parent node name
	 */
	private String parentName;

	/**
	 * Whether there are descendant nodes
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Boolean hasChildren;

	/**
	 * descendant node
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<RegionVO> children;

	@Override
	public Long getId() {
		return Func.toLong(this.getCode());
	}

	@Override
	public Long getParentId() {
		return Func.toLong(this.getParentCode());
	}

	@Override
	public List<RegionVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
}
