package com.data2.easybuild.kaptcha;

import java.util.UUID;

public abstract class AbstractImageCode implements ImageCodeService {
    @Override
    public ImageCode makeCode() {
        return new ImageCode(UUID.randomUUID().toString()).createImage();
    }

}
