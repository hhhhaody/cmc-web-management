package com.example.service.impl;

import com.example.mapper.ProductMapper;
import com.example.mapper.ProductionLineMapper;
import com.example.mapper.TheoreticalTimeMapper;
import com.example.pojo.*;
import com.example.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    TheoreticalTimeMapper theoreticalTimeMapper;

    @Autowired
    ProductionLineMapper productionLineMapper;

    @Transactional
    @Override
    public void insert(Product product) {
        if(productMapper.check(product) == null){
            Integer batch = productMapper.getMaxBatch(product.getName());
            if(batch != null){
                product.setBatch(batch+1);
            }
            else product.setBatch(1);
            productMapper.insert(product);

            Integer id = productMapper.getId(product.getName(),product.getSpec());
            List<Integer> stationsIds = product.getStations();
            List<Integer> times = product.getTimes();
            for (int i = 0; i < stationsIds.size(); i++){
                TheoreticalTime theoreticalTime = new TheoreticalTime();
                theoreticalTime.setProductId(id);
                theoreticalTime.setProductionLine(stationsIds.get(i));
                theoreticalTime.setTheoreticalTime(times.get(i));
                theoreticalTimeMapper.insert(theoreticalTime);
            }
        }
        else{
            throw new RuntimeException();
        }
    }

    @Transactional
    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec) {
        //计算数量
        productMapper.calAmount();

        PageHelper.startPage(page,pageSize);

        List<Product> productList = productMapper.list(name,spec);
        Page<Product> p = (Page<Product>) productList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return productMapper.searchField(field);
    }

    @Override
    public List<Value> search(Product product, String field) {
        String name = product.getName();
        String spec = product.getSpec();
        return productMapper.search(name,spec,field);
    }

    @Transactional
    @Override
    public Product getById(Integer product_id) {
        Product product = productMapper.getById(product_id);
        List<TheoreticalTime> theoreticalTimes = theoreticalTimeMapper.getByProductId(product_id);
//        System.out.println(theoreticalTimes);
        //查询理论耗时
        String section = productionLineMapper.getSectionById(theoreticalTimes.get(0).getProductionLine());
        product.setSection(section);
        List<Integer> stations = new ArrayList<>();
        List<Integer> times = new ArrayList<>();
        for(int i =0;i<theoreticalTimes.size();i++){
//            String station = productionLineMapper.getStationById(theoreticalTimes.get(i).getProductionLine());
//            System.out.println(station);
            stations.add(theoreticalTimes.get(i).getProductionLine());
            times.add(theoreticalTimes.get(i).getTheoreticalTime());
        }
        product.setStations(stations);
        product.setTimes(times);
        return product;
    }

    @Transactional
    @Override
    public void update(Product product) {
        Product res = productMapper.check(product);
        if(res == null){
            Integer batch = productMapper.getMaxBatch(product.getName());
            if(batch != null){
                product.setBatch(batch+1);
            }
            else product.setBatch(1);

            //更新理论耗时表
            Integer id = product.getId();
            List<Integer> stationsIds = product.getStations();
            List<Integer> times = product.getTimes();
            for (int i = 0; i < stationsIds.size(); i++){
                TheoreticalTime theoreticalTime = new TheoreticalTime();
                theoreticalTime.setProductId(id);
                theoreticalTime.setProductionLine(stationsIds.get(i));
                theoreticalTime.setTheoreticalTime(times.get(i));
                theoreticalTimeMapper.update(theoreticalTime);
            }
            //只更新了名称,规格型号,阈值
            productMapper.update(product);
        }
        else{
            if(res.getId() == product.getId()){
                //更新理论耗时表
                Integer id = product.getId();
                List<Integer> stationsIds = product.getStations();
                List<Integer> times = product.getTimes();
                for (int i = 0; i < stationsIds.size(); i++){
                    TheoreticalTime theoreticalTime = new TheoreticalTime();
                    theoreticalTime.setProductId(id);
                    theoreticalTime.setProductionLine(stationsIds.get(i));
                    theoreticalTime.setTheoreticalTime(times.get(i));
                    theoreticalTimeMapper.update(theoreticalTime);
                }
                //只更新了名称,规格型号,阈值
                productMapper.update(product);
            }
            else{
                throw new RuntimeException();
            }
        }

    }

    @Transactional
    @Override
    public void deleteById(Integer product_id) {
        //删除理论耗时
        theoreticalTimeMapper.deleteByProductId(product_id);
        //只删除了产品表
        productMapper.deleteById(product_id);
    }

}
