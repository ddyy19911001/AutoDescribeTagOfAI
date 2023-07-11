package com.app.mybaseframwork.base;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试数据生成工具
 */
public class TestDataCreater {

    public<T> List<T> getTestObjs(int count){
        if(count<=0){
            return new ArrayList<>();
        }else{
            List<T> list=new ArrayList<>();
            for(int i=0;i<count;i++){
                Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                try {
                    T entity = entityClass.newInstance();
                    list.add(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
    }

    public<T> List<T> getTestObjs(int count,T exampleObj){
        if(count<=0){
            return new ArrayList<>();
        }else{
            List<T> list=new ArrayList<>();
            for(int i=0;i<count;i++){
                list.add(exampleObj);
            }
            return list;
        }
    }
} 
