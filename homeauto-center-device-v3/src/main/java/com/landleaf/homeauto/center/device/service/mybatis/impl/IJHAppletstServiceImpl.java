package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.BindFamilyDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JzappletesUserDTO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IJHAppletsrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class IJHAppletstServiceImpl implements IJHAppletsrService {

    @Autowired(required = false)
    private UserRemote userRemote;

    @Override
    public JzappletesUserDTO addUser(JzappletesUserDTO request) {
        return null;
    }

    @Override
    public Long bindFamily(BindFamilyDTO request) {
        return null;
    }
}
