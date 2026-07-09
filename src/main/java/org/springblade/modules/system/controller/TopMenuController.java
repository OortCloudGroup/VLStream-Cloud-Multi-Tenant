package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.TopMenu;
import org.springblade.modules.system.pojo.vo.GrantVO;
import org.springblade.modules.system.service.ITopMenuService;
import org.springframework.web.bind.annotation.*;

import static org.springblade.core.cache.constant.CacheConstant.MENU_CACHE;
import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * top menu table controller
 *
 * @author Oort
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/topmenu")
@Tag(name = "top menu table", description = "top menu")
public class TopMenuController extends BladeController {

	private final ITopMenuService topMenuService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingtopMenu")
	public R<TopMenu> detail(TopMenu topMenu) {
		TopMenu detail = topMenuService.getOne(Condition.getQueryWrapper(topMenu));
		return R.data(detail);
	}

	/**
	 * Pagination top menu table
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingtopMenu")
	public R<IPage<TopMenu>> list(TopMenu topMenu, Query query) {
		IPage<TopMenu> pages = topMenuService.page(Condition.getPage(query), Condition.getQueryWrapper(topMenu).lambda().orderByAsc(TopMenu::getSort));
		return R.data(pages);
	}

	/**
	 * New top menu table
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingtopMenu")
	public R save(@Valid @RequestBody TopMenu topMenu) {
		return R.status(topMenuService.save(topMenu));
	}

	/**
	 * Revise top menu table
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingtopMenu")
	public R update(@Valid @RequestBody TopMenu topMenu) {
		return R.status(topMenuService.updateById(topMenu));
	}

	/**
	 * Add or modify top menu table
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingtopMenu")
	public R submit(@Valid @RequestBody TopMenu topMenu) {
		return R.status(topMenuService.saveOrUpdate(topMenu));
	}


	/**
	 * delete top menu table
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(topMenuService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Set top menu
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Top menu configuration", description = "incomingtopMenuIdcollection as wellmenuIdgather")
	public R grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		boolean temp = topMenuService.grant(grantVO.getTopMenuIds(), grantVO.getMenuIds());
		return R.status(temp);
	}

}
