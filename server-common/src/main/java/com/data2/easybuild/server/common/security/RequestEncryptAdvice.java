package com.data2.easybuild.server.common.security;

import com.data2.easybuild.server.common.util.RsaUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author data2
 * @description
 * @date 2021/3/15 下午4:22
 */
@ControllerAdvice
@ConditionalOnProperty(prefix = "easy.security.encrypt-request", name = "enable", havingValue = "true")
public class RequestEncryptAdvice extends RequestBodyAdviceAdapter implements InitializingBean {
    private static String privateKey;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        String httpBody = StreamUtils.copyToString(inputMessage.getBody(), Charset.defaultCharset());
        if (judgeEnable(parameter)){
            try {
                httpBody = RsaUtil.decryptByPrivateKey(privateKey, httpBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new EncryptRequestHttpMessage(new ByteArrayInputStream(httpBody.getBytes()), inputMessage.getHeaders());

    }

    private boolean judgeEnable(MethodParameter parameter) {
        if (parameter.getMethodAnnotation(EncryptRequest.class) != null) {
            return true;
        }
        if (parameter.getContainingClass().getDeclaredAnnotation(EncryptRequest.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        privateKey =  IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("key/private_key.txt"), Charset.defaultCharset());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public class EncryptRequestHttpMessage implements HttpInputMessage {
        private InputStream body;
        private HttpHeaders httpHeaders;

        @Override
        public InputStream getBody() {
            return this.body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }
    }
}
