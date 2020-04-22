package com.soft1851.music.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.soft1851.music.admin.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author wl
 * @ClassNameCapthchaController
 * @Description TODO
 * @Date 2020/4/21
 * @Versio 1.0
 */
@RestController
@Slf4j
public class CapthchaController {
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisService redisService;

    @GetMapping("/captcha")
    public void defaultCaptcha(String uuid) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        assert sra != null;
        HttpServletResponse response = sra.getResponse();
        String text = defaultKaptcha.createText();
        log.info(text);

//生成图片通过response输出到客户端
        BufferedImage image = defaultKaptcha.createImage(text);
        assert response != null;
        response.setContentType("image/jpeg");
        response.setDateHeader("Expires", 0);
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
            //过期时间等等
            redisService.set(uuid, text, 1L);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error("图片传输异常");
            e.printStackTrace();
        }
    }
}
