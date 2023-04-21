package com.damon.file.service;

import com.damon.entity.PageResult;
import com.damon.file.pojo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-18 15:06
 */
public interface FileService {

    FileInfo upload(MultipartFile file ) throws Exception;

    PageResult<FileInfo> findList(Map<String, Object> params);

    void delete(String id);

    FileInfo out(String id, OutputStream os);

}
