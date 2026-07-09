package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.springblade.vlstream.pojo.entity.AnnotationImage;
import org.springblade.vlstream.pojo.vo.AnnotationImageVO;
import org.springblade.vlstream.excel.VlsAnnotationImageExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Label image information table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAnnotationImageService extends BaseService<AnnotationImage> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAnnotationImage query parameters
	 * @return IPage<VlsAnnotationImageVO>
	 */
	IPage<AnnotationImageVO> selectVlsAnnotationImagePage(IPage<AnnotationImageVO> page, AnnotationImageVO vlsAnnotationImage);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAnnotationImageExcel>
	 */
	List<VlsAnnotationImageExcel> exportVlsAnnotationImage(Wrapper<AnnotationImage> queryWrapper);

	/**
	 * Upload pictures
	 */
	List<AnnotationImage> uploadImages(MultipartFile[] files, Long annotationId);

	/**
	 * According to the data setIDGet picture list
	 */
	List<AnnotationImage> getImagesByDataset(Long annotationId);

	/**
	 * according toIDGet image details
	 */
	AnnotationImage getImageById(Long id);

	/**
	 * Update picture information
	 */
	AnnotationImage updateImage(AnnotationImage image);

	/**
	 * Delete picture
	 */
	void deleteImage(Long id);

	/**
	 * Delete pictures in batches
	 */
	void batchDeleteImages(List<Long> ids);

	/**
	 * Get dataset statistics
	 */
	Map<String, Object> getDatasetStats(Long datasetId);

	/**
	 * Save image information toannotation_imagesurface
	 */
	boolean saveImage(AnnotationImage annotationImage);

	/**
	 * Save image information in batches toannotation_imagesurface
	 */
	boolean batchSaveImages(List<AnnotationImage> annotationImages);

	/**
	 * According to the marked itemsIDGet picture list
	 */
	List<AnnotationImage> getImagesByAnnotationId(Long annotationId);

}
