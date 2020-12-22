package com.data2.easybuild.api.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午8:52
 */
@Data
@NoArgsConstructor
public abstract class AbstractRequest implements Input{

    private String uuid;

    private Map<String,String> extra;

    @Override
    public void check() {
    }
}
