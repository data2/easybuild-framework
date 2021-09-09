package com.data2.easybuild.api.common.dto;

import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午8:51
 */
@Data
@NoArgsConstructor
public class PageParam extends AbstractParamInput{
    private Integer pageNum;
    private Integer pageSize;
    @Override
    public void check() {
        ParamUtil.nullThrowException(pageNum, "页码不能为空!");
        ParamUtil.nullThrowException(pageSize,"分页大小不能为空!");
    }
}
