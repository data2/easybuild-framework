package com.data2.easybuild.api.common.rest.dto;

import com.data2.easybuild.api.common.dto.AbstractParamInput;
import com.data2.easybuild.api.common.dto.Input;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserParam extends AbstractParamInput {
    @Override
    public void check() {

    }
}
