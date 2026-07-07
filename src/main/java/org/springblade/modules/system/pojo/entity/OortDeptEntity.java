package org.springblade.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tenant.mp.TenantEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 *  实体类
 *
 * @author BladeX
 * @since 2025-09-04
 */
@Data
@TableName("oort_dept")
@Schema(description = "OortDeptEntity对象")
@EqualsAndHashCode(callSuper = true)
public class OortDeptEntity extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * UUID
	 */
	@Schema(description = "UUID")
	private String oortUdid;
	/**
	 * 部门名称
	 */
	@Schema(description = "部门名称")
	private String oortDname;
	/**
	 * 部门拼音首字母
	 */
	@Schema(description = "部门拼音首字母")
	private String oortDnamefl;
	/**
	 * 部门编码
	 */
	@Schema(description = "部门编码")
	private String oortDcode;
	/**
	 * 父部门名称
	 */
	@Schema(description = "父部门名称")
	private String oortPdname;
	/**
	 * 父部门编码
	 */
	@Schema(description = "父部门编码")
	private String oortPdcode;
	/**
	 * 部门类型，0其它单位，1编制单位，2临时单位...9测试单位
	 */
	@Schema(description = "部门类型，0其它单位，1编制单位，2临时单位...9测试单位")
	private Byte oortDtype;
	/**
	 * 部门职级，0其它，1科级，2处级，3局级，4厅级...
	 */
	@Schema(description = "部门职级，0其它，1科级，2处级，3局级，4厅级...")
	private Byte oortDpost;
	/**
	 * 部门状态，0禁用，1正常，...9删除
	 */
	@Schema(description = "部门状态，0禁用，1正常，...9删除")
	private Byte oortStatus;
	/**
	 * 部门排序 越小越靠前
	 */
	@Schema(description = "部门排序 越小越靠前")
	private Integer oortDsort;
	/**
	 * 部门联系电话
	 */
	@Schema(description = "部门联系电话")
	private String oortDtel;
	/**
	 * 部门地址
	 */
	@Schema(description = "部门地址")
	private String oortDaddr;
	/**
	 * 部门层级
	 */
	@Schema(description = "部门层级")
	private Integer oortDlevel;
	/**
	 * 部门层级路径 从最顶层到所在部门的层级路径
	 */
	@Schema(description = "部门层级路径 从最顶层到所在部门的层级路径")
	private String oortDpath;
	/**
	 * 部门编码层级路径 从最顶层到所在部门的编码层级路径
	 */
	@Schema(description = "部门编码层级路径 从最顶层到所在部门的编码层级路径")
	private String oortDcodepath;
	/**
	 * 部门最后更新人
	 */
	@Schema(description = "部门最后更新人")
	private String oortDuupdate;
	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String oortDremark;
	/**
	 * 部门创建时间
	 */
	@Schema(description = "部门创建时间")
	private LocalDateTime oortDtcreate;
	/**
	 * 部门更新时间
	 */
	@Schema(description = "部门更新时间")
	private LocalDateTime oortDupdate;
	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	private Integer oortTdelete;
	/**
	 * 更新标记
	 */
	@Schema(description = "更新标记")
	private Integer uptag;
	/**
	 * Logo地址
	 */
	@Schema(description = "Logo地址")
	private String oortDeptPhoto;
	/**
	 * 是否通讯录中隐藏
	 */
	@Schema(description = "是否通讯录中隐藏")
	private Byte ishidden;
	/**
	 * 保留字段1
	 */
	@Schema(description = "保留字段1")
	private String exfield1;
	/**
	 * 保留字段2
	 */
	@Schema(description = "保留字段2")
	private String exfield2;
	/**
	 * 保留字段3
	 */
	@Schema(description = "保留字段3")
	private String exfield3;
	/**
	 * 保留字段4
	 */
	@Schema(description = "保留字段4")
	private String exfield4;
	/**
	 * 保留字段5
	 */
	@Schema(description = "保留字段5")
	private String exfield5;
	/**
	 * 保留字段6
	 */
	@Schema(description = "保留字段6")
	private String exfield6;
	/**
	 * 保留字段7
	 */
	@Schema(description = "保留字段7")
	private String exfield7;
	/**
	 * 保留字段8
	 */
	@Schema(description = "保留字段8")
	private String exfield8;
	/**
	 * 保留字段9
	 */
	@Schema(description = "保留字段9")
	private String exfield9;
	/**
	 * 保留字段10
	 */
	@Schema(description = "保留字段10")
	private String exfield10;

}
