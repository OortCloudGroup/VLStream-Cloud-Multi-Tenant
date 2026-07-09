package org.springblade.vlstream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.annotation.IsAdmin;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.vlstream.excel.VlsTagManagementExcel;
import org.springblade.vlstream.pojo.dto.TagManagementDTO;
import org.springblade.vlstream.pojo.entity.TagManagement;
import org.springblade.vlstream.pojo.vo.TagManagementVO;
import org.springblade.vlstream.service.IVlsTagManagementService;
import org.springblade.vlstream.wrapper.VlsTagManagementWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tag management table controller
 *
 * @author Oort
 * @since 2025-12-23
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/vlsTagManagement")
@Tag(name = "Tag management table", description = "Tag management table interface")
public class VlsTagManagementController extends BladeController {

	private final IVlsTagManagementService vlsTagManagementService;

	/**
	 * Tag management table Details
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "Details", description  = "incomingvlsTagManagement")
	public R<TagManagementVO> detail(TagManagement vlsTagManagement) {
		TagManagement detail = vlsTagManagementService.getOne(Condition.getQueryWrapper(vlsTagManagement));
		return R.data(VlsTagManagementWrapper.build().entityVO(detail));
	}

	/**
	 * Tag management table Pagination
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "Pagination", description  = "incomingvlsTagManagement")
	public R<IPage<TagManagementVO>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsTagManagement, Query query) {
		IPage<TagManagement> pages = vlsTagManagementService.page(Condition.getPage(query), Condition.getQueryWrapper(vlsTagManagement, TagManagement.class));
		return R.data(VlsTagManagementWrapper.build().pageVO(pages));
	}


	/**
	 * Tag management table Custom paging
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "Pagination", description  = "incomingvlsTagManagement")
	public R<IPage<TagManagementVO>> page(TagManagementVO vlsTagManagement, Query query) {
		IPage<TagManagementVO> pages = vlsTagManagementService.selectVlsTagManagementPage(Condition.getPage(query), vlsTagManagement);
		return R.data(pages);
	}

	/**
	 * Tag management table New
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "New", description  = "incomingvlsTagManagement")
	public R save(@Valid @RequestBody TagManagement vlsTagManagement) {
		return R.status(vlsTagManagementService.save(vlsTagManagement));
	}

	/**
	 * Tag management table Revise
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "Revise", description  = "incomingvlsTagManagement")
	public R update(@Valid @RequestBody TagManagement vlsTagManagement) {
		return R.status(vlsTagManagementService.updateById(vlsTagManagement));
	}

	/**
	 * Tag management table Add or modify
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "Add or modify", description  = "incomingvlsTagManagement")
	public R submit(@Valid @RequestBody TagManagement vlsTagManagement) {
		return R.status(vlsTagManagementService.saveOrUpdate(vlsTagManagement));
	}

	/**
	 * Tag management table delete
	 */
	@GetMapping("/remove")
	@ApiOperationSupport(order = 7)
	@Operation(summary = "tombstone", description  = "incomingids")
	public R remove(@Parameter(description = "primary key set", required = true) @RequestParam String ids) {
		return R.status(vlsTagManagementService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * Export data
	 */
	@IsAdmin
	@GetMapping("/export-vlsTagManagement")
	@ApiOperationSupport(order = 8)
	@Operation(summary = "Export data", description  = "incomingvlsTagManagement")
	public void exportVlsTagManagement(@Parameter(hidden = true) @RequestParam Map<String, Object> vlsTagManagement, BladeUser bladeUser, HttpServletResponse response) {
		QueryWrapper<TagManagement> queryWrapper = Condition.getQueryWrapper(vlsTagManagement, TagManagement.class);
		//if (!AuthUtil.isAdministrator()) {
		//	queryWrapper.lambda().eq(VlsTagManagementEntity::getTenantId, bladeUser.getTenantId());
		//}
		//queryWrapper.lambda().eq(VlsTagManagementEntity::getIsDeleted, BladeConstant.DB_NOT_DELETED);
		List<VlsTagManagementExcel> list = vlsTagManagementService.exportVlsTagManagement(queryWrapper);
		ExcelUtil.export(response, "Tag management table data" + DateUtil.time(), "Tag management table data table", list, VlsTagManagementExcel.class);
	}

	@GetMapping("/tree")
	@ApiOperation("Get tag tree structure(for left navigation)")
	public R<List<Map<String, Object>>> getTagTree() {
		try {
			// Get private label and public label data
			List<TagManagement> ownTags = vlsTagManagementService.getTagsByCategory("own");
			List<TagManagement> publicTags = vlsTagManagementService.getTagsByCategory("public");

			// Build return structure
			Map<String, Object> result = new HashMap<>();

			// Build your own tag tree structure
			Map<String, Object> ownRoot = new HashMap<>();
			ownRoot.put("id", "own");
			ownRoot.put("tagName", "private label");
			ownRoot.put("categoryType", "own");
			ownRoot.put("level", 0);
			ownRoot.put("children", buildTreeStructure(ownTags));

			// Build a public tag tree structure
			Map<String, Object> publicRoot = new HashMap<>();
			publicRoot.put("id", "public");
			publicRoot.put("tagName", "public tags");
			publicRoot.put("categoryType", "public");
			publicRoot.put("level", 0);
			publicRoot.put("children", buildTreeStructure(publicTags));

			List<Map<String, Object>> data = new ArrayList<>();
			data.add(ownRoot);
			data.add(publicRoot);

			return R.data(data);
		} catch (Exception e) {
			log.error("Failed to get tag tree", e);
			return R.fail("Failed to get tag tree: " + e.getMessage());
		}
	}

	/**
	 * Build a tree structure
	 */
	private List<Map<String, Object>> buildTreeStructure(List<TagManagement> tags) {
		List<Map<String, Object>> result = new ArrayList<>();

		// Get all label types(level=1)
		List<TagManagement> categories = tags.stream()
			.filter(tag -> tag.getLevel() == 1)
			.sorted(Comparator.comparing(TagManagement::getSortOrder))
			.collect(Collectors.toList());

		// Build subtags for each tag type
		for (TagManagement category : categories) {
			Map<String, Object> categoryNode = new HashMap<>();
			categoryNode.put("id", category.getId());
			categoryNode.put("tagName", category.getTagName());
			categoryNode.put("categoryType", category.getCategoryType());
			categoryNode.put("level", category.getLevel());
			categoryNode.put("parentId", category.getParentId());
			categoryNode.put("sortOrder", category.getSortOrder());
			categoryNode.put("tagColor", category.getTagColor());
			categoryNode.put("tagIcon", category.getTagIcon());
			categoryNode.put("description", category.getDescription());
			categoryNode.put("isActive", category.getIsActive());
			categoryNode.put("usageCount", category.getUsageCount());
			categoryNode.put("createTime", category.getCreateTime());
			categoryNode.put("updateTime", category.getUpdateTime());

			// Get all specific tags under this type
			List<TagManagement> subTags = tags.stream()
				.filter(tag -> tag.getLevel() == 2 && Objects.equals(tag.getParentId(), category.getId()))
				.sorted(Comparator.comparing(TagManagement::getSortOrder))
				.collect(Collectors.toList());

			List<Map<String, Object>> children = new ArrayList<>();
			for (TagManagement subTag : subTags) {
				Map<String, Object> subNode = new HashMap<>();
				subNode.put("id", subTag.getId());
				subNode.put("tagName", subTag.getTagName());
				subNode.put("categoryType", subTag.getCategoryType());
				subNode.put("level", subTag.getLevel());
				subNode.put("parentId", subTag.getParentId());
				subNode.put("sortOrder", subTag.getSortOrder());
				subNode.put("tagColor", subTag.getTagColor());
				subNode.put("tagIcon", subTag.getTagIcon());
				subNode.put("description", subTag.getDescription());
				subNode.put("isActive", subTag.getIsActive());
				subNode.put("usageCount", subTag.getUsageCount());
				subNode.put("createTime", subTag.getCreateTime());
				subNode.put("updateTime", subTag.getUpdateTime());
				subNode.put("parentName", category.getTagName());

				children.add(subNode);
			}

			categoryNode.put("children", children);
			result.add(categoryNode);
		}

		return result;
	}

	@GetMapping("/tree/{tagType}")
	@ApiOperation("Get tag tree based on type")
	public R<List<TagManagementDTO>> getTagTreeByType(@ApiParam(value = "Tag type(own-own, public-public)", required = true) @PathVariable String tagType) {
		try {
			List<TagManagementDTO> tagTree = vlsTagManagementService.getTagTreeByType(tagType);
			return R.data(tagTree);
		} catch (Exception e) {
			log.error("Failed to get tag tree, type: {}", tagType, e);
			return R.fail("Failed to get tag tree: " + e.getMessage());
		}
	}

	@PostMapping
	@ApiOperation("Create tags")
	public R<TagManagement> createTag(@RequestBody TagManagement tagManagement) {
		try {
			TagManagement createdTag = vlsTagManagementService.createTag(tagManagement);
			return R.data(createdTag);
		} catch (Exception e) {
			log.error("Failed to create label", e);
			return R.fail("Failed to create label: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	@ApiOperation("renewLabel")
	public R<TagManagement> updateTag(
		@ApiParam(value = "LabelID", required = true) @PathVariable Long id,
		@RequestBody TagManagement tagManagement) {
		try {
			tagManagement.setId(id);
			TagManagement updatedTag = vlsTagManagementService.updateTag(tagManagement);
			return R.data(updatedTag);
		} catch (Exception e) {
			log.error("Failed to update label, ID: {}", id, e);
			return R.fail("Failed to update label: " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Delete tag")
	public R<Void> deleteTag(
		@ApiParam(value = "LabelID", required = true) @PathVariable Long id) {
		try {
			boolean success = vlsTagManagementService.deleteTag(id);
			if (success) {
				return R.success();
			} else {
				return R.fail("Failed to delete tag");
			}
		} catch (Exception e) {
			log.error("Failed to delete tag, ID: {}", id, e);
			return R.fail("Failed to delete tag: " + e.getMessage());
		}
	}

	@DeleteMapping("/batch")
	@ApiOperation("Delete tags in batches")
	public R<Void> deleteTags(@RequestBody List<Long> tagIds) {
		try {
			boolean success = vlsTagManagementService.deleteTags(tagIds);
			if (success) {
				return R.success();
			} else {
				return R.fail("Failed to delete tags in batches");
			}
		} catch (Exception e) {
			log.error("Failed to delete tags in batches, IDs: {}", tagIds, e);
			return R.fail("Failed to delete tags in batches: " + e.getMessage());
		}
	}

	@PutMapping("/{id}/move")
	@ApiOperation("Move label")
	public R<Void> moveTag(
		@ApiParam(value = "LabelID", required = true) @PathVariable Long id,
		@ApiParam(value = "target parentID") @RequestParam(required = false) Long targetParentId,
		@ApiParam(value = "Target location") @RequestParam(required = false) Integer targetPosition) {
		try {
			boolean success = vlsTagManagementService.moveTag(id, targetParentId, targetPosition);
			if (success) {
				return R.success();
			} else {
				return R.fail("Failed to move label");
			}
		} catch (Exception e) {
			log.error("Failed to move label, ID: {}", id, e);
			return R.fail("Failed to move label: " + e.getMessage());
		}
	}

	@PutMapping("/{id}/toggle-status")
	@ApiOperation("enable/Disable tag")
	public R<Void> toggleTagStatus(
		@ApiParam(value = "LabelID", required = true) @PathVariable Long id,
		@ApiParam(value = "Whether to enable", required = true) @RequestParam boolean isActive) {
		try {
			boolean success = vlsTagManagementService.toggleTagStatus(id, isActive);
			if (success) {
				return R.success();
			} else {
				return R.fail("Failed to switch label status");
			}
		} catch (Exception e) {
			log.error("Failed to switch label status, ID: {}", id, e);
			return R.fail("Failed to switch label status: " + e.getMessage());
		}
	}

	@GetMapping("/{id}/stats")
	@ApiOperation("Get tag usage statistics")
	public R<TagManagement> getTagUsageStats(
		@ApiParam(value = "LabelID", required = true) @PathVariable Long id) {
		try {
			TagManagement tagStats = vlsTagManagementService.getTagUsageStats(id);
			return R.data(tagStats);
		} catch (Exception e) {
			log.error("Failed to obtain tag statistics, ID: {}", id, e);
			return R.fail("Failed to obtain tag statistics: " + e.getMessage());
		}
	}

	@GetMapping("/check-name")
	@ApiOperation("Check if tag name is duplicated")
	public R<Boolean> checkTagName(
		@ApiParam(value = "Tag name", required = true) @RequestParam String tagName,
		@ApiParam(value = "parentID") @RequestParam(required = false) Long parentId,
		@ApiParam(value = "excludedID") @RequestParam(required = false) Long excludeId) {
		try {
			boolean isDuplicate = vlsTagManagementService.isTagNameDuplicate(tagName, parentId, excludeId);
			return R.data(isDuplicate);
		} catch (Exception e) {
			log.error("Checking tag name failed", e);
			return R.fail("Checking tag name failed: " + e.getMessage());
		}
	}
}
