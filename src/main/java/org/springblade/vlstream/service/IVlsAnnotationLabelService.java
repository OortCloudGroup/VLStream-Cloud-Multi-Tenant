package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AnnotationLabel;
import org.springblade.vlstream.pojo.vo.AnnotationLabelVO;
import org.springblade.vlstream.excel.VlsAnnotationLabelExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Annotation label entity class Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAnnotationLabelService extends BaseService<AnnotationLabel> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnnotationLabel query parameters
	 * @return IPage<VlsAnnotationLabelVO>
	 */
	IPage<AnnotationLabelVO> selectVlsAnnotationLabelPage(IPage<AnnotationLabelVO> page, AnnotationLabelVO vlsAnnotationLabel);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnnotationLabelExcel>
	 */
	List<VlsAnnotationLabelExcel> exportVlsAnnotationLabel(Wrapper<AnnotationLabel> queryWrapper);

	/**
	 * According to the marked itemsIDQuery tag list(Contains usage statistics)
	 *
	 * @param annotationId Label itemsID
	 * @return tag list
	 */
	List<AnnotationLabel> getByAnnotationIdWithUsageCount(Long annotationId);

	/**
	 * Create tags
	 *
	 * @param annotationId Label itemsID
	 * @param name Tag name
	 * @param color Label color
	 * @param description Tag description
	 * @return Tags created
	 */
	AnnotationLabel createLabel(Long annotationId, String name, String color, String description);

	/**
	 * renewLabel
	 *
	 * @param labelId LabelID
	 * @param name Tag name
	 * @param color Label color
	 * @param description Tag description
	 * @return Updated label
	 */
	AnnotationLabel updateLabel(Long labelId, String name, String color, String description);

	/**
	 * Delete tag
	 *
	 * @param labelId LabelID
	 * @return Is deletion successful?
	 */
	boolean deleteLabel(Long labelId);

	/**
	 * Update label usage count
	 *
	 * @param labelId LabelID
	 * @return Is the update successful?
	 */
	boolean updateUsageCount(Long labelId);

	/**
	 * Batch update tag sorting
	 *
	 * @param annotationId Label itemsID
	 * @param labelIds LabelIDlist(by sort order)
	 * @return Is the update successful?
	 */
	boolean updateSortOrder(Long annotationId, List<Long> labelIds);

	/**
	 * Search tags by name
	 *
	 * @param annotationId Label itemsID
	 * @param keyword Search keywords
	 * @return tag list
	 */
	List<AnnotationLabel> searchLabels(Long annotationId, String keyword);

}
