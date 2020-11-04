package com.demo.middleware.canal.message;

import com.demo.middleware.canal.enums.OperateType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author youyu
 */
@Data
public class StandardMessage implements Serializable {
    private String domainName;
    private OperateType operateType;
    private List<StandardRowData> data;
}
