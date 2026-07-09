package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springblade.vlstream.pojo.dto.TagManagementDTO;
import org.springblade.vlstream.pojo.entity.TagManagement;
import org.springblade.vlstream.pojo.vo.TagManagementVO;
import org.springblade.vlstream.excel.VlsTagManagementExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Tag management table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsTagManagementService extends BaseService<TagManagement> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsTagManagement query parameters
	 * @return IPage<VlsTagManagementVO>
	 */
	IPage<TagManagementVO> selectVlsTagManagementPage(IPage<TagManagementVO> page, TagManagementVO vlsTagManagement);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsTagManagementExcel>
	 */
	List<VlsTagManagementExcel> exportVlsTagManagement(Wrapper<TagManagement> queryWrapper);

	/**
	 * Get tag tree structure
	 *
	 * @return tag tree
	 */
	List<TagManagementDTO> getTagTree();

	/**
	 * Get tag tree based on type
	 *
	 * @param tagType Tag type(own-own, public-public)
	 * @return tag tree
	 */
	List<TagManagementDTO> getTagTreeByType(String tagType);

	/**
	 * Create tags
	 *
	 * @param tagManagement Label information
	 * @return Created successful label
	 */
	TagManagement createTag(TagManagement tagManagement);

	/**
	 * renewLabel
	 *
	 * @param tagManagement Label information
	 * @return Tags updated successfully
	 */
	TagManagement updateTag(TagManagement tagManagement);

	/**
	 * Delete tag(Cascading subtag deletion)
	 *
	 * @param tagId LabelID
	 * @return Is deletion successful?
	 */
	boolean deleteTag(Long tagId);

	/**
	 * Delete tags in batches
	 *
	 * @param tagIds LabelIDlist
	 * @return Is deletion successful?
	 */
	boolean deleteTags(List<Long> tagIds);

	/**
	 * Move label(Adjust parent or sort)
	 *
	 * @param tagId LabelID
	 * @param targetParentId target parentID
	 * @param targetPosition Target location
	 * @return Whether the move was successful
	 */
	boolean moveTag(Long tagId, Long targetParentId, Integer targetPosition);

	/**
	 * Update label usage count
	 *
	 * @param tagId LabelID
	 * @param increment increased times
	 */
	void updateUsageCount(Long tagId, Integer increment);

	/**
	 * Check if tag name is duplicated(Under the same level)
	 *
	 * @param tagName Tag name
	 * @param parentId parentID
	 * @param excludeId excludedID(Used for validation while editing)
	 * @return Whether to repeat
	 */
	boolean isTagNameDuplicate(String tagName, Long parentId, Long excludeId);

	/**
	 * enable/Disable tag
	 *
	 * @param tagId LabelID
	 * @param isActive Whether to enable
	 * @return Whether the operation was successful
	 */
	boolean toggleTagStatus(Long tagId, boolean isActive);

	/**
	 * Get tag usage statistics
	 *
	 * @param tagId LabelID
	 * @return usage statistics
	 */
	TagManagement getTagUsageStats(Long tagId);

	/**
	 * Get tags based on classification type
	 *
	 * @param categoryType Classification type(own-own, public-public)
	 * @return tag list
	 */
	List<TagManagement> getTagsByCategory(String categoryType);

	/**
	 * Paginated query tag management
	 *
	 * @param page Paging parameters
	 * @param keyword Search keywords
	 * @param categoryType Tag categories
	 * @param level Tag hierarchy
	 * @param parentId parentID
	 * @param tagId LabelID
	 * @return Paginated results
	 */
	IPage<TagManagement> getTagManagementPage(Page<TagManagement> page, String keyword, String categoryType, Integer level, Long parentId, Long tagId);

}
