package com.damon.file.mapper;


import com.damon.db.mapper.SuperMapper;
import com.damon.file.pojo.FileInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 上传存储db
 *
 * @author
 */
public interface FileMapper extends SuperMapper<FileInfo> {

    List<FileInfo> findList(@Param("f") Map<String, Object> params);
}
