package com.landleaf.homeauto.common.domain.vo.dic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicTagForAppVO {

    private String label;

    private String value;

    private List<DicTagForAppVO> children;

}
