package com.soft1851.music.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.soft1851.music.admin.annotation.ControllerWebLog;
import com.soft1851.music.admin.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.TransactionUsageException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wl
 * @ClassNameCapthchaController
 * @Description TODO
 * @Date 2020/4/21
 * @Versio 1.0
 */
@RestController
@Slf4j
@RequestMapping("/api")
@Validated
public class CapthchaController {
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisService redisService;

    @GetMapping("/captcha")
    @ControllerWebLog(name = "验证码",isSaved = true)
    public void defaultCaptcha(@Valid @NotBlank(message = "uuid不能为空") String uuid) {
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
