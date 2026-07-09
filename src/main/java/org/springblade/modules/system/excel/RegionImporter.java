package org.springblade.modules.system.excel;

import lombok.RequiredArgsConstructor;
import org.springblade.core.excel.support.ExcelImporter;
import org.springblade.modules.system.service.IRegionService;

import java.util.List;

/**
 * Administrative division data import class
 *
 * @author Chill
 */
@RequiredArgsConstructor
public class RegionImporter implements ExcelImporter<RegionExcel> {

	private final IRegionService service;
	private final Boolean isCovered;

	@Override
	public void save(List<RegionExcel> data) {
		service.importRegion(data, isCovered);
	}
}
