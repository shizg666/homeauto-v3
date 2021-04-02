package com.landleaf.homeauto.center.device.chain.screen.status.config;

import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealChain;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName ScreenStatusDealChainConfig
 * @Description: 大屏上传状态处理链构建
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Configuration
public class ScreenStatusDealChainConfig {

    @Resource
    private List<ScreenStatusDealHandle> screenStatusDealHandleList;
    @Bean
    public ScreenStatusDealChain screenStatusDealChain(){
        screenStatusDealHandleList.sort(Comparator.comparing(ScreenStatusDealHandle::getOrder));
        ScreenStatusDealChain statusDealChain = new ScreenStatusDealChain();
        statusDealChain.setHandle(screenStatusDealHandleList.get(0));
        int size = screenStatusDealHandleList.size();
        for (int i = 0; i < size; i++) {
            ScreenStatusDealHandle screenStatusDealHandle = screenStatusDealHandleList.get(i);
            if(i+1<size){
                screenStatusDealHandle.setNext(screenStatusDealHandleList.get(i+1));
            }
        }
        return statusDealChain;
    }
}


