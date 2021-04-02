package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author wenyilu
 */
@ApiModel(value = "ScreenApkConditionDTO",description = "大屏apk查询条件源")
@Data
public class ScreenApkConditionDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;

    @ApiModelProperty(value = "应用版本号",required = true)
    private Set<SelectedVO> versionCode;
    @ApiModelProperty(value = "上传人",required = true)
    private Set<SelectedVO> uploadUser;
    @ApiModelProperty(value = "应用名称",required = true)
    private Set<SelectedVO> name;





}
