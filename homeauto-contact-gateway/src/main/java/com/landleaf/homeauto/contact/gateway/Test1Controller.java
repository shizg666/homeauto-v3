package com.landleaf.homeauto.contact.gateway;


import com.landleaf.homeauto.contact.gateway.entity.Test1;
import com.landleaf.homeauto.contact.gateway.mapper.Test1Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/test/test1")
@Slf4j
public class Test1Controller  {
    @Autowired
    Test1Mapper test1Mapper;

    @GetMapping("/test")
    public void test(){
        Test1 test = new Test1();
        test.setAge(23);
        test1Mapper.insert(test);

        Test1 test2 = test1Mapper.selectById(test.getId());
        log.info("按id查询:"+test2);
    }

}
