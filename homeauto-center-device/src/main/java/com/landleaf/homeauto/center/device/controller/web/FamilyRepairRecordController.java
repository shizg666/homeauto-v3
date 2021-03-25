package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordAddDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordPageRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyRepairRecordEnclosureVO;
import com.landleaf.homeauto.center.device.model.vo.FamilyRepairRecordVO;
import com.landleaf.homeauto.center.device.remote.FileRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRepairRecordService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 家庭维修记录 接口
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Api(value = "FamilyRepairRecordController", tags = {"家庭维修记录API"})
@Controller
@RequestMapping("/family/repair-record")
public class FamilyRepairRecordController extends BaseController {
    @Autowired
    private IFamilyRepairRecordService familyRepairRecordService;
    @Autowired
    private FileRemote fileRemote;

    @ApiOperation(value = "新增")
    @PostMapping("add")
    @ResponseBody
    public Response addRecord(@RequestBody FamilyRepairRecordAddDTO requestDTO) {
        familyRepairRecordService.addRecord(requestDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    @ResponseBody
    public Response updateRecord(@RequestBody FamilyRepairRecordUpdateDTO requestDTO) {
        familyRepairRecordService.updateRecord(requestDTO);
        return returnSuccess();
    }

    @ResponseBody
    @ApiOperation(value = "根据主键查看详情")
    @GetMapping("detail")
    public Response<FamilyRepairRecordVO> detail(@RequestParam("id") String id) {
        return returnSuccess(familyRepairRecordService.detail(id));
    }

    @ResponseBody
    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("delete")
    public Response delete(@RequestParam("id") String id) {
        familyRepairRecordService.delete(id);
        return returnSuccess();
    }

    @ResponseBody
    @ApiOperation(value = "分页查询")
    @PostMapping("list")
    public Response<BasePageVO<FamilyRepairRecordVO>> list(@RequestBody FamilyRepairRecordPageRequestDTO requestDTO) {
        return returnSuccess(familyRepairRecordService.pageList(requestDTO));
    }

    @ResponseBody
    @ApiOperation(value = "上传附件", notes = "上传附件", produces = "multipart/form-data")
    @PostMapping(value = "/enclosure/upload")
    public Response<FamilyRepairRecordEnclosureVO> enclosureUpload(@RequestParam("file") MultipartFile file) {
        FileVO fileVO = new FileVO();
        fileVO.setTypeName("repair-enclosure");
        fileVO.setFile(file);
        String filename = file.getOriginalFilename();
        Response<Map<String, Object>> response = fileRemote.singleUpload(fileVO);
        FamilyRepairRecordEnclosureVO result = FamilyRepairRecordEnclosureVO.builder()
                .fileName(filename.substring(0, filename.lastIndexOf(".")))
                .fileType(filename.substring(filename.lastIndexOf(".") + 1, filename.length()))
                .url((String) response.getResult().get("url"))
                .build();
        return returnSuccess(result);
    }

    @ApiOperation(value = "下载附件", notes = "下载附件")
    @GetMapping(value = "/enclosure/download")
    public void enclosureUpload(@RequestParam("url") String fileUrl, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        if (fileUrl != null) {
            //下载时文件名称
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("."));
            FileInputStream fis = null;
            File file = null;
            try {
                String uuidName = UUID.randomUUID().toString();
                file = new File(uuidName + fileName);
                file.createNewFile();
                URL url = new URL(fileUrl);
                FileUtils.copyURLToFile(url, file, 30, 100);
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                fis = new FileInputStream(file);
                IOUtils.copy(fis, response.getOutputStream());
                response.flushBuffer();
                return ;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(file!=null&&file.exists()){
                    file.delete();
                }
            }
        }
    }
}
