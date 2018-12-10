package com.xdong.datacenter.provider.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.toolkit.ClassUtils;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;

/**
 * 类ObjectUtil.java的实现描述：处理对象属性
 * 
 * @author wanglei 2018年6月12日 下午2:14:46
 */
public class ObjectUtil {

    public static Map<String, Object> getColumnParamList(Object entity) {
        Class<?> clazz = entity.getClass();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(clazz));
        if (CollectionUtils.isNotEmpty(fieldList)) {
            Iterator<Field> iterator = fieldList.iterator();
            while (iterator.hasNext()) {
                Field field = iterator.next();
                /* 注解非表字段属性 */
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField != null) {
                    resultMap.put(tableField.value(), ReflectionKit.getMethodValue(clazz, entity, field.getName()));
                } else {
                    resultMap.put(field.getName(), ReflectionKit.getMethodValue(clazz, entity, field.getName()));
                }
            }
        }
        return resultMap;
    }
}
