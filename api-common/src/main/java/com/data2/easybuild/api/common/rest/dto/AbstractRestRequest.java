package com.data2.easybuild.api.common.rest.dto;

import com.data2.easybuild.api.common.dto.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractRestRequest extends AbstractRequest {
    private ClientParam clientParam;
    private UserParam userParam;

    @Override
    public void check() {
        super.check();
        if (!Objects.isNull(clientParam)){
            clientParam.check();
        }
        if (!Objects.isNull(userParam)){
            userParam.check();
        }
    }
}
