package com.sp.store.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sp.store.common.Result;
import com.sp.store.entity.File;
import com.sp.store.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@CrossOrigin
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";
    @Resource
    private FileMapper fileMapper;
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
        File file1 = new File();
        file1.setName(originalFilename);
        file1.setImg(new byte[inputStream.available()]);
        inputStream.read(file1.getImg());
        inputStream.close();
        fileMapper.insert(file1);
        //String rootFilePath = System.getProperty("user.dir")+"/src/main/resources/files/" +originalFilename;
        //FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.success(ip+":"+port+"/files/"+file1.getUuid());
    }
    @GetMapping("/{uuid}")
    public void getFiles(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        File file = fileMapper.selectOne(new QueryWrapper<File>().eq("uuid", uuid));
        response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentType("application/octet-stream");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(file.getImg());
        outputStream.flush();
        outputStream.close();
    }
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
        File file1 = new File();
        file1.setName(originalFilename);
        file1.setImg(new byte[inputStream.available()]);
        inputStream.read(file1.getImg());
        inputStream.close();
        fileMapper.insert(file1);
        //String rootFilePath = System.getProperty("user.dir")+"/src/main/resources/files/" +originalFilename;
        //FileUtil.writeBytes(file.getBytes(), rootFilePath);
        String url= ip+":"+port+"/files/"+file1.getUuid();
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("errno", 0);
        jsonObject.set("data", new JSONObject().set("url", url));
        return jsonObject;
    }
}
