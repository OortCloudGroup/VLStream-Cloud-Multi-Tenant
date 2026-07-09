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
 * Administrative division table controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@PreAuth(menu = "region")
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/region")
@Tag(name = "Administrative division", description = "Administrative division")
public class RegionController extends BladeController {

	private final IRegionService regionService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingregion")
	public R<RegionVO> detail(Region region) {
		Region detail = regionService.getOne(Condition.getQueryWrapper(region));
		return R.data(RegionWrapper.build().entityVO(detail));
	}

	/**
	 * Pagination Administrative division table
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingregion")
	public R<IPage<Region>> list(Region region, Query query) {
		IPage<Region> pages = regionService.page(Condition.getPage(query), Condition.getQueryWrapper(region));
		return R.data(pages);
	}

	/**
	 * Lazy loading list
	 */
	@GetMapping("/lazy-list")
	@Parameters({
		@Parameter(name = "code", description = "Zoning number", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "Zoning name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Lazy loading list", description = "incomingmenu")
	public R<List<RegionVO>> lazyList(String parentCode, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyList(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * Lazy loading list
	 */
	@GetMapping("/lazy-tree")
	@Parameters({
		@Parameter(name = "code", description = "Zoning number", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "name", description = "Zoning name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Lazy loading list", description = "incomingmenu")
	public R<List<RegionVO>> lazyTree(String parentCode, @Parameter(hidden = true) @RequestParam Map<String, Object> menu) {
		List<RegionVO> list = regionService.lazyTree(parentCode, menu);
		return R.data(RegionWrapper.build().listNodeLazyVO(list));
	}

	/**
	 * New Administrative division table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "New", description = "incomingregion")
	public R save(@Valid @RequestBody Region region) {
		return R.status(regionService.save(region));
	}

	/**
	 * Revise Administrative division table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Revise", description = "incomingregion")
	public R update(@Valid @RequestBody Region region) {
		return R.status(regionService.updateById(region));
	}

	/**
	 * Add or modify Administrative division table
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "Add or modify", description = "incomingregion")
	public R submit(@Valid @RequestBody Region region) {
		return R.status(regionService.submit(region));
	}


	/**
	 * delete Administrative division table
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "delete", description = "Pass in primary key")
	public R remove(@Parameter(description = "primary key", required = true) @RequestParam String id) {
		return R.status(regionService.removeRegion(id));
	}

	/**
	 * Administrative division drop-down data source
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Drop down data source", description = "incomingtenant")
	public R<List<Region>> select(@RequestParam(required = false, defaultValue = "00") String code) {
		List<Region> list = regionService.list(Wrappers.<Region>query().lambda().eq(Region::getParentCode, code));
		return R.data(list);
	}

	/**
	 * Import administrative division data
	 */
	@PostMapping("import-region")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Import administrative divisions", description = "incomingexcel")
	public R importRegion(MultipartFile file, Integer isCovered) {
		RegionImporter regionImporter = new RegionImporter(regionService, isCovered == 1);
		ExcelUtil.save(file, regionImporter, RegionExcel.class);
		return R.success("Operation successful");
	}

	/**
	 * Export administrative division data
	 */
	@GetMapping("export-region")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "Export administrative divisions", description = "incominguser")
	public void exportRegion(@Parameter(hidden = true) @RequestParam Map<String, Object> region, HttpServletResponse response) {
		QueryWrapper<Region> queryWrapper = Condition.getQueryWrapper(region, Region.class);
		List<RegionExcel> list = regionService.exportRegion(queryWrapper);
		ExcelUtil.export(response, "Administrative division data" + DateUtil.time(), "Administrative division data table", list, RegionExcel.class);
	}

	/**
	 * Export template
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "Export template")
	public void exportUser(HttpServletResponse response) {
		List<RegionExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "Administrative division template", "Administrative division table", list, RegionExcel.class);
	}


}
