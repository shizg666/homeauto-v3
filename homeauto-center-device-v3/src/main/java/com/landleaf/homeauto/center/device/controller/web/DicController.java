package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IDicService;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.DicDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicQueryDTO;
import com.landleaf.homeauto.common.domain.dto.device.SingleParamIdDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@Slf4j
@RestController
@RequestMapping("web/dic")
@Api(tags = "数据字典")
public class DicController extends BaseController {

    private IDicService dicService;

    /**
     * 添加字典
     *
     * @param dicDTO 字典信息
     * @return 主键值
     */
    @PostMapping("add")
    @ApiOperation("添加数据字典")
    public Response<String> addDic(@RequestBody DicDTO dicDTO) {
        log.info("请求接口：{}", "/dic/add");
        log.info("请求参数：{}", dicDTO);
        String id = dicService.save(dicDTO);
        log.info("返回数据：{}", id);
        return returnSuccess(id);
    }

    /**
     * 修改字典
     *
     * @param dicDTO 字典信息
     * @return 修改结果
     */
    @PostMapping("edit")
    @ApiOperation("修改数据字典")
    public Response<?> updateDic(@RequestBody DicDTO dicDTO) {
        log.info("请求接口：{}", "/dic/update");
        log.info("请求参数：{}", dicDTO);
        dicService.updateDic(dicDTO);
        return returnSuccess();
    }

    /**
     * 启用字典
     *
     * @param enableDTO 主键ID
     * @return 无返回数据
     */
    @PostMapping("enable")
    @ApiOperation("启用数据字典")
    public Response<?> enableDic(@RequestBody SingleParamIdDTO enableDTO) {
        log.info("请求接口：{}", "/dic/enable/{id}");
        log.info("请求参数：{}", enableDTO);
        dicService.enableDic(enableDTO.getId());
        return returnSuccess();
    }

    /**
     * 禁用字典
     *
     * @param disableDTO 禁用字典数据对象
     * @return 无返回数据
     */
    @PostMapping("disable")
    @ApiOperation("禁用数据字典")
    public Response<?> disableDic(@RequestBody SingleParamIdDTO disableDTO) {
        log.info("请求接口：{}", "/dic/disable");
        log.info("请求参数：{}", disableDTO);
        dicService.disableDic(disableDTO.getId());
        return returnSuccess();
    }

    /**
     * 设置字典为系统字典
     *
     * @param enableDTO 字典id
     * @return 无返回数据
     */
    @PostMapping("sys/enable")
    @ApiOperation("设置字典为系统字典")
    public Response<?> enableSystemDic(@RequestBody SingleParamIdDTO enableDTO) {
        log.info("请求接口：{}", "/dic/sys/enable");
        log.info("请求参数：{}", enableDTO);
        dicService.enableSystemDic(enableDTO.getId());
        return returnSuccess();
    }

    /**
     * 取消字典为系统字典
     *
     * @param disableDTO 字典ID
     * @return 无返回数据
     */
    @PostMapping("sys/disable")
    @ApiOperation("取消字典为系统字典")
    public Response<?> disableSystemDic(@RequestBody SingleParamIdDTO disableDTO) {
        log.info("请求接口：{}", "/dic/sys/disable");
        log.info("请求参数：{}", disableDTO);
        dicService.cancelSystemDic(disableDTO.getId());
        return returnSuccess();
    }

    /**
     * 查询字典表
     *
     * @param dicQueryDTO 查询条件
     * @return
     */
    @PostMapping("list")
    @ApiOperation("查询字典表")
    public Response<BasePageVO<DicVO>> dicList(@RequestBody DicQueryDTO dicQueryDTO) {
        log.info("请求接口：{}", "/dic/list");
        log.info("请求参数：{}", dicQueryDTO);
        return returnSuccess(dicService.getDicList(dicQueryDTO));
    }

    @Autowired
    public void setDicService(IDicService dicService) {
        this.dicService = dicService;
    }
}
