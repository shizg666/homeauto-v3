package com.landleaf.homeauto.center.device.model.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * <p>
 * 家庭维保记录分页请求对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
@ApiModel("家庭维保记录新增请求对象")
public class FamilyMaintenanceUpdateRequestDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "年月日加6位流水号")
    @NotNull
    private Long num;

    @ApiModelProperty(value = "家庭",required = true)
    @NotNull
    private Long familyId;

    @ApiModelProperty(value = "维保时间",required = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    @NotNull
    private LocalDate maintenanceTime;

    @ApiModelProperty(value = "维保类别",required = false)
    private Integer maintenanceType;

    @ApiModelProperty(value = "维保内容",required = true)
    @Length(max = 1000,message = "长度不能超过1000")
    @NotNull
    private String content;

    @ApiModelProperty(value = "业主姓名",required = true)
    @NotNull
    private String owner;

    @ApiModelProperty(value = "联系方式",required = true)
    @NotNull
    private String mobile;




}
