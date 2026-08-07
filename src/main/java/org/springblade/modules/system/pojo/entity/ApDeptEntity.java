package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 组织机构表 实体类
 *
 * @author BladeX
 * @since 2025-08-09
 */
@Data
@TableName("ap_dept")
@Schema(description = "ApDeptEntity对象")
public class ApDeptEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构ID
	 */
	@Schema(description = "组织机构ID")
	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	private String deptId;
	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 组织机构编码
	 */
	@Schema(description = "组织机构编码")
	private String deptCode;
	/**
	 * 父组织机构ID
	 */
	@Schema(description = "父组织机构ID")
	private String parentDeptId;
	/**
	 * 组织机构类型 1:集团 2:公司 3:部门 4:项目 0:未知
	 */
	@Schema(description = "组织机构类型 1:集团 2:公司 3:部门 4:项目 0:未知")
	private Integer deptType;
	/**
	 * 组织机构名称
	 */
	@Schema(description = "组织机构名称")
	private String deptName;
	/**
	 * 组织机构编码层级路径
	 */
	@Schema(description = "组织机构编码层级路径")
	private String deptCodePath;
	/**
	 * 组织机构名称层级路径
	 */
	@Schema(description = "组织机构名称层级路径")
	private String deptNamePath;
	/**
	 * 组织机构层级
	 */
	@Schema(description = "组织机构层级")
	private Long deptLevel;
	/**
	 * 部门排序 越小越靠前
	 */
	@Schema(description = "部门排序 越小越靠前")
	private Integer sort;
	/**
	 * 额外数据
	 */
	@Schema(description = "额外数据")
	private String data;
	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createdAt;
	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updatedAt;
	/**
	 * 删除时间戳
	 */
	@Schema(description = "删除时间戳")
	private Long deletedAt;
	/**
	 * 创建者的标识
	 */
	@Schema(description = "创建者的标识")
	private String createdBy;
	/**
	 * 最后更新者的标识
	 */
	@Schema(description = "最后更新者的标识")
	private String updatedBy;
	/**
	 * 审核表关联id
	 */
	@Schema(description = "审核表关联id")
	private Integer checkId;

}
