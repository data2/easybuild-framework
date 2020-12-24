package com.data2.easybuild.api.common.rest.dto;

import com.data2.easybuild.api.common.dto.PageParam;
import com.data2.easybuild.api.common.utils.ParamUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author data2
 * @description
 * @date 2020/11/29 下午9:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPageQueryRequest extends AbstractQueryRequest {
    private PageParam pageParam;

    @Override
    public void check() {
        super.check();
        ParamUtil.isNull(pageParam,"分页参数不能为空");
        pageParam.check();
    }
}
