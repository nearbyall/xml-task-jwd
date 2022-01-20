package by.epamtc.melnikov.xmltask.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.xmltask.bean.Device;
import by.epamtc.melnikov.xmltask.parser.AbstractDevicesBuilder;
import by.epamtc.melnikov.xmltask.parser.DevicesBuilderFactory;
import by.epamtc.melnikov.xmltask.validator.XSDValidator;

public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String UPLOAD_DIRECTORY = "upload";
	private static final String XSD_PATH = "xsd/devices.xsd";
	private static final String MESSAGE_PATH = "/jsp/message.jsp";
	private static final int THRESHOLD_SIZE 	= 1024 * 1024 * 3; 	// 3MB
	private static final int MAX_FILE_SIZE 		= 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 50; // 50MB
	
	private static final Logger logger = LogManager.getLogger(Controller.class);

    public Controller() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<FileItem> formItems;
		String filePath = StringUtils.EMPTY;
		String parserType = StringUtils.EMPTY;
		String uploadPath = request.getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;
		File uploadDirectory = new File(uploadPath);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
		}
		
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
		
		try {
			formItems = upload.parseRequest(request);
			for (FileItem fileItem : formItems) {
				if (fileItem.getFieldName().equals(parserType)) {
					parserType = fileItem.getString();
				}
				if (fileItem.getFieldName().equals("file")) {
					String fileName = new File(fileItem.getName()).getName();
					filePath = uploadPath + File.separator + fileName;
					File storeFile = new File(filePath);
					fileItem.write(storeFile);
				}
			}
			request.setAttribute("message", "Upload has been done successfully!");
		} catch (Exception e) {
			logger.error("File uploading error", e);
			request.setAttribute("message", "There was an error: " + e.getMessage());
		}
		
		System.out.println(parserType);
		
		XSDValidator validator = XSDValidator.getInstance();
		
		if (!validator.validate(filePath, getServletContext().getRealPath("")
				+ File.separator + XSD_PATH)) {
			request.setAttribute("result", "xml is not valid");
			getServletContext().getRequestDispatcher(MESSAGE_PATH).forward(request, response);
		} else {
			AbstractDevicesBuilder devicesBuilder = DevicesBuilderFactory.createDevicesBuilder("sax");
			devicesBuilder.buildSetDevices(filePath);
			Set<Device> devices = devicesBuilder.getDevices();
			request.setAttribute("result",  buildHTMLDevicesTable(devices));
			getServletContext().getRequestDispatcher(MESSAGE_PATH).forward(request, response);
		}
		
	}

	private String buildHTMLDevicesTable(Set<Device> devices) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<table>"
				+ "  <colgroup>\n"
				+ "    <col span=\"10\" style=\"background:Khaki\">\n"
				+ "  </colgroup>"
				+ "  <tr>"
				+ "	 	<td>Name</td>"
				+ "	 	<td>Id</td>"
				+ "	 	<td>Critical</td>"
				+ "	 	<td>Origin</td>"
				+ "	 	<td>AddDate</td>"
				+ "	 	<td>Price</td>"
				+ "	 	<td>Peripherality</td>"
				+ "	 	<td>Group</td>"
				+ "	 	<td>Port</td>"
				+ "	 	<td>Consumption</td>"
				+ "  </tr>");
		
		for (Device device : devices) {
			sb.append("<tr>"
					+ "<td>" + device.getName() + "</td>"
					+ "<td>" + device.getId() + "</td>"
					+ "<td>" + device.getCritical() + "</td>"
					+ "<td>" + device.getOrigin() + "</td>"
					+ "<td>" + device.getAddDate() + "</td>"
					+ "<td>" + device.getPrice() + "</td>"
					+ "<td>" + device.getType().getPeripherality() + "</td>"
					+ "<td>" + device.getType().getGroup() + "</td>"
					+ "<td>" + device.getType().getPort() + "</td>"
					+ "<td>" + device.getType().getConsumption() + "</td>"
					+ "</tr>");
		}
		
		sb.append("</table>");
		
		return sb.toString();
		
	}
	
}
