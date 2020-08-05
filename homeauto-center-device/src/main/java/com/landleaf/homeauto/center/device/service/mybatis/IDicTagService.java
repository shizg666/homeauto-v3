package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.device.DicTagDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicTagQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicTagPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagForAppVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagVO;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
public interface IDicTagService extends IService<DicTagPO> {

    /**
     * 添加字典标签
     *
     * @param dicTagDTO
     * @return
     */
    String addDicTag(DicTagDTO dicTagDTO, String operator);

    /**
     * 启用
     *
     * @param id
     */
    void enable(String id, String operator);

    /**
     * 禁用
     *
     * @param id
     */
    void disable(String id, String operator);

    /**
     * 更新
     *
     * @param dicTagDTO
     */
    void update(DicTagDTO dicTagDTO, String operator);

    /**
     * 查询字典标签
     *
     * @param dicTagQueryDTO
     * @return
     */
    BasePageVO<DicTagVO> getDicTagList(DicTagQueryDTO dicTagQueryDTO);

    /**
     *
     * @param dicCode
     * @return
     */
    List<DicTagForAppVO> getDicTagList(String dicCode);
}
