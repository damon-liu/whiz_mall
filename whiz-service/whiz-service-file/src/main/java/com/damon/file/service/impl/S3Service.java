package com.damon.file.service.impl;

import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.damon.file.core.properties.FileServerProperties;
import com.damon.file.core.template.AbstractS3Template;
import com.damon.file.pojo.FileInfo;
import com.damon.file.pojo.ObjectInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-21 6:34
 */
@Service
@ConditionalOnProperty(prefix = com.damon.file.core.properties.FileServerProperties.PREFIX, name = "type", havingValue = FileServerProperties.TYPE_S3)
public class S3Service extends AbstractIFileService{

    @Autowired
    private AbstractS3Template s3Template;

    @Override
    protected String fileType() {
        return FileServerProperties.TYPE_S3;
    }

    @Override
    protected ObjectInfo uploadFile(MultipartFile file) {
        return s3Template.upload(file);
    }

    @Override
    protected void deleteFile(String objectPath) {
        S3Object s3Object = parsePath(objectPath);
        s3Template.delete(s3Object.bucketName, s3Object.objectName);
    }

    @Override
    public FileInfo out(String id, OutputStream os) {
        FileInfo fileInfo = fileMapper.selectByPrimaryKey(id);
        if (fileInfo != null) {
            S3Object s3Object = parsePath(fileInfo.getPath());
            s3Template.out(s3Object.bucketName, s3Object.objectName, os);
        }
        return fileInfo;
    }


    private S3Object parsePath(String path) {
        S3Object s3Object = new S3Object();
        if (StrUtil.isNotEmpty(path)) {
            int splitIndex = path.indexOf("/");
            if (splitIndex != -1) {
                s3Object.bucketName = path.substring(0, splitIndex);
                s3Object.objectName = path.substring(splitIndex + 1);
            }
        }
        return s3Object;
    }

    @Setter
    @Getter
    private class S3Object {
        private String bucketName;
        private String objectName;
    }

}
