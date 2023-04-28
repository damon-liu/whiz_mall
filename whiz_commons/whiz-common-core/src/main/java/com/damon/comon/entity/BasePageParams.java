package com.damon.comon.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-28 14:06
 */
@Data
@NoArgsConstructor
public class BasePageParams {

    @ApiModelProperty(value = "页码", required = true)
    @NotNull(message = "20005#页码不能为空")
    private Integer pageNum;

    @ApiModelProperty(value = "每页行数", required = true)
    @NotNull(message = "20006#每页行数不能为空")
    private Integer pageSize;

    @ApiModelProperty(value = "分页参数")
    private String search;

    @ApiModelProperty(value = "排序字段")
    private String orderBy;

    @ApiModelProperty(value = "排序方式")
    private String orderType;

}
