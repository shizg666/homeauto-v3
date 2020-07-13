package com.landleaf.homeauto.center.device.service.dic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import io.swagger.models.auth.In;

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
     * @param dicDTO
     * @return
     */
    Integer addDic(DicDTO dicDTO);

    /**
     * 查询字典
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    BasePageVO<DicVO> getDicList(String name, Integer pageNum, Integer pageSize);

    /**
     * 更新字典
     *
     * @param id
     * @param dicDTO
     */
    void updateDic(Integer id, DicDTO dicDTO);


    /**
     * 启用字典
     *
     * @param id
     */
    void enableDic(Integer id);

    /**
     * 禁用字典
     *
     * @param id
     */
    void disableDic(Integer id);
}
