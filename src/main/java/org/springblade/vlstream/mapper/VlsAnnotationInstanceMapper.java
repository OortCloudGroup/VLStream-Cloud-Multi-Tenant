package org.springblade.vlstream.mapper;

import org.apache.ibatis.annotations.Select;
import org.springblade.vlstream.pojo.entity.AnnotationInstance;
import org.springblade.vlstream.pojo.vo.AnnotationInstanceVO;
import org.springblade.vlstream.excel.VlsAnnotationInstanceExcel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Label instance entity class Mapper interface
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface VlsAnnotationInstanceMapper extends BaseMapper<AnnotationInstance> {

	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnnotationInstance query parameters
	 * @return List<VlsAnnotationInstanceVO>
	 */
	List<AnnotationInstanceVO> selectVlsAnnotationInstancePage(IPage page, AnnotationInstanceVO vlsAnnotationInstance);

	/**
	 * Get export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnnotationInstanceExcel>
	 */
	List<VlsAnnotationInstanceExcel> exportVlsAnnotationInstance(@Param("ew") Wrapper<AnnotationInstance> queryWrapper);

	/**
	 * According to the marked itemsIDand image name query annotation examples
	 *
	 * @param annotationId Label itemsID
	 * @param imageId pictureid
	 * @return Label instance list
	 */
	@Select("SELECT * FROM vls_annotation_instance " +
		"WHERE annotation_id = #{annotationId} AND image_id = #{imageId} AND is_deleted = 0")
	List<AnnotationInstance> selectByAnnotationIdAndImageName(@Param("annotationId") Long annotationId,
															  @Param("imageId") String imageId);

	/**
	 * According to labelIDCount usage
	 *
	 * @param labelId LabelID
	 * @return Number of uses
	 */
	@Select("SELECT COUNT(*) FROM vls_annotation_instance " +
		"WHERE label_id = #{labelId} AND is_deleted = 0")
	Integer countByLabelId(@Param("labelId") Long labelId);

	/**
	 * According to the marked itemsIDQuery all annotation instances
	 *
	 * @param annotationId Label itemsID
	 * @return Label instance list
	 */
	@Select("SELECT * FROM vls_annotation_instance " +
		"WHERE annotation_id = #{annotationId} AND is_deleted = 0 " +
		"ORDER BY image_id, create_time")
	List<AnnotationInstance> selectByAnnotationId(@Param("annotationId") Long annotationId);

	/**
	 * According to the marked itemsIDand tagsIDQuery labeling examples
	 *
	 * @param annotationId Label itemsID
	 * @param labelId LabelID
	 * @return Label instance list
	 */
	@Select("SELECT * FROM vls_annotation_instance " +
		"WHERE annotation_id = #{annotationId} AND label_id = #{labelId} AND is_deleted = 0")
	List<AnnotationInstance> selectByAnnotationIdAndLabelId(@Param("annotationId") Long annotationId,
															@Param("labelId") Long labelId);

}
