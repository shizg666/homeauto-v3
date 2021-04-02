package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.common.domain.po.device.DicTagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Mapper
public interface DicTagMapper extends BaseMapper<DicTagPO> {
    /**
     * 获取场景图片
     * @param code
     * @return
     */

    @Select("select t.name,t.value as url from sys_dic_tag t where t.dic_code = #{code} and enabled = '1' ORDER BY sort asc")
    List<PicVO> getListScenePic(@Param("code") String code);
}
