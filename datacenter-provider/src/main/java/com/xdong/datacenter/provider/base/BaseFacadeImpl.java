package com.xdong.datacenter.provider.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.weidai.zm.norm.bean.Pageable;
import com.weidai.zm.norm.rpc.RpcResponse;
import com.xdong.datacenter.facade.api.base.BaseFacade;

public class BaseFacadeImpl<S extends IService<T>, T> implements BaseFacade<T> {

    @Autowired
    protected S impService;

    @Override
    public RpcResponse<Boolean> saveRecord(T entity) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.insert(entity));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> updateRecordById(T entity) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.updateById(entity));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> updateRecordByCondition(T entity, EntityWrapper<T> condition) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.update(entity, condition));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> insertOrUpdateById(T entity) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.insertOrUpdate(entity));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> deleteRecordById(Long rId) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.deleteById(rId));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> deleteRecordByCondition(EntityWrapper<T> condition) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        rResult.setResult(impService.delete(condition));
        return rResult;
    }

    @Override
    public RpcResponse<Boolean> deleteRecordByEntityParam(T entity) {
        RpcResponse<Boolean> rResult = new RpcResponse<Boolean>();
        EntityWrapper<T> condition = new EntityWrapper<T>();
        condition.allEq(ObjectUtil.getColumnParamList(entity));
        rResult.setResult(impService.delete(condition));
        return rResult;
    }

    @Override
    public RpcResponse<List<T>> getListByCondition(EntityWrapper<T> condition) {
        RpcResponse<List<T>> rResult = new RpcResponse<List<T>>();
        rResult.setResult(impService.selectList(condition));
        return rResult;
    }

    @Override
    public RpcResponse<List<T>> getListByEntityParam(T entity) {
        RpcResponse<List<T>> rResult = new RpcResponse<List<T>>();
        EntityWrapper<T> condition = new EntityWrapper<T>();
        condition.allEq(ObjectUtil.getColumnParamList(entity));
        rResult.setResult(impService.selectList(condition));
        return rResult;
    }

    @Override
    public RpcResponse<Pageable<T>> getPageListByCondition(int current, int size, EntityWrapper<T> condition) {
        RpcResponse<Pageable<T>> rResult = new RpcResponse<Pageable<T>>();

        Page<T> page = new Page<T>(current, size);
        Page<T> queryResult = impService.selectPage(page, condition);

        Pageable<T> pageResult = new Pageable<T>();
        if (queryResult != null) {
            pageResult.setData(queryResult.getRecords());
            pageResult.setTotal(queryResult.getTotal());
            pageResult.setPageNo(current);
            pageResult.setPageSize(size);
        }

        rResult.setResult(pageResult);

        return rResult;
    }

}
