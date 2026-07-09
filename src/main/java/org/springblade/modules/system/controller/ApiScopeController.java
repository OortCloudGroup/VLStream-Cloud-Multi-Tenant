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
import org.springblade.modules.system.pojo.entity.ApiScope;
import org.springblade.modules.system.pojo.vo.ApiScopeVO;
import org.springblade.modules.system.service.IApiScopeService;
import org.springblade.modules.system.wrapper.ApiScopeWrapper;
import org.springframework.web.bind.annotation.*;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * Interface permission controller
 *
 * @author Oort
 */
@NonDS
@RestController
@AllArgsConstructor
@IsAdmin
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/api-scope")
@Tag(name = "Interface permissions", description = "Interface permissions")
public class ApiScopeController extends BladeController {

	private final IApiScopeService apiScopeService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingdataScope")
	public R<ApiScope> detail(ApiScope dataScope) {
		ApiScope detail = apiScopeService.getOne(Condition.getQueryWrapper(dataScope));
		return R.data(detail);
	}

	/**
	 * Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingdataScope")
	public R<IPage<ApiScopeVO>> list(ApiScope dataScope, Query query) {
		IPage<ApiScope> pages = apiScopeService.page(Condition.getPage(query), Condition.getQueryWrapper(dataScope));
		return R.data(ApiScopeWrapper.build().pageVO(pages));
	}

	/**
	 * New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingdataScope")
	public R save(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(apiScopeService.save(dataScope));
	}

	/**
	 * Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingdataScope")
	public R update(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(apiScopeService.updateById(dataScope));
	}

	/**
	 * Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingdataScope")
	public R submit(@Valid @RequestBody ApiScope dataScope) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(apiScopeService.saveOrUpdate(dataScope));
	}


	/**
	 * delete
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		return R.status(apiScopeService.deleteLogic(Func.toLongList(ids)));
	}

}
