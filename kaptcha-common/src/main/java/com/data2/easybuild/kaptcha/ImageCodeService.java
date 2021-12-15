package com.data2.easybuild.kaptcha;

/**
 * @author data2
 */
public interface ImageCodeService {
    ImageCode makeCode();

    boolean saveCode(CodeEntity codeEntity);

    CodeEntity getCode(String codeId);

}
