package com.landleaf.homeauto.contact.screen.client.mqtt.handle;

import com.landleaf.homeauto.mqtt.container.OnCloseListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author pilo
 */
@Slf4j
@Component
public class OnCloseHandler implements OnCloseListener {
    @Override
    public void start() {
        log.info("啥子都不做,关呗~~~~~");
    }
}
