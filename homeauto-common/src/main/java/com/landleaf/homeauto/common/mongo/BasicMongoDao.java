package com.landleaf.homeauto.common.mongo;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 提供mongo的基本操作 继承此类的方法，必须交由spring，否则不可用
 * @author wenyilu
 */
public abstract class BasicMongoDao {

    @Resource
    protected MongoTemplate mongoTemplate;

    /**
     * 插入单条数据 insert into
     *
     * @param collection 表名称
     * @param t          数据信息
     * @return 插入的数据
     */
    public <T> T insert(String collection, T t) {
        return mongoTemplate.insert(t, collection);
    }

    /**
     * 批量插入数据 insert into
     *
     * @param collection 表名称
     * @param list       要插入的数据
     * @return 插入的数据
     */
    public Collection<?> batchInsert(String collection, Collection<?> list) {
        return mongoTemplate.insert(list, collection);
    }

    /**
     * 查询总数 select count()
     *
     * @param collection 表名称
     * @param query      查询条件
     * @return 查询条件命中的数量
     */
    public long count(String collection, Query query) {
        return mongoTemplate.count(query, collection);
    }

    /**
     * 去重查询 select distinct，仅支持一个字段去重
     *
     * @param collection  表名称
     * @param query       查询条件
     * @param distinctKey 要去重的值
     * @param resultClass 返回的值类型
     * @return 去重后的集合
     */
    public <T> List<T> distinct(String collection, Query query, String distinctKey, Class<T> resultClass) {
        return mongoTemplate.findDistinct(query, distinctKey, collection, resultClass);
    }

    /**
     * 修改单条数据，如果查询条件命中多条，则仅修改第一条
     *
     * @param collection 表名称
     * @param query      查询条件
     * @param update     修改的值
     * @return 修改的结果
     */
    public UpdateResult updateOne(String collection, Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, collection);
    }

    /**
     * 修改单条数据，如果查询条件命中多条，则仅修改第一条
     *
     * @param collection 表名称
     * @param query      查询条件
     * @param update     修改的值
     * @return 修改的结果
     */
    public UpdateResult update(String collection, Query query, Update update) {
        return mongoTemplate.updateMulti(query, update, collection);
    }

    /**
     * 查询，select * limit 1
     *
     * @param collection  表名称
     * @param query       查询条件
     * @param resultClass 返回的值类型
     * @return 返回结果
     */
    public <T> T findOne(String collection, Query query, Class<T> resultClass) {
        return mongoTemplate.findOne(query, resultClass, collection);
    }

    /**
     * 查询，select *
     *
     * @param collection  表名称
     * @param query       查询条件
     * @param resultClass 返回的值类型
     * @param page        分页信息，等于null则查询所有
     * @return 返回结果
     */
    public <T> List<T> findList(String collection, Query query, Class<T> resultClass, Pageable page) {
        if (null != page) {
            query.with(page);
        }
        return mongoTemplate.find(query, resultClass, collection);
    }

    public <T> AggregationResults<T> aggregate(String collection, Aggregation aggregation, Class<T> output) {
        return mongoTemplate.aggregate(aggregation, collection, output);
    }
}
