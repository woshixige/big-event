package com.zzx.controller;

import com.zzx.pojo.Result;
import com.zzx.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-09 15:09
 */
@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        //将文件储存在本地磁盘上
        //源文件
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的，防止被覆盖
        String fileName= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("C:\\Users\\zzx\\Desktop\\files\\" + fileName));
        //因为文件储存在本地磁盘上，所以文件的地址其他人无法访问
        //模拟一下文件的地址
        //return Result.success("模拟一下文件的url ...");
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
