package com.landleaf.homeauto.common.web.wrapper;

import com.landleaf.homeauto.common.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 重写httpServletRequest
 *
 * @author wenyilu
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    // 报文
    private byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        try {
            body = StreamUtils.getByteByStream(request.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

}

