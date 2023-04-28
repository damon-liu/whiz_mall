package com.damon.file.core.template;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.damon.file.core.properties.FileServerProperties;
import com.damon.file.pojo.ObjectInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-21 6:49
 */
@ConditionalOnClass(AmazonS3.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX_S3, name = "type", havingValue = FileServerProperties.TYPE_S3_AWS)
public class AwsS3Template extends AbstractS3Template implements InitializingBean {

    private static final String DEF_CONTEXT_TYPE = "application/octet-stream";
    private static final String PATH_SPLIT = "/";

    @Autowired
    private FileServerProperties fileProperties;
    private AmazonS3 amazonS3;

    @Override
    public String s3FileType() {
        return FileServerProperties.TYPE_S3_AWS;
    }

    @Override
    public void afterPropertiesSet() {
        ClientConfiguration config = new ClientConfiguration();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(fileProperties.getS3().getEndpoint(), fileProperties.getS3().getRegion());
        AWSCredentials credentials = new BasicAWSCredentials(fileProperties.getS3().getAccessKeyId(), fileProperties.getS3().getAccessKeySecret());
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(credentials);
        this.amazonS3 = AmazonS3Client.builder()
                .withEndpointConfiguration(endpoint)
                .withClientConfiguration(config)
                .withCredentials(awsCredentialsProvider)
                .withPathStyleAccessEnabled(fileProperties.getS3().getPathStyleAccessEnabled())
                .disableChunkedEncoding()
                .build();
    }

    @Override
    public ObjectInfo upload(MultipartFile file) {
        return null;
    }

    @SneakyThrows
    @Override
    public ObjectInfo upload(String fileName, InputStream is) {
        return upload(fileProperties.getS3().getBucketName(), fileName, is, is.available(), DEF_CONTEXT_TYPE);
    }

    @Override
    public ObjectInfo upload(String bucketName, String fileName, InputStream is) {
        return null;
    }

    @Override
    public void delete(String objectName) {
        delete(fileProperties.getS3().getBucketName(), objectName);
    }

    @Override
    public void delete(String bucketName, String objectName) {
        amazonS3.deleteObject(bucketName, objectName);
    }

    @Override
    public void out(String objectName, OutputStream os) {
        out(fileProperties.getS3().getBucketName(), objectName, os);
    }

    @SneakyThrows
    @Override
    public void out(String bucketName, String objectName, OutputStream os) {
        S3Object s3Object = amazonS3.getObject(bucketName, objectName);
        try (
                S3ObjectInputStream s3is = s3Object.getObjectContent();
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
        com.amazonaws.services.s3.model.ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);
        com.amazonaws.services.s3.model.PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucketName, objectName, is, objectMetadata);
        putObjectRequest.getRequestClientOptions().setReadLimit(size + 1);
        amazonS3.putObject(putObjectRequest);

        ObjectInfo obj = new ObjectInfo();
        obj.setObjectPath(bucketName + PATH_SPLIT + objectName);
        obj.setObjectUrl(fileProperties.getS3().getEndpoint() + PATH_SPLIT + obj.getObjectPath());
        return obj;
    }
}
