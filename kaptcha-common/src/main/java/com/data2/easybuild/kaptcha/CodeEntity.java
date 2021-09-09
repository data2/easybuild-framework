package com.data2.easybuild.kaptcha;

/**
 * backend entity
 */
public class CodeEntity {
    /**
     * front make codeId
     */
    private String codeId;
    private String validateCode;

    CodeEntity() {

    }

    CodeEntity(String codeId, String validateCode) {
        this.codeId = codeId;
        this.validateCode = validateCode;
    }

    public String getCodeId() {
        return codeId;
    }

    public CodeEntity setCodeId(String codeId) {
        this.codeId = codeId;
        return this;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public CodeEntity setValidateCode(String validateCode) {
        this.validateCode = validateCode;
        return this;
    }
}
