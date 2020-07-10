package com.landleaf.homeauto.center.device.domain.msg.jpush;

import com.landleaf.homeauto.common.enums.jg.JpushContentEnum;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName JpushMsgTask
 * @Description: 极光推送任务
 * @Author ljy88
 * @Date 2020/4/20
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"msgId"})
@ApiModel("极光推送任务")
public class JpushMsgTask {

    @ApiModelProperty("随机生成的消息id,标识")
    private String msgId;

    @ApiModelProperty("推送类型")
    private Integer type;

    @ApiModelProperty("需推送的内容")
    private JpushMsg jpushMsg;

    @ApiModelProperty("推送对象")
    private List<String> tags;

    /**
     * type为1的时候使用
     */
    @ApiModelProperty("数据库主键")
    private List<String> dbIds;


    public JpushMsgTask(JpushMsg jpushMsg, List<String> dbIds, String... tagArr) {
        this.msgId = IdGeneratorUtil.getUUID32();
        this.type = JpushContentEnum.ALARM_MSG.getType();
        this.jpushMsg = jpushMsg;
        this.tags = Arrays.asList(tagArr);
        this.dbIds = dbIds;
    }

    public JpushMsgTask(JpushMsg jpushMsg, String... tagArr) {
        this.msgId = IdGeneratorUtil.getUUID32();
        this.type = JpushContentEnum.DISARM_MSG.getType();
        this.jpushMsg = jpushMsg;
        this.tags = Arrays.asList(tagArr);
    }
}
