package com.damon.file.core.template;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.SetBucketCORSRequest;
import com.amazonaws.util.IOUtils;
import com.damon.file.core.properties.FileServerProperties;
import com.damon.file.pojo.ObjectInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-21 6:37
 */
@Configuration
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX_S3, name = "type", havingValue = FileServerProperties.TYPE_S3_ALI_YUN)
public class AliYunS3Template extends AbstractS3Template implements InitializingBean {

    private static final String DEF_CONTEXT_TYPE = "application/octet-stream";
    private static final String PATH_SPLIT = "/";

    @Autowired
    private FileServerProperties fileProperties;
    private OSSClient ossClient;


    @Override
    public String s3FileType() {
        return FileServerProperties.TYPE_S3_ALI_YUN;
    }

    @Override
    public void afterPropertiesSet() {
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        conf.setMaxConnections(1000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(20000);
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(4);
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(fileProperties.getS3().getAccessKeyId(), fileProperties.getS3().getAccessKeySecret());
        this.ossClient = new OSSClient(fileProperties.getS3().getEndpoint(), credentialProvider, conf);
        // 获取跨域资源共享规则列表。
        ArrayList<SetBucketCORSRequest.CORSRule> corsRules = (ArrayList<SetBucketCORSRequest.CORSRule>) ossClient
                .getBucketCORSRules(fileProperties.getS3().getBucketName());
        SetBucketCORSRequest request = new SetBucketCORSRequest(fileProperties.getS3().getBucketName());
        // 已存在的规则将被覆盖。
        request.setCorsRules(corsRules);
        ossClient.setBucketCORS(request);
    }

    @SneakyThrows
    @Override
    public ObjectInfo upload(MultipartFile file) {
        return upload(fileProperties.getS3().getBucketName(), file.getOriginalFilename(), file.getInputStream()
                , ((Long) file.getSize()).intValue(), file.getContentType());
    }

    @SneakyThrows
    @Override
    public ObjectInfo upload(String fileName, InputStream is) {
        return upload(fileProperties.getS3().getBucketName(), fileName, is, is.available(), DEF_CONTEXT_TYPE);
    }

    @SneakyThrows
    @Override
    public ObjectInfo upload(String bucketName, String fileName, InputStream is) {
        return upload(bucketName, fileName, is, is.available(), DEF_CONTEXT_TYPE);
    }

    @SneakyThrows
    @Override
    public void delete(String objectPath) {
        delete(fileProperties.getS3().getBucketName(), objectPath);
    }

    @Override
    public void delete(String bucketName, String objectName) {
        ossClient.deleteObject(bucketName, objectName);
    }

    @Override
    public void out(String objectName, OutputStream os) {
        out(fileProperties.getS3().getBucketName(), objectName, os);
    }

    @SneakyThrows
    @Override
    public void out(String bucketName, String objectName, OutputStream os) {
        OSSObject s3Object = ossClient.getObject(bucketName, objectName);
        try (
                InputStream s3is = s3Object.getObjectContent()
        ) {
            IOUtils.copy(s3is, os);
        }
    }

    /**
     * 上传对象
     *
     * @param bucketName  bucket名称
     * @param objectName  对象名
     * @param is          对象流
     * @param size        大小
     * @param contentType 类型
     */
    private ObjectInfo upload(String bucketName, String objectName, InputStream is, int size, String contentType) {
        String dirPrefix = fileProperties.getS3().getDirPrefix();
        String folder = "";
        if (StrUtil.isNotBlank(dirPrefix)) {
            folder  = dirPrefix;
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);
        // inline：预览Object。attachment：将Object下载到本地。例如attachment; filename="example.jpg"，表示下载Object到本地并以example.jpg文件名进行保存。
        objectMetadata.setContentDisposition("attachment");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folder + objectName, is, objectMetadata);
        ossClient.putObject(putObjectRequest);

        ObjectInfo obj = new ObjectInfo();
        String objectUrl = "http://" + bucketName + "." + fileProperties.getS3().getEndpoint() + PATH_SPLIT + folder + objectName;
        String objectPath = bucketName + PATH_SPLIT + folder + objectName;
        obj.setObjectPath(objectPath);
        obj.setObjectUrl(objectUrl);
        return obj;
    }
}
