package com.damon.file.service.impl;

import com.damon.entity.PageResult;
import com.damon.file.dao.FileMapper;
import com.damon.file.pojo.FileInfo;
import com.damon.file.pojo.ObjectInfo;
import com.damon.file.service.FileService;
import com.damon.file.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-18 15:07
 */
public abstract class AbstractIFileService implements FileService {

    @Autowired
    protected FileMapper fileMapper;

    private static final String FILE_SPLIT = ".";

    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        if (!fileInfo.getName().contains(FILE_SPLIT)) {
            throw new IllegalArgumentException("缺少后缀名");
        }
        ObjectInfo objectInfo = uploadFile(file);
        fileInfo.setPath(objectInfo.getObjectPath());
        fileInfo.setUrl(objectInfo.getObjectUrl());

        // 设置文件来源
        fileInfo.setSource(fileType());

        // 将文件信息保存到数据库
        fileMapper.insert(fileInfo);
        return fileInfo;
    }

    @Override
    public PageResult<FileInfo> findList(Map<String, Object> params) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public FileInfo out(String id, OutputStream os) {
        return null;
    }

    /**
     * 文件来源
     *
     * @return
     */
    protected abstract String fileType();

    /**
     * 上传文件
     *
     * @param file
     */
    protected abstract ObjectInfo uploadFile(MultipartFile file);

    /**
     * 删除文件资源
     *
     * @param objectPath 文件路径
     */
    protected abstract void deleteFile(String objectPath);
}
