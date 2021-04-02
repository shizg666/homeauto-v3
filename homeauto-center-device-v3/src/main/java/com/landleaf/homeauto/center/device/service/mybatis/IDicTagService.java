package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
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
    String addDicTag(DicTagDTO dicTagDTO);

    /**
     * 启用
     *
     * @param id
     */
    void enable(String id);

    /**
     * 禁用
     *
     * @param id
     */
    void disable(String id);

    /**
     * 更新
     *
     * @param dicTagDTO
     */
    void update(DicTagDTO dicTagDTO);


    /**
     *
     * @param dicCode
     * @param dicId
     */
    void updateDicCodeByDicId(String dicCode, String dicId);

    /**
     * 查询字典标签
     *
     * @param dicTagQueryDTO
     * @return/list/app
     */
    BasePageVO<DicTagVO> getDicTagList(DicTagQueryDTO dicTagQueryDTO);

    /**
     * @param dicCode
     * @return
     */
    List<DicTagForAppVO> getDicTagList(String dicCode);

    /**获取场景图片集合
     * @param
     * @return
     */
    List<PicVO> getListScenePic();


}
