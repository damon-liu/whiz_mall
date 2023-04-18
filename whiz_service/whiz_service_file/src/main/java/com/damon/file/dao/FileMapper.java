package com.damon.file.dao;


import com.damon.file.pojo.FileInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;
import java.util.Map;

/**
 * 上传存储db
 *
 * @author
 */
public interface FileMapper extends Mapper<FileInfo> {

    List<FileInfo> findList(Page<FileInfo> page, @Param("f") Map<String, Object> params);
}
