package com.xdong.datacenter.facade.api.base;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.weidai.zm.norm.bean.Pageable;
import com.weidai.zm.norm.rpc.RpcResponse;

public interface BaseFacade<T> {

    /**
     * 新增：保存对象记录
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> saveRecord(T entity);

    /**
     * 修改：通过主键更新记录
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> updateRecordById(T entity);

    /**
     * 修改：通过条件更新记录
     * 
     * @param entity 更新对象属性
     * @param condition 条件
     * @return
     */
    RpcResponse<Boolean> updateRecordByCondition(T entity, EntityWrapper<T> condition);

    /**
     * 新增/修改：insert记录或者通过主键更新记录 有id update，没有id insert
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> insertOrUpdateById(T entity);

    /**
     * 删除：通过主键删除记录【物理删除】
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> deleteRecordById(Long rId);

    /**
     * 删除：通过条件删除记录
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> deleteRecordByCondition(EntityWrapper<T> condition);
    
    /**
     * 删除：通过条件删除记录
     * 
     * @param entity
     * @return
     */
    RpcResponse<Boolean> deleteRecordByEntityParam(T entity);

    /**
     * 查询：通过条件查询对象集合
     * 
     * @param condition
     * @return
     */
    RpcResponse<List<T>> getListByCondition(EntityWrapper<T> condition);
    
    /**
     * 查询：通过条件查询对象集合【对象属性作为查询条件】
     * 
     * @param condition
     * @return
     */
    RpcResponse<List<T>> getListByEntityParam(T entity);

    /**
     * 分页查询：通过条件分页查询对象集合
     * 
     * @param condition
     * @return
     */
    RpcResponse<Pageable<T>> getPageListByCondition(int current, int size, EntityWrapper<T> condition);

}
