package org.springblade.modules.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.pojo.entity.Dict;
import org.springblade.modules.system.service.IDictService;
import org.springblade.modules.system.pojo.vo.DictVO;
import org.springblade.modules.system.wrapper.DictWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * controller
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_SYSTEM_NAME + "/dict")
@Tag(name = "system dictionary", description = "system dictionary")
public class DictController extends BladeController {

	private final IDictService dictService;

	/**
	 * Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description = "incomingdict")
	public R<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return R.data(DictWrapper.build().entityVO(detail));
	}

	/**
	 * list
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "code", description = "dictionary number", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "dictValue", description = "Dictionary name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "list", description = "incomingdict")
	public R<List<DictVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> dict) {
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		return R.data(DictWrapper.build().listNodeVO(list));
	}

	/**
	 * top list
	 */
	@GetMapping("/parent-list")
	@Parameters({
		@Parameter(name = "code", description = "dictionary number", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "dictValue", description = "Dictionary name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 3)
	@Operation(summary = "list", description = "incomingdict")
	public R<IPage<DictVO>> parentList(@Parameter(hidden = true) @RequestParam Map<String, Object> dict, Query query) {
		return R.data(dictService.parentList(dict, query));
	}

	/**
	 * sublist
	 */
	@GetMapping("/child-list")
	@Parameters({
		@Parameter(name = "code", description = "dictionary number", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "dictValue", description = "Dictionary name", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "parentId", description = "Dictionary name", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 4)
	@Operation(summary = "list", description = "incomingdict")
	public R<List<DictVO>> childList(@Parameter(hidden = true) @RequestParam Map<String, Object> dict, @RequestParam(required = false, defaultValue = "-1") Long parentId) {
		return R.data(dictService.childList(dict, parentId));
	}

	/**
	 * Get dictionary tree structure
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "tree structure", description = "tree structure")
	public R<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return R.data(tree);
	}

	/**
	 * Get dictionary tree structure
	 */
	@GetMapping("/parent-tree")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "tree structure", description = "tree structure")
	public R<List<DictVO>> parentTree() {
		List<DictVO> tree = dictService.parentTree();
		return R.data(tree);
	}

	/**
	 * Add or modify
	 */
	@PreAuth(menu = "dict")
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description = "Add or modify")
	public R submit(@Valid @RequestBody Dict dict) {
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		return R.status(dictService.submit(dict));
	}


	/**
	 * delete
	 */
	@PreAuth(menu = "dict")
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "delete", description = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		return R.status(dictService.removeDict(ids));
	}

	/**
	 * Get dictionary
	 */
	@GetMapping("/dictionary")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Get dictionary", description = "Get dictionary")
	public R<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(tree);
	}

	/**
	 * Get dictionary
	 */
	@GetMapping("/dictionary-full")
	@ApiOperationSupport(order = 9)
	@Operation(summary = "Get dictionary", description = "Get dictionary")
	public R<List<Dict>> dictionaryFull(String code) {
		List<Dict> tree = dictService.getList(code);
		tree.forEach(dict -> dict.setDictValue(dict.getDictKey() + StringPool.SPACE + StringPool.COLON + StringPool.SPACE + dict.getDictValue()));
		return R.data(tree);
	}

	/**
	 * Get dictionary tree
	 */
	@GetMapping("/dictionary-tree")
	@ApiOperationSupport(order = 10)
	@Operation(summary = "Get dictionary tree", description = "Get dictionary tree")
	public R<List<DictVO>> dictionaryTree(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(DictWrapper.build().listNodeVO(tree));
	}

	/**
	 * List of dictionary keys
	 */
	@GetMapping("/select")
	@ApiOperationSupport(order = 11)
	@Operation(summary = "List of dictionary keys", description = "List of dictionary keys")
	public R<List<Dict>> select() {
		List<Dict> list = dictService.list(Wrappers.<Dict>query().lambda().eq(Dict::getParentId, CommonConstant.TOP_PARENT_ID));
		list.forEach(dict -> dict.setDictValue(dict.getCode() + StringPool.COLON + StringPool.SPACE + dict.getDictValue()));
		return R.data(list);
	}

	/**
	 * Full list of dictionaries
	 */
	@GetMapping("/select-all")
	@ApiOperationSupport(order = 12)
	@Operation(summary = "Full list of dictionaries", description = "Full list of dictionaries")
	public R<List<Dict>> selectAll() {
		List<Dict> list = dictService.list(Wrappers.<Dict>query().lambda().eq(Dict::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		return R.data(list);
	}

}
