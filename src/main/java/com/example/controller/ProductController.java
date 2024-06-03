package com.example.controller;

import com.example.pojo.*;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    /**
     * 新增产品类型
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Product product){
        log.info("新增产品:{}",product);
        try{
            productService.insert(product);
        }
        catch (Exception e){
            return Result.error("该产品类型已存在");
        }
        return Result.success("新增成功");
    }

    /**
     * 产品查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,String spec){
        log.info("分页查询，参数：{},{},{},{}",page,pageSize,name,spec);
        PageBean pageBean = productService.page(page,pageSize,name,spec);
        return Result.success(pageBean);
    }

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  productService.searchField(field);
        return Result.success(res);
    }

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody Product product, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",product,field);

        List<Value> res =  productService.search(product,field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param product
     * @param field
     * @return
     */
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody Product product, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",product,field);

        List<Value> res =  productService.search(product,field);
        return Result.success(res);
    }

    /**
     * 根据id查询产品信息
     * @param product_id
     * @return
     */
    @GetMapping("/{product_id}")
    public Result getById(@PathVariable Integer product_id){
        log.info("根据id查询物料信息， id：{}",product_id);
        Product product = productService.getById(product_id);
        return Result.success(product);
    }

    /**
     * 根据id更新产品信息
     * @param product
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody Product product){
        log.info("更新物料信息");
        try{
            productService.update(product);
        }
        catch (Exception e){
            return Result.error("该产品类型已存在");
        }
        return Result.success("更新成功");
    }

    /**
     * 删除产品
     * @param product_id
     * @return
     */
    @DeleteMapping("/{product_id}")
    public Result deleteById(@PathVariable Integer product_id){
        log.info("根据id删除物料：{}",product_id);

        productService.deleteById(product_id);
        return Result.success();
    }
}
