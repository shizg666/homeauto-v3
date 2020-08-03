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
     * @param operator 操作人
     * @return 数据库主键
     */
    String save(DicDTO dicDTO, String operator);

    /**
     * 启用字典
     *
     * @param id       字典表主键
     * @param operator 操作人
     */
    void enableDic(String id, String operator);

    /**
     * 禁用字典
     *
     * @param id       字典表主键
     * @param operator 操作人
     */
    void disableDic(String id, String operator);

    /**
     * 更新字典表
     *
     * @param dicDTO   字典表信息
     * @param operator 操作人
     */
    void updateDic(DicDTO dicDTO, String operator);

    /**
     * 开启系统字典码
     *
     * @param id       字典表主键
     * @param operator 操作人
     */
    void enableSystemDic(String id, String operator);

    /**
     * 取消系统字典码
     *
     * @param id       字典表主键
     * @param operator 操作人
     */
    void cancelSystemDic(String id, String operator);

    /**
     * 查询字典表
     *
     * @param dicQueryDTO 查询条件
     * @return 带分页的数据字典列表
     */
    BasePageVO<DicVO> getDicList(DicQueryDTO dicQueryDTO);

}
