package com.demo.middleware.canal.message;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author youyu
 */
@Data
public class StandardRowData implements Serializable {
    private Long id;
    private Map<String,Object> columns;
    private Map<String,Object> preColumns;
}
