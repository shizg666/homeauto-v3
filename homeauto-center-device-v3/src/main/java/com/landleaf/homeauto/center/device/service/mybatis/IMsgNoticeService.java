package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.dto.msg.*;

import java.util.List;


/**
 * <p>
 * 公告消息表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
public interface IMsgNoticeService extends IService<MsgNoticeDO> {


    /**
     *  新增公告消息
     * @param msgWebSaveOrUpdateDTO
     * @return
     */
    void saveMsgNotice(MsgWebSaveDTO msgWebSaveOrUpdateDTO);

        /**
     * 查询MsgNoticeWebDTO
     * @param msgWebQry
     * @return
     */
    PageInfo<MsgNoticeWebDTO> queryMsgNoticeWebDTOList(MsgWebQry msgWebQry);

    /**
     * 根据id删除消息
     * @param ids
     */
    void deleteMsgByIds(List<String> ids);


    /**
     * 发布公告
     * @param id
     * @param releaseFlag
     */
    void releaseState(String id, Integer releaseFlag) throws Exception;


    /**
     * 修改公告消息
     * @param requestBody
     * @return
     */
    void updateMsg(MsgWebUpdateDTO requestBody);

    /**
     * 大屏查询消息
     * @param projectId
     */
    List<MsgNoticeDO> queryMsgNoticeByProjectIdForScreen(String projectId);

    /**
     *  App获取家庭消息列表
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO>
     * @author wenyilu
     * @date 2021/1/12 13:27
     */
    List<MsgNoticeAppDTO> getMsgList4VO(Long familyId);
}
