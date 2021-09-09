package com.data2.easybuild.kaptcha;

public interface ImageCodeService {
    ImageCode makeCode();

    boolean saveCode(CodeEntity codeEntity);

    CodeEntity getCode(String codeId);

}
