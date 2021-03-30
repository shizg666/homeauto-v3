package com.landleaf.homeauto.contact.screen.client.mqtt.handle;

import com.landleaf.homeauto.mqtt.container.ExceptorAcceptor;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler implements ExceptorAcceptor {
    @Override
    public void accept(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
