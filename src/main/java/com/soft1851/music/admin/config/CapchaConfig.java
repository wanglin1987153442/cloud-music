package com.soft1851.music.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author wl
 * @ClassNameKapchaConfig
 * @Description Capcha的配置类
 * @Date 2020/4/21
 * @Version 1.0
 */
@Configuration

public class CapchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
//        验证码生成
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //验证码是否带边框 No
        properties.setProperty("kaptcha.border", "no");
        //验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "170,0,255");
        //验证码整体宽度
        properties.setProperty("kaptcha.image.width", "250");
        //验证码整体高度
        properties.setProperty("kaptcha.image.height", "125");
        //文字个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //文字大小
        properties.setProperty("kaptcha.textproducer.font.size", "60");
        //文字随机字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //文字距离
        properties.setProperty("kaptcha.textproducer.char.space", "16");
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color", "255,82,82");
        //自定义验证码样式
        properties.setProperty("kaptcha.obscurificator.impl", "com.soft1851.music.admin.util.DisKaptchaCssImpl");
        //自定义验证码背景
        properties.setProperty("kaptcha.background.impl", "com.soft1851.music.admin.util.NoKaptchaBackhround");
//        谷歌captcha的依赖
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
