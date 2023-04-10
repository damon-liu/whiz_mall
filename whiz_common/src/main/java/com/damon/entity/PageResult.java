package com.damon.entity;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-10 2:09
 */
public class PageResult<T> {

    private Long total;//总记录数

    private List<T> rows;//记录


    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
