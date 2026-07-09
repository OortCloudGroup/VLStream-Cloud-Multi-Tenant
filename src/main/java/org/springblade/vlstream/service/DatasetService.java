package org.springblade.vlstream.service;


import jakarta.servlet.http.HttpServletResponse;

/**
 * Dataset service interface
 *
 * @author VLStream Team
 * @since 1.0.0
 */
public interface DatasetService {

	/**
	 * Connect to remote server
	 *
	 * @param host     Server address
	 * @param username username
	 * @param password password
	 * @param path     Dataset path
	 * @return Is the connection successful?
	 */
	boolean connectToServer(String host, String username, String password, String path);

	/**
	 * Get the list of dataset files
	 *
	 * @param host Server address
	 * @param path Dataset path
	 * @return file list
	 */
	Object getDatasetFiles(String host, String path);

	/**
	 * Get file content
	 *
	 * @param host     Server address
	 * @param path     Dataset path
	 * @param filename file name
	 * @return File content
	 */
	String getFileContent(String host, String path, String filename);

	/**
	 * Download file
	 *
	 * @param host     Server address
	 * @param path     Dataset path
	 * @param filename file name
	 * @param response HTTPresponse object
	 */
	void downloadFile(String host, String path, String filename, HttpServletResponse response);
}
