package com.landleaf.homeauto.common.domain.vo.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class FileVO implements Serializable {

    private MultipartFile file;

    private String typeName;


}
