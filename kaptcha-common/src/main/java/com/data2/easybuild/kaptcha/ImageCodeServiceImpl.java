package com.data2.easybuild.kaptcha;

public abstract class ImageCodeServiceImpl implements ImageCodeService {
    @Override
    public ImageCode makeCode() {
        return new ImageCode().createImage();
    }

}
