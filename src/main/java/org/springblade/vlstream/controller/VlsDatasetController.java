package org.springblade.vlstream.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.api.R;
import org.springblade.vlstream.service.DatasetService;
import org.springframework.web.bind.annotation.*;

/**
 * Dataset Management Controller
 *
 * @author VLStream Team
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/dataset")
@RequiredArgsConstructor
@Api(tags = "Dataset management")
public class VlsDatasetController {

	private final DatasetService datasetService;

	/**
	 * Connect to remote server
	 */
	@PostMapping("/connect")
	@Operation(summary = "Connect to remote server", description = "Connect to the remote server and verify the connection status")
	public R<String> connectToServer(
		@Parameter(description = "Server address") @RequestParam String host,
		@Parameter(description = "username") @RequestParam String username,
		@Parameter(description = "password") @RequestParam String password,
		@Parameter(description = "Dataset path") @RequestParam String path) {

		log.info("Connect to remote server: host={}, username={}, path={}", host, username, path);

		try {
			boolean connected = datasetService.connectToServer(host, username, password, path);
			if (connected) {
				return R.success("Server connection successful");
			} else {
				return R.fail("Server connection failed");
			}
		} catch (Exception e) {
			log.error("Failed to connect to server: ", e);
			return R.fail("Failed to connect to server: " + e.getMessage());
		}
	}

	/**
	 * Get the list of dataset files
	 */
	@GetMapping("/files")
	@Operation(summary = "Get file list", description = "Get the file list under the specified path of the remote server")
	public R<Object> getDatasetFiles(
		@Parameter(description = "Server address") @RequestParam String host,
		@Parameter(description = "Dataset path") @RequestParam String path) {

		log.info("Get the list of dataset files: host={}, path={}", host, path);

		try {
			Object files = datasetService.getDatasetFiles(host, path);
			return R.data(files);
		} catch (Exception e) {
			log.error("Failed to get file list: ", e);
			return R.fail("Failed to get file list: " + e.getMessage());
		}
	}

	/**
	 * Get file content
	 */
	@GetMapping("/file-content")
	@Operation(summary = "Get file content", description = "Get the contents of the specified file on the remote server")
	public R<String> getFileContent(
		@Parameter(description = "Server address") @RequestParam String host,
		@Parameter(description = "Dataset path") @RequestParam String path,
		@Parameter(description = "file name") @RequestParam String filename) {

		log.info("Get file content: host={}, path={}, filename={}", host, path, filename);

		try {
			String content = datasetService.getFileContent(host, path, filename);
			return R.success(content);
		} catch (Exception e) {
			log.error("Failed to get file content: ", e);
			return R.fail("Failed to get file content: " + e.getMessage());
		}
	}

	/**
	 * Download file
	 */
	@GetMapping("/download")
	@Operation(summary = "Download file", description = "Download specified file from remote server")
	public void downloadFile(
		@Parameter(description = "Server address") @RequestParam String host,
		@Parameter(description = "Dataset path") @RequestParam String path,
		@Parameter(description = "file name") @RequestParam String filename,
		HttpServletResponse response) {

		log.info("Download file: host={}, path={}, filename={}", host, path, filename);

		try {
			datasetService.downloadFile(host, path, filename, response);
		} catch (Exception e) {
			log.error("Download file failed: ", e);
			try {
				response.setStatus(500);
				response.getWriter().write("Download file failed: " + e.getMessage());
			} catch (Exception ex) {
				log.error("Writing error response failed: ", ex);
			}
		}
	}
}
