package com.landleaf.homeauto.center.id.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.id.common.model.domain.IdAlloc;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface IdAllocMapper extends BaseMapper<IdAlloc> {

    @Select("SELECT biz_tag, max_id, step, update_time FROM id_alloc")
    @Results(value = {
            @Result(column = "biz_tag", property = "bizTag"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<IdAlloc> getAllLeafAllocs();

    @Select("SELECT biz_tag, max_id, step FROM id_alloc WHERE biz_tag = #{tag}")
    @Results(value = {
            @Result(column = "biz_tag", property = "bizTag"),
            @Result(column = "max_id", property = "maxId"),
            @Result(column = "step", property = "step")
    })
    IdAlloc getIdAlloc(@Param("tag") String tag);

    @Update("UPDATE id_alloc SET max_id = max_id + step WHERE biz_tag = #{tag}")
    void updateMaxId(@Param("tag") String tag);

    @Update("UPDATE id_alloc SET max_id = max_id + #{step} WHERE biz_tag = #{bizTag}")
    void updateMaxIdByCustomStep(@Param("step") int step, @Param("bizTag") String bizTag);

    @Select("SELECT biz_tag FROM id_alloc")
    List<String> getAllTags();
}
