package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.device.DicDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;

/**
 * <p>
 * 字典服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
public interface IDicService extends IService<DicPO> {

    /**
     * 添加字典
     *
     * @param dicDTO   字典表数据
     * @return 数据库主键
     */
    Long save(DicDTO dicDTO);

    /**
     * 启用字典
     *
     * @param id       字典表主键
     */
    void enableDic(Long id);

    /**
     * 禁用字典
     *
     * @param id       字典表主键
     */
    void disableDic(Long id);

    /**
     * 更新字典表
     *
     * @param dicDTO   字典表信息
     */
    void updateDic(DicDTO dicDTO);

    /**
     * 开启系统字典码
     *
     * @param id       字典表主键
     */
    void enableSystemDic(Long id);

    /**
     * 取消系统字典码
     *
     * @param id       字典表主键
     */
    void cancelSystemDic(Long id);

    /**
     * 查询字典表
     *
     * @param dicQueryDTO 查询条件
     * @return 带分页的数据字典列表
     */
    BasePageVO<DicVO> getDicList(DicQueryDTO dicQueryDTO);

    /**
     * 删除id
     * @param dicId
     */
    void deleteById(Long dicId);
}
