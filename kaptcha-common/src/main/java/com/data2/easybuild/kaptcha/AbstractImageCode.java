package com.data2.easybuild.kaptcha;

public abstract class AbstractImageCode implements ImageCodeService {
    @Override
    public ImageCode makeCode() {
        return new ImageCode().createImage();
    }

}
