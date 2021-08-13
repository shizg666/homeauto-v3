package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.dto.address.AreaDTO;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoArea;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 行政区表 Mapper 接口
 * </p>
 *
 */
@Mapper
public interface AreaMapper extends BaseMapper<HomeAutoArea> {

    /**
     *
     * WITH RECURSIVE r AS ( SELECT home_auto_area.code, home_auto_area.parent_code, home_auto_area.NAME, home_auto_area.TYPE FROM home_auto_area WHERE code = '110115' UNION ALL
     * 	SELECT
     * 		home_auto_area.code,
     * 		home_auto_area.parent_code,
     * 		home_auto_area.NAME,
     * 		home_auto_area.TYPE
     * 	FROM
     * 		home_auto_area,
     * 		r
     * 	WHERE
     * 		home_auto_area.code = r.parent_code
     * 	) SELECT
     * 	string_agg ( code, '/' )
     * FROM
     * 	( SELECT code FROM r ORDER BY TYPE ) n;
     *
     * 获取地区路径path
     * @param code
     * @return
     */
    @Select("  WITH RECURSIVE r AS (\n" +
            "\tSELECT\n" +
            "\t\thome_auto_area.code,\n" +
            "\t\thome_auto_area.parent_code,\n" +
            "\t\thome_auto_area.NAME,\n" +
            "\t\thome_auto_area.TYPE\n" +
            "\tFROM\n" +
            "\t\thome_auto_area\n" +
            "\tWHERE\n" +
            "\t\tcode = #{code} UNION ALL\n" +
            "\tSELECT\n" +
            "\t\thome_auto_area.code,\n" +
            "\t\thome_auto_area.parent_code,\n" +
            "\t\thome_auto_area.NAME,\n" +
            "\t\thome_auto_area.TYPE\n" +
            "\tFROM\n" +
            "\t\thome_auto_area,\n" +
            "\t\tr\n" +
            "\tWHERE\n" +
            "\t\thome_auto_area.code = r.parent_code\n" +
            "\t) SELECT\n" +
            "\tstring_agg ( code, '/' )\n" +
            "FROM\n" +
            "\t( SELECT code FROM r ORDER BY TYPE ) n;")
    String getAreaPath(@Param("code") String code);

    /**
     * 获取地区path名称
     * @param code
     * @return
     */
    @Select(" WITH RECURSIVE r AS (\n" +
            "\t      SELECT\n" +
            "\t\thome_auto_area.code,\n" +
            "\t\thome_auto_area.parent_code,\n" +
            "\t\thome_auto_area.NAME,\n" +
            "\t\thome_auto_area.TYPE\n" +
            "\tFROM\n" +
            "\t\thome_auto_area\n" +
            "\tWHERE\n" +
            "\t\tcode = #{code} UNION ALL\n" +
            "\tSELECT\n" +
            "\t\thome_auto_area.code,\n" +
            "\t\thome_auto_area.parent_code,\n" +
            "\t\thome_auto_area.NAME,\n" +
            "\t\thome_auto_area.TYPE\n" +
            "\tFROM\n" +
            "\t\thome_auto_area,\n" +
            "\t\tr\n" +
            "\tWHERE\n" +
            "\t\thome_auto_area.code = r.parent_code\n" +
            "\t    ) SELECT\n" +
            "\tstring_agg ( NAME, '' )\n" +
            "FROM\n" +
            "\t( SELECT NAME FROM r ORDER BY TYPE ) n;")
    String getAreaPathName(@Param("code")String code);

    List<AreaDTO> getAreafilterProject(@Param("code") String code, @Param("type") int type);

    List<CascadeVo> cascadeList();
}
