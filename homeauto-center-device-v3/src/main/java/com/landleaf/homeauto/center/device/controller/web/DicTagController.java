package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.service.mybatis.IDicTagService;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagForAppVO;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.DicTagDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicTagQueryDTO;
import com.landleaf.homeauto.common.domain.dto.device.SingleParamIdDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Slf4j
@RestController
@RequestMapping("web/dic/tag")
@Api(tags = "字典标签")
public class DicTagController extends BaseController {

    private IDicTagService dicTagService;

    @PostMapping("add")
    @ApiOperation("添加字典标签")
    public Response<Long> add(@RequestBody DicTagDTO dicTagDTO) {
        return returnSuccess(dicTagService.addDicTag(dicTagDTO));
    }

    @PostMapping("enable")
    @ApiOperation("启用字典标签")
    public Response<?> enable(@RequestBody SingleParamIdDTO singleParamIdDTO) {
        dicTagService.enable(singleParamIdDTO.getId());
        return returnSuccess();
    }

    @PostMapping("disable")
    @ApiOperation("禁用字典标签")
    public Response<?> disable(@RequestBody SingleParamIdDTO singleParamIdDTO) {
        dicTagService.disable(singleParamIdDTO.getId());
        return returnSuccess();
    }

    @PostMapping("edit")
    @ApiOperation("编辑字典标签")
    public Response<?> update(@RequestBody DicTagDTO dicTagDTO) {
        dicTagService.update(dicTagDTO);
        return returnSuccess();
    }

    @PostMapping("list")
    @ApiOperation("查询字典标签")
    public Response<?> list(@RequestBody DicTagQueryDTO dicTagQueryDTO) {
        BasePageVO<DicTagVO> dicTagList = dicTagService.getDicTagList(dicTagQueryDTO);
        return returnSuccess(dicTagList);
    }

    @GetMapping("list/app")
    @ApiOperation("查询启用的字典标签")
    public Response<?> listEnabled(@RequestParam String dicCode) {
        List<DicTagForAppVO> dicTagList = dicTagService.getDicTagList(dicCode);
        return returnSuccess(dicTagList);
    }


    @Autowired
    public void setDicTagService(IDicTagService dicTagService) {
        this.dicTagService = dicTagService;
    }
}
