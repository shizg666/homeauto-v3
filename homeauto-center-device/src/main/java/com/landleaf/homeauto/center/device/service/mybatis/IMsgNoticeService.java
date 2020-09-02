package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeWebDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebQry;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebSaveOrUpdateDTO;
import com.landleaf.homeauto.common.domain.qry.MsgQry;


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
     * 根据id查询
     * @param id
     * @return
     */
//    MsgNoticeWebDTO queryMsgNoticeWebDTO(String id);

    /**
     *  新增公告消息
     * @param msgWebSaveOrUpdateDTO
     * @return
     */
    void saveMsgNotice(MsgWebSaveOrUpdateDTO msgWebSaveOrUpdateDTO);

        /**
     * 查询MsgNoticeWebDTO
     * @param msgWebQry
     * @return
     */
    PageInfo<MsgNoticeWebDTO> queryMsgNoticeWebDTOList(MsgWebQry msgWebQry);

    /**
     * 修改公告消息
     * @param msgNoticeWebDTO
     * @return
     */
//    void updateMsgNotice(MsgNoticeWebDTO msgNoticeWebDTO);
//
//    /**
//     * 根据id删除消息
//     * @param id
//     */
//    void deleteMsgNotice(String id);
//
//
//    /**
//     * 发布公告
//     * @param id
//     * @param releaseFlag
//     */
//    void releaseState(String id, Integer releaseFlag);


}
