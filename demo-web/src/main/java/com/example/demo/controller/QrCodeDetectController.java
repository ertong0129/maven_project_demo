package com.example.demo.controller;


import com.example.demo.impl.QrCodeDetectService;
import com.example.demo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author paida 派哒 zeyu.pzy@alibaba-inc.com
 */
@Controller
public class QrCodeDetectController {

	@Autowired
	private QrCodeDetectService qrCodeDetectService;

	@GetMapping("/getQrCode")
	@ResponseBody
	public String getQrCode(@RequestParam String filePath) {
		return qrCodeDetectService.getQrCode(filePath);
	}

	@PostMapping("/uploadImgGetQrCode")
	@ResponseBody
	public Result<String> uploadImgGetQrCode(@RequestParam("file") MultipartFile file) throws Exception {
	   return qrCodeDetectService.uploadImgGetQrCode(file);
	}

	@PostMapping("/httpImgGetQrCode")
	@ResponseBody
	public Result<String> httpImgGetQrCode(@RequestParam("imgUrl") String imgUrl) throws Exception {
		return qrCodeDetectService.httpImgGetQrCode(imgUrl);
	}

	@GetMapping("/getLibraryPath")
	@ResponseBody
	public Result<String> getLibraryPath() {
		return Result.buildSuccess(System.getProperty("java.library.path"));
	}
}
