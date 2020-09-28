package com.landleaf.homeauto.center.websocket.controller;

import com.landleaf.homeauto.center.websocket.model.WebSocketSessionContext;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Yujiumin
 * @version 2020/9/25
 */
@RestController
@RequestMapping("/session")
public class SessionController extends BaseController {

    @GetMapping("/family/list")
    public Response<Set<String>> sessionList() {
        return returnSuccess(WebSocketSessionContext.getFamilyIdList());
    }

}
