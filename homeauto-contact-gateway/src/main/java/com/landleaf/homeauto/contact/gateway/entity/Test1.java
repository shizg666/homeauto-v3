package com.landleaf.homeauto.contact.gateway.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Test1对象", description="")
public class Test1 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Integer age;

    private LocalDate date;

    private Long money;


}
