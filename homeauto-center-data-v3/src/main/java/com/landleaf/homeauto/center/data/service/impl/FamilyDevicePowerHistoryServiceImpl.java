package com.landleaf.homeauto.center.data.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.domain.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.data.domain.bo.FamilyDevicePowerDO;
import com.landleaf.homeauto.center.data.mapper.FamilyDevicePowerHistoryMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDevicePowerHistoryService;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceStatusHistoryService;
import com.landleaf.homeauto.center.data.util.DateUtil2;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备功耗表 服务实现类
 * </p>
 *
 */
@Service
@Slf4j
public class FamilyDevicePowerHistoryServiceImpl
		extends ServiceImpl<FamilyDevicePowerHistoryMapper, FamilyDevicePowerHistory>
		implements IFamilyDevicePowerHistoryService {

	@Autowired
	private FamilyDeviceEnergyDayServiceImpl energyDayService;

	@Override
	public void insertBatchDevicePower(List<FamilyDevicePowerDO> powerDOS) {
		log.info("insertBatchDevicePower(List<FamilyDevicePowerDO> powerDOS:{} ", powerDOS.toString());

		List<FamilyDevicePowerHistory> powerHistoryList = powerDOS.stream().map(s -> {
			FamilyDevicePowerHistory powerHistory = new FamilyDevicePowerHistory();
			BeanUtils.copyProperties(s, powerHistory);
			return powerHistory;
		}).collect(Collectors.toList());

		if (powerHistoryList.size() > 0) {
			saveBatch(powerHistoryList);
		}
	}

	@Override
	public Map<Long, List<FamilyDevicePowerHistory>> getGlcPowerYesterday(String startTime, String endTime) {
		LambdaQueryWrapper<FamilyDevicePowerHistory> queryWrapper = new LambdaQueryWrapper();

		queryWrapper.eq(FamilyDevicePowerHistory::getStatusCode, "glcPower");

		if (StringUtils.isEmpty(startTime)) {
			startTime = DateUtil.yesterday().toDateStr() + " 00:00:00";
		}

		if (StringUtils.isEmpty(endTime)) {
			endTime = DateUtil.yesterday().toDateStr() + " 23:59:59";
		}
		queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and "
				+ "upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");

		queryWrapper.orderByAsc(FamilyDevicePowerHistory::getUploadTime);

		List<FamilyDevicePowerHistory> list = list(queryWrapper);

		if (list.size() <= 0) {
			return null;
		}

		Map<Long, List<FamilyDevicePowerHistory>> map = list.stream()
				.collect(Collectors.groupingBy(FamilyDevicePowerHistory::getFamilyId));

		return map;
	}

	@Override
	public Map<Long, List<FamilyDevicePowerHistory>> getGlvPowerYesterday(String startTime, String endTime) {
		LambdaQueryWrapper<FamilyDevicePowerHistory> queryWrapper = new LambdaQueryWrapper();

		queryWrapper.eq(FamilyDevicePowerHistory::getStatusCode, "glvPower");

		if (StringUtils.isEmpty(startTime)) {
			startTime = DateUtil.yesterday().toDateStr() + " 00:00:00";
		}

		if (StringUtils.isEmpty(endTime)) {
			endTime = DateUtil.yesterday().toDateStr() + " 23:59:59";
		}
		queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and "
				+ "upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");

		queryWrapper.orderByAsc(FamilyDevicePowerHistory::getUploadTime);

		List<FamilyDevicePowerHistory> list = list(queryWrapper);

		if (list.size() <= 0) {
			return null;
		}

		Map<Long, List<FamilyDevicePowerHistory>> map = list.stream()
				.collect(Collectors.groupingBy(FamilyDevicePowerHistory::getFamilyId));

		return map;
	}

	@Override
	public BasePageVO<FamilyDeviceStatusHistory> getStatusByFamily(HistoryQryDTO2 historyQryDTO) {
		// 此处如果code为glcPower或者glvPower调用

		BasePageVO<FamilyDeviceStatusHistory> basePageVO1 = new BasePageVO<>();

		BasePageVO<FamilyDevicePowerHistory> basePageVO = new BasePageVO<>();

		List<FamilyDeviceStatusHistory> result2 = new ArrayList<>();

		PageHelper.startPage(historyQryDTO.getPageNum(), historyQryDTO.getPageSize(), true);

		LambdaQueryWrapper<FamilyDevicePowerHistory> queryWrapper = new LambdaQueryWrapper<>();

		Long familyId = historyQryDTO.getFamilyId();
		String sn = historyQryDTO.getDeviceSn();

		if (StringUtils.isNotBlank(historyQryDTO.getCode())) {
			queryWrapper.eq(FamilyDevicePowerHistory::getStatusCode, historyQryDTO.getCode());
		}

		if (StringUtils.isNotBlank(sn)) {
			queryWrapper.eq(FamilyDevicePowerHistory::getDeviceSn, sn);
		}
		if (familyId > 0) {
			queryWrapper.eq(FamilyDevicePowerHistory::getFamilyId, familyId);
		}

		List<String> uploadTimes = historyQryDTO.getUploadTimes();

		String startTime = "";
		String endTime = "";

		if (!CollectionUtils.isEmpty(uploadTimes) && uploadTimes.size() == 2) {
			startTime = uploadTimes.get(0);
			endTime = uploadTimes.get(1);
		}

		if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
			queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime
					+ "','YYYY-MM-DD hh24:mi:ss') and upload_time<= TO_TIMESTAMP('" + endTime
					+ "','YYYY-MM-DD hh24:mi:ss')) ");
		}

		queryWrapper.orderByAsc(FamilyDevicePowerHistory::getUploadTime);
		List<FamilyDevicePowerHistory> result = list(queryWrapper);

		if (result.size() > 0) {

			// 1.首先查出有几天
			List<String> dayList = result.stream().map(s -> {
				String day = "";
				day = DateUtil.formatDate(DateUtil2.localDateTime2Date(s.getUploadTime()));
				return day;
			}).distinct().collect(Collectors.toList());

			// 2. 按照天进行计算功耗
			for (String day : dayList) {

				// 同一天的
				List<FamilyDevicePowerHistory> dayPowerList = result.stream()
						.filter(s -> day.equals(DateUtil.formatDate(DateUtil2.localDateTime2Date(s.getUploadTime()))))
						.collect(Collectors.toList());
				String tempDay = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.parse(day), -1));// 求上一天
				double basicValue = energyDayService.getLastValue(familyId, historyQryDTO.getCode(), tempDay);
				System.out.println(basicValue + "**********");
				double todayValue = 0;

				for (int i = 0; i < dayPowerList.size() - 1; i++) {

					FamilyDevicePowerHistory iObject = dayPowerList.get(i);
					FamilyDevicePowerHistory iObject2 = dayPowerList.get(i + 1);

					todayValue += (Double.valueOf(iObject2.getStatusValue()) + Double.valueOf(iObject.getStatusValue()))
							/ 2 * (Duration.between(iObject.getUploadTime(), iObject2.getUploadTime()).toMillis()
									/ 36000.0);// 符号为wh

					FamilyDeviceStatusHistory history = new FamilyDeviceStatusHistory();
					BeanUtils.copyProperties(iObject, history);
					history.setStatusCode(iObject.getStatusCode());
					history.setStatusValue(String.valueOf((todayValue + basicValue) / 1000));// 返回千瓦時
					history.setUploadTime(iObject.getUploadTime());
					result2.add(history);
				}
			}

		}

		PageInfo pageInfo = new PageInfo(result2);

		pageInfo.setList(result2);

		basePageVO1 = BeanUtil.mapperBean(pageInfo, BasePageVO.class);

		return basePageVO1;
	}
}
