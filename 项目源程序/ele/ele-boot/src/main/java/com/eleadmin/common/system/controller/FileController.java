package com.eleadmin.common.system.controller;

import cn.hutool.core.util.StrUtil;
import com.eleadmin.common.core.annotation.OperationLog;
import com.eleadmin.common.core.config.ConfigProperties;
import com.eleadmin.common.core.utils.FileServerUtil;
import com.eleadmin.common.core.web.ApiResult;
import com.eleadmin.common.core.web.BaseController;
import com.eleadmin.common.core.web.PageResult;
import com.eleadmin.common.system.entity.FileRecord;
import com.eleadmin.common.system.param.FileRecordParam;
import com.eleadmin.common.system.service.FileRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 文件上传下载控制器
 *
 * @author EleAdmin
 * @since 2018-12-24 16:10:24
 */
@Api(tags = "文件上传下载")
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseController {
    @Resource
    private ConfigProperties config;
    @Resource
    private FileRecordService fileRecordService;

    @PreAuthorize("hasAuthority('sys:file:upload')")
    @OperationLog
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ApiResult<FileRecord> upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        FileRecord result = null;
        try {
            String dir = getUploadDir();
            File upload = FileServerUtil.upload(file, dir, config.getUploadUuidName());
            String path = upload.getAbsolutePath()
                    .replace("\\", "/")
                    .substring(dir.length() - 1)
                    .replace("\\", "/");
            String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/upload");
            String originalName = file.getOriginalFilename();
            result = new FileRecord();
            result.setCreateUserId(getLoginUserId());
            result.setName(StrUtil.isBlank(originalName) ? upload.getName() : originalName);
            result.setLength(upload.length());
            result.setPath(path);
            result.setUrl(requestURL + "/" + path);
            result.setThumbnail(FileServerUtil.isImage(upload) ? (requestURL + "/thumbnail/" + path) : null);
            fileRecordService.save(result);
            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return fail("上传失败", result).setError(e.toString());
        }
    }

    @PreAuthorize("hasAuthority('sys:file:upload')")
    @OperationLog
    @ApiOperation("上传base64文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "base64", value = "base64", required = true, dataType = "string"),
            @ApiImplicitParam(name = "fileName", value = "文件名称", dataType = "string")
    })
    @PostMapping("/upload/base64")
    public ApiResult<FileRecord> uploadBase64(String base64, String fileName, HttpServletRequest request) {
        FileRecord result = null;
        try {
            String dir = getUploadDir();
            File upload = FileServerUtil.upload(base64, fileName, getUploadDir());
            String path = upload.getAbsolutePath().substring(dir.length()).replace("\\", "/");
            String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/upload/base64");
            result = new FileRecord();
            result.setCreateUserId(getLoginUserId());
            result.setName(StrUtil.isBlank(fileName) ? upload.getName() : fileName);
            result.setLength(upload.length());
            result.setPath(path);
            result.setUrl(requestURL + path);
            result.setThumbnail(FileServerUtil.isImage(upload) ? (requestURL + "/thumbnail" + path) : null);
            fileRecordService.save(result);
            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return fail("上传失败", result).setError(e.toString());
        }
    }

    @ApiOperation("查看原文件")
    @GetMapping("/{dir}/{name:.+}")
    public void preview(@PathVariable("dir") String dir, @PathVariable("name") String name,
                        HttpServletResponse response, HttpServletRequest request) {
        File file = new File(getUploadDir(), dir + "/" + name);
        FileServerUtil.preview(file, getPdfOutDir(), config.getOpenOfficeHome(), response, request);
    }

    @ApiOperation("下载原文件")
    @GetMapping("/download/{dir}/{name:.+}")
    public void download(@PathVariable("dir") String dir, @PathVariable("name") String name,
                         HttpServletResponse response, HttpServletRequest request) {
        File file = new File(getUploadDir(), dir + "/" + name);
        FileServerUtil.preview(file, true, null, null, response, request);
    }

    @ApiOperation("查看缩略图")
    @GetMapping("/thumbnail/{dir}/{name:.+}")
    public void thumbnail(@PathVariable("dir") String dir, @PathVariable("name") String name,
                          HttpServletResponse response, HttpServletRequest request) {
        File file = new File(getUploadDir(), dir + "/" + name);
        File thumbnail = new File(getUploadSmDir(), dir + "/" + name);
        FileServerUtil.previewThumbnail(file, thumbnail, config.getThumbnailSize(), response, request);
    }

    @PreAuthorize("hasAuthority('sys:file:remove')")
    @OperationLog
    @ApiOperation("删除文件")
    @DeleteMapping("/remove/{id}")
    public ApiResult<?> remove(@PathVariable("id") Integer id) {
        if (fileRecordService.removeById(id)) {
            FileRecord record = fileRecordService.getById(id);
            if (StrUtil.isNotBlank(record.getPath())) {
                new File(getUploadDir(), record.getPath()).delete();
                new File(getUploadSmDir(), record.getPath()).delete();
            }
            return success("删除成功");
        }
        return fail("删除失败");
    }

    @PreAuthorize("hasAuthority('sys:file:list')")
    @OperationLog
    @ApiOperation("分页查询文件")
    @GetMapping("/page")
    public ApiResult<PageResult<FileRecord>> page(FileRecordParam param, HttpServletRequest request) {
        PageResult<FileRecord> result = fileRecordService.pageRel(param);
        String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/page");
        for (FileRecord record : result.getList()) {
            record.setUrl(requestURL + record.getPath());
            record.setThumbnail(requestURL + "/thumbnail" + record.getPath());
        }
        return success(result);
    }

    @PreAuthorize("hasAuthority('sys:file:list')")
    @OperationLog
    @ApiOperation("查询全部文件")
    @GetMapping("/list")
    public ApiResult<List<FileRecord>> list(FileRecordParam param, HttpServletRequest request) {
        List<FileRecord> records = fileRecordService.listRel(param);
        String requestURL = StrUtil.removeSuffix(request.getRequestURL(), "/list");
        for (FileRecord record : records) {
            record.setUrl(requestURL + record.getPath());
            record.setThumbnail(requestURL + "/thumbnail" + record.getPath());
        }
        return success(records);
    }

    /**
     * 文件上传基目录
     */
    private String getUploadBaseDir() {
        return File.listRoots()[config.getUploadLocation()].getAbsolutePath()
                .replace("\\", "/") + "/upload/";
    }

    /**
     * 文件上传位置
     */
    private String getUploadDir() {
        return getUploadBaseDir() + "file/";
    }

    /**
     * 缩略图生成位置
     */
    private String getUploadSmDir() {
        return getUploadBaseDir() + "thumbnail/";
    }

    /**
     * office转pdf输出位置
     */
    private String getPdfOutDir() {
        return getUploadBaseDir() + "pdf/";
    }

}
