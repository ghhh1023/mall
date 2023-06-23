package com.gh.mall.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.gh.mall.common.Result;
import com.gh.mall.entity.NxSystemFileInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.service.NxSystemFileInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/files")
public class NxSystemFileInfoController {

    private static final String BASE_PATH = System.getProperty("user.dir")+"/src/main/resources/static/file/";

    @Resource
    private NxSystemFileInfoService nxSystemFileInfoService;

    /**
     * add
     */
    @PostMapping("/upload")
    public Result<NxSystemFileInfo> upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if(originalName==null){
            return Result.error("1001","文件名不能为空");
        }
        if(originalName.contains("png")&&originalName.contains("jpg")&&originalName.contains("jpeg")&&originalName.contains("gif")){
            return Result.error("1002","只能上传图片");
        }
        String fileName = FileUtil.mainName(originalName)+System.currentTimeMillis()+"."+FileUtil.extName(originalName);
        FileUtil.writeBytes(file.getBytes(),BASE_PATH+fileName);
        NxSystemFileInfo info = new NxSystemFileInfo();
        info.setOriginname(originalName);
        info.setFilename(fileName);
        NxSystemFileInfo addInfo = nxSystemFileInfoService.add(info);
        if(addInfo!=null){
            return Result.success(addInfo);
        }
        return Result.error("1003","上传失败");
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result update(@RequestBody NxSystemFileInfo nxSystemFileInfo){
        nxSystemFileInfoService.update(nxSystemFileInfo);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        nxSystemFileInfoService.delete(id);
        return Result.success();
    }

    /**
     * find
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(nxSystemFileInfoService.findById(id));
    }

    /**
     * 下载文件
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        if(StrUtil.isBlank(id)||"null".equals(id)){
            throw new CustomException("1001","您未上传文件");
        }
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.findById(Long.parseLong(id));
        if(nxSystemFileInfo==null){
            throw new CustomException("1001","没找到该文件");
        }
        byte[] bytes = FileUtil.readBytes(BASE_PATH+nxSystemFileInfo.getFilename());
        response.reset();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(nxSystemFileInfo.getOriginname(),"UTF-8"));
        response.addHeader("Content-Length",""+bytes.length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(bytes);
        toClient.flush();
        toClient.close();
    }
}
