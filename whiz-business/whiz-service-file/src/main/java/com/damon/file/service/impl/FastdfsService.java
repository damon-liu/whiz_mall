package com.damon.file.service.impl;

import com.damon.file.core.properties.FileServerProperties;
import com.damon.file.core.template.FdfsTemplate;
import com.damon.file.pojo.FileInfo;
import com.damon.file.pojo.FileDetail;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.OutputStream;

/**
 * Description: fstDfs上传
 *
 * @author damon.liu
 * Date 2023-04-18 15:24
 */
@Service
@ConditionalOnProperty(prefix = com.damon.file.core.properties.FileServerProperties.PREFIX, name = "type", havingValue = FileServerProperties.TYPE_FDFS)
public class FastdfsService extends AbstractIFileService{

    @Resource
    private FdfsTemplate fdfsTemplate;


    @Override
    protected String fileType() {
        return FileServerProperties.TYPE_FDFS;
    }

    @Override
    protected FileDetail uploadFile(MultipartFile file) {
        return fdfsTemplate.upload(file);
    }

    @Override
    protected void deleteFile(String objectPath) {
        fdfsTemplate.delete(objectPath);
    }

    @Override
    public FileInfo out(String id, OutputStream os) {
        return null;
    }
}
