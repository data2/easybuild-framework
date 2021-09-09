package com.data2.easybuild.example.bean;

import com.data2.easybuild.api.common.rest.dto.AbstractRestRequest;
import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author data2
 * @description
 * @date 2021/3/15 下午5:21
 */
@Data
@NoArgsConstructor
public class OrderBean extends AbstractRestRequest {
    private String orderId;
    private String action;

    @Override
    public void check() {
        super.check();
        ParamUtil.emptyThrowException(orderId, "orderId不能为空");
    }
}
