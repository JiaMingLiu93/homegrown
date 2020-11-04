package com.demo.test.code.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author youyu
 */
public abstract class AbstractRequest extends ApiBean{
    @ApiModelProperty(
            hidden = true
    )
    private String reqId;
    @ApiModelProperty(
            hidden = true
    )
    private String callerIp;
    @ApiModelProperty(
            hidden = true
    )
    private String callerType;
    @ApiModelProperty(
            hidden = true
    )
    private Integer tenantId;
    @ApiModelProperty(
            hidden = true
    )
    private Map<String, String> extra;
    @ApiModelProperty(
            hidden = true
    )
    private String updatedBy;

    @ApiModelProperty(
            hidden = true
    )
    public abstract OperationType getOperationType();
}
