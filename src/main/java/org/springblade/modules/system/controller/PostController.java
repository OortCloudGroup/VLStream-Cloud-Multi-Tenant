package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.pojo.entity.Post;
import org.springblade.modules.system.service.IPostService;
import org.springblade.modules.system.pojo.vo.PostVO;
import org.springblade.modules.system.wrapper.PostWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * Job list controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@PreAuth(menu = "post")
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/post")
@Tag(name = "post", description = "post")
public class PostController extends BladeController {

	private final IPostService postService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingpost")
	public R<PostVO> detail(Post post) {
		Post detail = postService.getOne(Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().entityVO(detail));
	}

	/**
	 * Pagination Job list
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description = "incomingpost")
	public R<IPage<PostVO>> list(Post post, Query query) {
		IPage<Post> pages = postService.page(Condition.getPage(query), Condition.getQueryWrapper(post));
		return R.data(PostWrapper.build().pageVO(pages));
	}


	/**
	 * Custom paging Job list
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description = "incomingpost")
	public R<IPage<PostVO>> page(PostVO post, Query query) {
		IPage<PostVO> pages = postService.selectPostPage(Condition.getPage(query), post);
		return R.data(pages);
	}

	/**
	 * New Job list
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description = "incomingpost")
	public R save(@Valid @RequestBody Post post) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(postService.save(post));
	}

	/**
	 * Revise Job list
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description = "incomingpost")
	public R update(@Valid @RequestBody Post post) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(postService.updateById(post));
	}

	/**
	 * Add or modify Job list
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "incomingpost")
	public R submit(@Valid @RequestBody Post post) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(postService.saveOrUpdate(post));
	}


	/**
	 * delete Job list
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(postService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Drop down data source
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Drop down data source", description = "incomingpost")
	public R<List<Post>> select(String tenantId, BladeUser bladeUser) {
		List<Post> list = postService.list(Wrappers.<Post>query().lambda().eq(Post::getTenantId, Func.toStrWithEmpty(tenantId, bladeUser.getTenantId())));
		return R.data(list);
	}

}
