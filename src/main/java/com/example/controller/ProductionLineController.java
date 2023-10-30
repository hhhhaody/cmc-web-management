package com.example.controller;

import com.example.pojo.*;
import com.example.service.ProductionLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/productionLine")
public class ProductionLineController {

    @Autowired
    private ProductionLineService productionLineService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        log.info("分页查询，参数：{},{},{},{}",page,pageSize);
        PageBean pageBean = productionLineService.page(page,pageSize);
        return Result.success(pageBean);
    }

    /**
     * 批量新增
     * @param productionLine
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody ProductionLine productionLine) {
        log.info("批量新增: {}", productionLine);
        productionLineService.insert(productionLine);
        return Result.success();
    }

    /**
     * 删除此工段及工段下所有工位
     * @param section
     * @return
     */

    @DeleteMapping("/{section}")
    public Result deleteBySection(@PathVariable String section){
        log.info("删除工段：{}",section);

        productionLineService.deleteBySection(section);
        return Result.success();
    }

    /**
     * 查询此工段下所有工位
     * @param section
     * @return
     */
    @GetMapping("/{section}")
    public Result getBySection(@PathVariable String section){
        log.info("根据工位查询工段， section：{}",section);
        List<Value> stationList = productionLineService.getBySection(section);
        return Result.success(stationList);
    }

    /**
     * 修改工段名称
     * @return
     */
    @PutMapping("/section")
    public Result updateSectionName(@RequestBody Pair data){
        log.info("修改工段 {} 名称，{}",data.getSection(),data.getNewName());
        productionLineService.updateSectionName(data.getSection(),data.getNewName());
        return Result.success();
    }

    /**
     * 更新工位
     * @param productionLine
     * @return
     */
    @PutMapping
    public Result updateStations(@RequestBody ProductionLine productionLine){
        log.info("更新工位: {}", productionLine);
        productionLineService.updateStations(productionLine);
        return Result.success();
    }

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  productionLineService.searchField(field);
        return Result.success(res);
    }


    /**
     * 弹框内搜索联想
     * @param facility
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody Facility facility,@PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",facility,field);

        List<Value> res =  productionLineService.search(facility,field);
        return Result.success(res);
    }

    /**
     * 查询此工段对应工位id及工位名
     * @param section
     * @return
     */
    @GetMapping("/{section}/ids")
    public Result getStationsIds(@PathVariable String section){
        log.info("根据工位查询工段及其id， section：{}",section);
        List<IdStrPair> stationsIdsList = productionLineService.getStationsIds(section);
        return Result.success(stationsIdsList);
    }

}
