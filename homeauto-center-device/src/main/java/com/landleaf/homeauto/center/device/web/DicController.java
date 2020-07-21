package com.landleaf.homeauto.center.device.web;


import com.landleaf.homeauto.center.device.service.dic.IDicService;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.dto.dic.DicQueryDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("dic")
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
    public Response<?> addDic(@RequestBody DicDTO dicDTO) {
        log.info("请求接口：{}", "/dic/add");
        log.info("请求参数：{}", dicDTO);
        Integer id = dicService.addDic(dicDTO);
        log.info("返回数据：{}", id);
        return returnSuccess(id);
    }

    /**
     * 根据条件查询字典
     *
     * @param dicQueryDTO 字典查询条件
     * @return 字典列表
     */
    @GetMapping("list")
    @ApiOperation("查询数据字典")
    public Response<?> getDicList(@RequestBody DicQueryDTO dicQueryDTO) {
        log.info("请求接口：{}", "/dic/list");
        log.info("请求参数：{}", dicQueryDTO);
        Object object = dicService.getDicList(dicQueryDTO);
        log.info("返回数据：{}", object);
        return returnSuccess(object);
    }

    /**
     * 更新数据字典
     *
     * @param id     主键ID
     * @param dicDTO 字典数据
     * @return 无返回数据
     */
    @PutMapping("update/{id}")
    @ApiOperation("修改数据字典")
    public Response<?> modifyDic(@PathVariable Integer id, @RequestBody DicDTO dicDTO) {
        log.info("请求接口：{}", "/dic/update/{id}");
        log.info("请求参数：{}", id, dicDTO);
        dicService.updateDic(id, dicDTO);
        return returnSuccess();
    }

    /**
     * 启用字典
     *
     * @param id 主键ID
     * @return 无返回数据
     */
    @PutMapping("enable/{id}")
    @ApiOperation("启用数据字典")
    public Response<?> enableDic(@PathVariable Integer id) {
        log.info("请求接口：{}", "/dic/enable/{id}");
        log.info("请求参数：{}", id);
        dicService.enableDic(id);
        return returnSuccess();
    }

    /**
     * 禁用字典
     *
     * @param id 主键ID
     * @return 无返回数据
     */
    @PutMapping("disable/{id}")
    @ApiOperation("禁用数据字典")
    public Response<?> disableDic(@PathVariable Integer id) {
        log.info("请求接口：{}", "/dic/disable/{id}");
        log.info("请求参数：{}", id);
        dicService.disableDic(id);
        return returnSuccess();
    }

    @Autowired
    public void setDicService(IDicService dicService) {
        this.dicService = dicService;
    }
}
