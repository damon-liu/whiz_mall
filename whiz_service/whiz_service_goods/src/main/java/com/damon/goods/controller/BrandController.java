package com.damon.goods.controller;

import com.damon.entity.PageResult;
import com.damon.entity.Result;
import com.damon.entity.StatusCode;
import com.damon.goods.pojo.Brand;
import com.damon.goods.service.BrandService;
import com.damon.util.IdWorker;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-04-10 2:57
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result findAll() {
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", brandList);
    }

    /***
     * 根据ID查询品牌数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Brand brand = brandService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", brand);
    }

    /***
     * 新增品牌数据
     * @param brand
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 修改品牌数据
     * @param brand
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable Integer id) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        brandService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Brand> list = brandService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    // @GetMapping(value = "/search/{page}/{size}")
    // public Result findPage(@PathVariable int page, @PathVariable int size) {
    //     Page<Brand> pageList = brandService.findPage(page, size);
    //     PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
    //     return new Result(true, StatusCode.OK, "查询成功", pageResult);
    // }

    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam(required = false) Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Brand> pageList = brandService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @Autowired
    private IdWorker idWorker;

    @GetMapping(value = "/createId")
    public long createId(){
        return idWorker.nextId();
    }

}
