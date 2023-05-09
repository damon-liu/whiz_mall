package com.damon.file.core.template;

import com.damon.file.pojo.FileDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-21 6:36
 */
public abstract class AbstractS3Template {

    /**
     * s3文件来源
     *
     * @return
     */
    public abstract String s3FileType();


    /**
     * 上传文件
     *
     * @param file
     */
    public abstract FileDetail upload(MultipartFile file);

    public abstract FileDetail upload(String fileName, InputStream is);

    public abstract FileDetail upload(String bucketName, String fileName, InputStream is);

    /**
     * 删除文件资源
     *
     * @param objectPath 文件路径
     */
    public abstract void delete(String objectPath);

    public abstract void delete(String bucketName, String objectName);

    /**
     * 下载文件
     *
     * @param objectName
     * @param os
     */
    public abstract void out(String objectName, OutputStream os);

    public abstract void out(String bucketName, String objectName, OutputStream os);
}
