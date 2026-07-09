package org.springblade.modules.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.node.INode;
import org.springblade.modules.system.pojo.entity.Dict;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * View entity class
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DictVOobject")
public class DictVO extends Dict implements INode<DictVO> {
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
	 * descendant node
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<DictVO> children;

	@Override
	public List<DictVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}

	/**
	 * Superior dictionary
	 */
	private String parentName;
}
