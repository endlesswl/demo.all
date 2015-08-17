package com.lingcaibao.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lingcaibao.entity.Result;
import com.palm.commom.uitl.FileResult;
import com.palm.commom.uitl.FileuploadUtil;
import com.palm.lingcai.api.Image;
@Controller
@RequestMapping(value = "/fileupload")
public class FileuploadController
{
	private static Logger	logger		= LoggerFactory.getLogger(FileuploadController.class);
	private String			temp		= "/uploadfile";
	private long			fileMaxSize	= 1024000;
	private String			errorMsg	= "{\"resultCode\":\"50001\",\"retultMsg\":\"不是multipart/form-data类型\"}";
	private String			otherMsg	= "{\"resultCode\":\"50002\",\"retultMsg\":\"请选择文件\"}";
	private static Map<String, Object>	ImgSuf	= Maps.newHashMap();
	{
		ImgSuf.put(".bmp", "");
		ImgSuf.put(".jpg", "");
		ImgSuf.put(".jpeg", "");
		ImgSuf.put(".tiff", "");
		ImgSuf.put(".gif", "");
		ImgSuf.put(".pcx", "");
		ImgSuf.put(".tga", "");
		ImgSuf.put(".exif", "");
		ImgSuf.put(".fpx", "");
		ImgSuf.put(".svg", "");
		ImgSuf.put(".psd", "");
		ImgSuf.put(".cdr", "");
		ImgSuf.put(".pcd", "");
		ImgSuf.put(".dxf", "");
		ImgSuf.put(".ufo", "");
		ImgSuf.put(".eps", "");
		ImgSuf.put(".ai", "");
		ImgSuf.put(".raw", "");
		ImgSuf.put(".png", "");
	}
	@Autowired
	private Image			imageFtpApi;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		logger.info("---file upload --:{}");
		@SuppressWarnings("deprecation")
		String realPath = request.getRealPath("/");
		StringBuilder uploadTemp = new StringBuilder();
		uploadTemp.append(realPath).append(temp);
		boolean multipartContent = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart
		InputStream inputStream = null;
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = dateFormat.format(date);
		StringBuilder uploadDir = new StringBuilder();
		uploadDir.append(uploadTemp.toString()).append("/").append(year).append("/").append(month).append("/");
		try
		{
			if (multipartContent)
			{
				DiskFileItemFactory factory = new DiskFileItemFactory();//
				factory.setSizeThreshold(4096);// 设置缓冲区大小，这里是4kb
				//factory.setRepository(tempPathFile);// 设置缓冲区目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(fileMaxSize);// 设置上传文件大小
				List<FileItem> items = upload.parseRequest(request);// 获取所有文件
				Iterator<FileItem> i = items.iterator();
				List<FileResult> fileList = new ArrayList<FileResult>();
				while (i.hasNext())
				{
					FileItem fileItem = i.next();
					String fileName = fileItem.getName();
					if (fileName != null && !fileName.equals(""))
					{
						String subStr = fileName.substring(fileName.lastIndexOf("."), fileName.length());
						int random = new Random().nextInt(10000);
						inputStream = fileItem.getInputStream();
						uploadDir.append(format).append("_").append(random).append(subStr);
						File file = new File(uploadDir.toString());
						FileResult fileResult = new FileResult();
						fileResult.setFileName(file.getName());
						String filePath = file.getAbsolutePath().substring(realPath.length(), file.getAbsolutePath().length());
						filePath = filePath.replace('\\', '/');
						fileResult.setFilePath(filePath);
						FileUtils.copyInputStreamToFile(inputStream, file);
						fileList.add(fileResult);
						logger.info("----upload file result-----:{}", JSON.toJSONString(fileList));
					}
				}
				if (fileList != null && !fileList.isEmpty())
				{
					return returnOKMsg(fileList);
				} else
				{
					return otherMsg;
				}
			} else
			{
				return errorMsg;
			}
		} catch (Exception e)
		{
			logger.error("uploadFile()", e);
		} finally
		{
			IOUtils.closeQuietly(inputStream);
		}
		return errorMsg;
	}

	@RequestMapping(value = "/uploadRu", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFileRU(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Result result;
		logger.debug("图片上传.........................");
		String path = FileuploadUtil.uploadFile(request);
		logger.debug("path" + path);
		JSONObject json = JSONObject.parseObject(path);
		if (path.lastIndexOf(".") != -1) {
			String subStr = path.substring(path.lastIndexOf("."), path.length()).replace("\"}]}", "");
			if (ImgSuf.get(subStr) == null) {
				result = new Result(false, "请上传正确图片格式？", null);
				return JSON.toJSONString(result);
			}
		}
		if (!StringUtils.equals(json.getString("resultCode"), "0")) {
			result = new Result(false, json.getString("retultMsg"), null);
			return JSON.toJSONString(result);
		}
		JSONArray jsonArray = json.getJSONArray("retultMsg");
		JSONObject info = jsonArray.getJSONObject(0);
		logger.debug("info:{}", info.toJSONString());
		String fileName = info.getString("filePath");
		logger.debug("fileName:{}", fileName);
		String url = "";
		try {
			url = imageFtpApi.uploadReturnUrl(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return JSON.toJSONString(new Result(false, "文件上传失败，请重新上传或联系管理员！", null));
		}
		logger.debug("url:{}", url);
		result = new Result(true, url, null);
		logger.info("response:{}", JSON.toJSONString(result));
		return JSON.toJSONString(result);
	}

	@RequestMapping(value = "/uploadDoc", method = RequestMethod.POST)
	@ResponseBody
	public String uploadDoc(HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
		Result result;
		logger.debug("文件上传.........................");
		String path = FileuploadUtil.uploadFile(request, 102400000);
		logger.debug("path" + path);
		JSONObject json = JSONObject.parseObject(path);
		if (!StringUtils.equals(json.getString("resultCode"), "0"))
		{
			result = new Result(false, json.getString("retultMsg"), null);
			return JSON.toJSONString(result);
		}
		JSONArray jsonArray = json.getJSONArray("retultMsg");
		JSONObject info = jsonArray.getJSONObject(0);
		logger.debug("info:{}", info.toJSONString());
		String fileName = info.getString("filePath");
		logger.debug("fileName:{}", fileName);
		String url = "";
		try
		{
			url = imageFtpApi.uploadDocReturnUrl(fileName);
		} catch (Exception e)
		{
			e.printStackTrace();
			return JSON.toJSONString(new Result(false, "文件上传失败，请重新上传或联系管理员！", null));
		}
		logger.debug("url:{}", url);
		result = new Result(true, url, null);
		logger.info("response:{}", JSON.toJSONString(result));
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response)
	{
		File file = new File("/tmp/test.xls");
		try
		{
			byte[] readFileToByteArray = FileUtils.readFileToByteArray(file);
			/**重置response**/
			response.reset();
			/**设置HTML头部信息**/
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "tttt" + "\"");
			response.addHeader("Content-Length", "" + readFileToByteArray.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			/**获得输出流**/
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			/**从字节数组中将文件写到输出流中**/
			outputStream.write(readFileToByteArray);
			/**清空输出流**/
			outputStream.flush();
			/***关闭输入流*/
			outputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String returnOKMsg(List<FileResult> fileList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"resultCode\":\"0\",\"retultMsg\":").append(JSON.toJSONString(fileList)).append("}");
		return sb.toString();
	}
}
