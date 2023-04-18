package com.damon.file.core.template;

import com.damon.file.core.properties.FileServerProperties;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-18 15:27
 */
@ConditionalOnClass(FastFileStorageClient.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "type", havingValue = FileServerProperties.TYPE_FDFS)
public class FdfsTemplate {
}
