package com.landleaf.homeauto.common.domain.vo.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("导入协议属性")
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolFileVO implements Serializable {


    @ApiModelProperty(value = "协议文件")
    private MultipartFile file;

    @NotBlank
    @ApiModelProperty(value = "协议名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "协议标志")
    private String code;

    @NotNull
    @ApiModelProperty(value = "协议场景类型")
    private Integer type;



}
