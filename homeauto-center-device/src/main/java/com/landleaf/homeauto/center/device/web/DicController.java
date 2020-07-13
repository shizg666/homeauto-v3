package com.landleaf.homeauto.center.device.web;


import com.landleaf.homeauto.center.device.service.dic.IDicService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.landleaf.homeauto.common.controller.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@RestController
@RequestMapping("/device/dic")
public class DicController extends BaseController {

    @Autowired
    private IDicService dicService;

    @PostMapping("add")
    public Response<?> addDic(@RequestBody DicDTO dicDTO) {
        try {
            Integer integer = dicService.addDic(dicDTO);
            return returnSuccess(integer);
        } catch (Exception ex) {
            return handlerException(ex);
        }
    }

    @GetMapping("list")
    public Response<?> getDicList(String name, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            BasePageVO<DicVO> dicList = dicService.getDicList(name, pageNum, pageSize);
            return returnSuccess(dicList);
        } catch (Exception ex) {
            return handlerException(ex);
        }
    }

    @PutMapping("update/{id}")
    public Response<?> modifyDic(@PathVariable Integer id, @RequestBody DicDTO dicDTO) {
        try {
            dicService.updateDic(id, dicDTO);
            return returnSuccess();
        } catch (Exception ex) {
            return handlerException(ex);
        }
    }
}
