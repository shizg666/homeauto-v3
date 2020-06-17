package com.landleaf.homeauto.common.mysql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 无任何意义，只是把baseMapper的方法再转一次，方便编码
 *
 * @param <T> 任意domain，必须存在@Data的注解
 * @author wenyilu
 */
public interface HomeAutoBaseMapper<T> extends BaseMapper<T> {
    /**
     * 新增一条数据
     *
     * @param entity 新增的数据
     */
    @Override
    int insert(T entity);

    /**
     * 根据id，删除一条数据
     *
     * @param id 数据编号
     */
    @Override
    int deleteById(Serializable id);

    /**
     * 根据id的集合批量删除数据
     *
     * @param idList 编号的集合信息
     */
    @Override
    int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 根据id修改数据
     *
     * @param entity 修改的数据
     */
    @Override
    int updateById(@Param(Constants.ENTITY) T entity);

    /**
     * 鏍规嵁 whereEntity 鏉′欢锛屾洿鏂拌褰�
     *
     * @param entity        修改的数据
     * @param updateWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null,閲岄潰鐨� entity 鐢ㄤ簬鐢熸垚 where 璇彞锛�
     */
    int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

    /**
     * 根据id查询数据
     *
     * @param id 数据的编号
     */
    T selectById(Serializable id);

    /**
     * 根据id的集合批量查询数据
     *
     * @param idList id的集合
     */
    List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

    /**
     * 根据map信息查询数据
     *
     * @param columnMap map信息
     */
    List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

    /**
     * 鏍规嵁 entity 鏉′欢锛屾煡璇竴鏉¤褰�
     *
     * @param queryWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null锛�
     */
    T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 鏍规嵁 Wrapper 鏉′欢锛屾煡璇㈡�昏褰曟暟
     *
     * @param queryWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null锛�
     */
    Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 鏍规嵁 entity 鏉′欢锛屾煡璇㈠叏閮ㄨ褰�
     *
     * @param queryWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null锛�
     */
    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 鏍规嵁 Wrapper 鏉′欢锛屾煡璇㈠叏閮ㄨ褰�
     *
     * @param queryWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null锛�
     */
    List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 鏍规嵁 Wrapper 鏉′欢锛屾煡璇㈠叏閮ㄨ褰�
     * <p>
     * 娉ㄦ剰锛� 鍙繑鍥炵涓�涓瓧娈电殑鍊�
     * </p>
     *
     * @param queryWrapper 瀹炰綋瀵硅薄灏佽鎿嶄綔绫伙紙鍙互涓� null锛�
     */
    List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 分页查询
     *
     * @param page         分页西悉尼
     * @param queryWrapper 查询条件
     */
    IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据wap
     *
     * @param page         鍒嗛〉鏌ヨ鏉′欢
     * @param queryWrapper 数据的wrap信息
     */
    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * 根据map信息，删除数据
     *
     * @param columnMap 数据信息
     */
    int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);

    /**
     * 根据wrap删除数据
     *
     * @param wrapper 数据的wrap信息
     */
    int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

}