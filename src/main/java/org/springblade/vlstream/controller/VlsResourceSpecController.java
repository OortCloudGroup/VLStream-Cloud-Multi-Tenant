package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.pojo.entity.ResourceSpec;
import org.springblade.vlstream.pojo.vo.ResourceSpecVO;
import org.springblade.vlstream.service.IVlsResourceSpecService;
import org.springblade.vlstream.wrapper.VlsResourceSpecWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Resource specification configuration table controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vlsResourceSpec")
@Tag(name = "Resource specification configuration", description = "Resource specification configuration interface")
public class VlsResourceSpecController extends BladeController {

	private final IVlsResourceSpecService vlsResourceSpecService;

	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingresourceSpec")
	public R<ResourceSpecVO> detail(ResourceSpec resourceSpec) {
		ResourceSpec detail = vlsResourceSpecService.getOne(Condition.getQueryWrapper(resourceSpec));
		return R.data(VlsResourceSpecWrapper.build().entityVO(detail));
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingresourceSpec")
	public R<IPage<ResourceSpecVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> resourceSpec, Query query) {
		IPage<ResourceSpec> pages = vlsResourceSpecService.page(Condition.getPage(query), Condition.getQueryWrapper(resourceSpec, ResourceSpec.class));
		return R.data(VlsResourceSpecWrapper.build().pageVO(pages));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "New", description = "incomingresourceSpec")
	public R save(@Valid @RequestBody ResourceSpec resourceSpec) {
		return R.status(vlsResourceSpecService.save(resourceSpec));
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "Revise", description = "incomingresourceSpec")
	public R update(@Valid @RequestBody ResourceSpec resourceSpec) {
		return R.status(vlsResourceSpecService.updateById(resourceSpec));
	}

	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Add or modify", description = "incomingresourceSpec")
	public R submit(@Valid @RequestBody ResourceSpec resourceSpec) {
		return R.status(vlsResourceSpecService.saveOrUpdate(resourceSpec));
	}

	@GetMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsResourceSpecService.deleteLogic(Func.toLongList(ids)));
	}
}
