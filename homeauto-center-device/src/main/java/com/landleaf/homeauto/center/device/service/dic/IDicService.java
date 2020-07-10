package com.landleaf.homeauto.center.device.service.dic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;

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

}
