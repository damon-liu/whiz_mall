package com.damon.file.service.impl;

import com.damon.file.core.properties.FileServerProperties;
import com.damon.file.core.template.FdfsTemplate;
import com.damon.file.pojo.ObjectInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Description:
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
        return null;
    }

    @Override
    protected ObjectInfo uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    protected void deleteFile(String objectPath) {

    }
}
