package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.enums.AlgorithmAnnotationTypeEnum;
import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import org.springblade.vlstream.pojo.vo.AnnotationInstanceVO;
import org.springblade.vlstream.excel.VlsAnnotationInstanceExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import java.util.List;

/**
 * Label instance entity class Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAnnotationInstanceService extends BaseService<AnnotationInstance> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnnotationInstance query parameters
	 * @return IPage<VlsAnnotationInstanceVO>
	 */
	IPage<AnnotationInstanceVO> selectVlsAnnotationInstancePage(IPage<AnnotationInstanceVO> page, AnnotationInstanceVO vlsAnnotationInstance);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnnotationInstanceExcel>
	 */
	List<VlsAnnotationInstanceExcel> exportVlsAnnotationInstance(Wrapper<AnnotationInstance> queryWrapper);

	/**
	 * According to the marked itemsIDand image name query annotation examples
	 *
	 * @param annotationId Label itemsID
	 * @param imageName Picture name
	 * @return Label instance list
	 */
	List<AnnotationInstance> getByAnnotationIdAndImageName(Long annotationId, String imageName);

	/**
	 * Save annotation instance
	 *
	 * @param annotationId Label itemsID
	 * @param labelId LabelID
	 * @param imageId pictureid
	 * @param annotationType Dimension type
	 * @param annotationData Label data(JSONFormat)
	 * @return Saved callout instance
	 */
	AnnotationInstance saveAnnotation(Long annotationId, Long labelId, Long imageId, AlgorithmAnnotationTypeEnum annotationType, String annotationData);

	/**
	 * Update callout instance
	 *
	 * @param instanceId ExampleID
	 * @param labelId LabelID
	 * @param annotationType Dimension type
	 * @param annotationData Label data(JSONFormat)
	 * @return Updated annotation example
	 */
	AnnotationInstance updateAnnotation(Long instanceId, Long labelId,
										AlgorithmAnnotationTypeEnum annotationType, String annotationData);

	/**
	 * Delete annotation instance
	 *
	 * @param instanceId ExampleID
	 * @return Is deletion successful?
	 */
	boolean deleteAnnotation(Long instanceId);

	/**
	 * Save labeling instances in batches
	 *
	 * @param annotationId Label itemsID
	 * @param imageId pictureID
	 * @param annotations Label instance list
	 * @return Is the save successful?
	 */
	boolean batchSaveAnnotations(Long annotationId, Long imageId, List<AnnotationInstance> annotations);

	/**
	 * According to the marked itemsIDQuery all annotation instances
	 *
	 * @param annotationId Label itemsID
	 * @return Label instance list
	 */
	List<AnnotationInstance> getByAnnotationId(Long annotationId);

	/**
	 * According to labelIDCount usage
	 *
	 * @param labelId LabelID
	 * @return Number of uses
	 */
	Integer countByLabelId(Long labelId);

	/**
	 * Delete the image and all data associated with it
	 * include: annotation_image、annotation_instance、renewannotation_labelusage count
	 *
	 * @param annotationId Label itemsID
	 * @param imageId pictureID
	 * @return Delete results
	 */
	boolean deleteImageAndRelatedData(Long annotationId, Long imageId);

}
