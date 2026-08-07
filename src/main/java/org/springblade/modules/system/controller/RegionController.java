package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.modules.system.pojo.entity.Region;
import org.springblade.modules.system.excel.RegionExcel;
import org.springblade.modules.system.excel.RegionImporter;
import org.springblade.modules.system.service.IRegionService;
import org.springblade.modules.system.pojo.vo.RegionVO;
import org.springblade.modules.system.wrapper.RegionWrapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行政区划表 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@PreAuth(menu = "region")
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/region")
@Tag(name = "行政区划", description = "行政区划")
public class RegionController extends BladeController {

	private final IRegionService regionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入region")
	public R<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return R.data(RegionWrapper.build().entityVO(detail));
	}

	/**
	 * 分页 行政区划表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入region")
	public R<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return R.data(pages);
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "code", description = "区划编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "区划名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "懒加载列表", description = "传入menu")
	public R<List<RegionVO>> lazyList(String parentCode, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyList(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 懒加载列表
	 */
	@GetMapping("/lazy-tree")
	@Parameters({
		@Parameter(name = "code", description = "区划编号", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "区划名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 4)
	@Operation(summary = "懒加载列表", description = "传入menu")
	public R<List<RegionVO>> lazyTree(String parentCode, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyTree(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * 新增 行政区划表
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增", description = "传入region")
	public R save(@Valid @RequestBody Region region) {
		return R.status(regionService.save(region));
	}

	/**
	 * 修改 行政区划表
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "修改", description = "传入region")
	public R update(@Valid @RequestBody Region region) {
		return R.status(regionService.updateById(region));
	}

	/**
	 * 新增或修改 行政区划表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "新增或修改", description = "传入region")
	public R submit(@Valid @RequestBody Region region) {
		return R.status(regionService.submit(region));
	}


	/**
	 * 删除 行政区划表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "删除", description = "传入主键")
	public R remove(@Parameter(description = "主键", required = true) @RequestParam String id) {
		return R.status(regionService.removeRegion(id));
	}

	/**
	 * 行政区划下拉数据源
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "下拉数据源", description = "传入tenant")
	public R<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return R.data(list);
	}

	/**
	 * 导入行政区划数据
	 */
	@PostMapping("import-region")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "导入行政区划", description = "传入excel")
	public R importRegion(MultipartFile file, Integer isCovered) {
		RegionImporter regionImporter = new RegionImporter(regionService, isCovered == 1);
		ExcelUtil.save(file, regionImporter, RegionExcel.class);
		return R.success("操作成功");
	}

	/**
	 * 导出行政区划数据
	 */
	@GetMapping("export-region")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "导出行政区划", description = "传入user")
	public void exportRegion(@Parameter(hidden = true) @RequestParam Map<String, Object> region, HttpServletResponse response) {
		QueryWrapper<Region> queryWrapper = Condition.getQueryWrapper(region, Region.class);
		List<RegionExcel> list = regionService.exportRegion(queryWrapper);
		ExcelUtil.export(response, "行政区划数据" + DateUtil.time(), "行政区划数据表", list, RegionExcel.class);
	}

	/**
	 * 导出模板
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<RegionExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "行政区划模板", "行政区划表", list, RegionExcel.class);
	}


}
