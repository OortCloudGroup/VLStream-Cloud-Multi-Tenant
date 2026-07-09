package org.springblade.vlstream.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import org.springblade.vlstream.pojo.entity.AlgorithmAnnotation;
import org.springblade.vlstream.pojo.vo.AlgorithmAnnotationVO;
import org.springblade.vlstream.excel.VlsAlgorithmAnnotationExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.mp.base.BaseService;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

/**
 * Algorithm annotation data table Service category
 *
 * @author Oort
 * @since 2025-12-23
 */
public interface IVlsAlgorithmAnnotationService extends BaseService<AlgorithmAnnotation> {
	/**
	 * Custom paging
	 *
	 * @param page Paging parameters
	 * @param vlsAlgorithmAnnotation query parameters
	 * @return IPage<VlsAlgorithmAnnotationVO>
	 */
	IPage<AlgorithmAnnotationVO> selectVlsAlgorithmAnnotationPage(IPage<AlgorithmAnnotationVO> page, AlgorithmAnnotationVO vlsAlgorithmAnnotation);

	/**
	 * Export data
	 *
	 * @param queryWrapper Query conditions
	 * @return List<VlsAlgorithmAnnotationExcel>
	 */
	List<VlsAlgorithmAnnotationExcel> exportVlsAlgorithmAnnotation(Wrapper<AlgorithmAnnotation> queryWrapper);

	/**
	 * Paging query algorithm annotation list
	 *
	 * @param page Paging parameters
	 * @param annotationName Label name(fuzzy query)
	 * @param annotationType Dimension type
	 * @param annotationStatus Annotation status
	 * @return Paginated results
	 */
	IPage<AlgorithmAnnotation> selectAnnotationPage(Page<AlgorithmAnnotation> page,
													String annotationName,
													String annotationType,
													String annotationStatus);

	/**
	 * Query annotation list based on annotation type
	 *
	 * @param annotationType Dimension type
	 * @return Annotation list
	 */
	List<AlgorithmAnnotation> getByAnnotationType(String annotationType);

	/**
	 * Query annotation list based on annotation status
	 *
	 * @param annotationStatus Annotation status
	 * @return Annotation list
	 */
	List<AlgorithmAnnotation> getByAnnotationStatus(String annotationStatus);

	/**
	 * Create algorithm annotations
	 *
	 * @param annotation Annotation information
	 * @return Is it successful?
	 */
	boolean createAnnotation(AlgorithmAnnotation annotation);

	/**
	 * Update algorithm annotation
	 *
	 * @param annotation Annotation information
	 * @return Is it successful?
	 */
	boolean updateAnnotation(AlgorithmAnnotation annotation);

	/**
	 * Delete algorithm annotation
	 *
	 * @param id markID
	 * @return Is it successful?
	 */
	boolean deleteAnnotation(Long id);

	/**
	 * Batch deletion of algorithm annotations
	 *
	 * @param ids markIDlist
	 * @return Is it successful?
	 */
	boolean batchDeleteAnnotations(List<Long> ids);

	/**
	 * Update labeling progress
	 *
	 * @param id markID
	 * @param annotatedCount Quantity marked
	 * @return Is it successful?
	 */
	boolean updateAnnotationProgress(Long id, Integer annotatedCount);

	/**
	 * Update annotation status in batches
	 *
	 * @param ids markIDlist
	 * @param annotationStatus New label status
	 * @return Is it successful?
	 */
	boolean batchUpdateAnnotationStatus(List<Long> ids, String annotationStatus);

	/**
	 * Start labeling task
	 *
	 * @param id markID
	 * @return Is it successful?
	 */
	boolean startAnnotationTask(Long id);

	/**
	 * Complete the labeling task
	 *
	 * @param id markID
	 * @return Is it successful?
	 */
	boolean completeAnnotationTask(Long id);

	/**
	 * Reset labeling task
	 *
	 * @param id markID
	 * @return Is it successful?
	 */
	boolean resetAnnotationTask(Long id);

	/**
	 * Import annotation data
	 *
	 * @param id markID
	 * @param dataPath data path
	 * @return Import results
	 */
	Map<String, Object> importAnnotationData(Long id, String dataPath);

	/**
	 * Import annotation dataset from zip file.
	 *
	 * @param annotationId annotation id
	 * @param zipFile zip dataset file
	 * @return import result
	 */
	Map<String, Object> importAnnotationDatasetZip(Long annotationId, MultipartFile zipFile);

	/**
	 * Get annotation type statistics
	 *
	 * @return Label type statistics
	 */
	List<Map<String, Object>> getAnnotationTypeStatistics();

	/**
	 * Get annotation status statistics
	 *
	 * @return Label status statistics
	 */
	List<Map<String, Object>> getAnnotationStatusStatistics();

	/**
	 * Get annotation progress statistics
	 *
	 * @return Mark progress statistics
	 */
	List<Map<String, Object>> getProgressStatistics();

	/**
	 * Get annotation workload statistics
	 *
	 * @return Label workload statistics
	 */
	Map<String, Object> getWorkloadStatistics();

	/**
	 * Verify annotation data
	 *
	 * @param id markID
	 * @return Verification results
	 */
	Map<String, Object> validateAnnotationData(Long id);

	/**
	 * Save annotation data to dataset file
	 *
	 * @param annotationId markID
	 * @return Is the save successful?
	 */
	boolean saveAnnotationToDataset(Long annotationId);

	/**
	 * Download the annotation dataset as a zip package.
	 *
	 * @param id       annotation id
	 * @param response http response
	 */
	void downloadAnnotationDataset(Long id, HttpServletResponse response);

}
