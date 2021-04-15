package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyAddDTO", description="家庭对象")
public class
FamilyAddBatchDTO {

    @NotEmpty(message = "楼栋code不能为空")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @NotEmpty(message = "楼层不能为空")
    @ApiModelProperty(value = "楼层  start-end")
    private String floor;

    @ApiModelProperty(value = "跳过楼层 多个以，分隔")
    private String skipFloor;

    @ApiModelProperty(value = "单元信息")
    private List<UnitInfo> units;

    @NotEmpty(message = "项目Id不能为空")
    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @NotEmpty(message = "楼盘ID不能为空")
    @ApiModelProperty(value = "楼盘ID")
    private Long realestateId;

    @ApiModelProperty(value = "后端组装")
    private String path;

    @ApiModelProperty(value = "后端组装")
    private String pathName;

    public class UnitInfo{
        @ApiModelProperty(value = "房间信息")
        private List<UnitRoomInfo> rooms;
        @ApiModelProperty(value = "前缀")
        private String prefix;
        @ApiModelProperty(value = "后缀")
        private String suffix;

        public List<UnitRoomInfo> getRooms() {
            return rooms;
        }

        public void setRooms(List<UnitRoomInfo> rooms) {
            this.rooms = rooms;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }

    public class UnitRoomInfo{
        @ApiModelProperty(value = "房号")
        private String roomNo;

        @ApiModelProperty(value = "户型id")
        private Long templateId;

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public Long getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Long templateId) {
            this.templateId = templateId;
        }
    }



}
