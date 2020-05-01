package com.soft1851.music.admin.aspect;


import com.soft1851.music.admin.annotation.ControllerWebLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wl
 * @ClassNameWebLogAspect
 * @Description web 日志切面
 * @Date 2020/4/12
 * @Version 1.0
 */
@Aspect
@Component
@Order(2)
@Slf4j
public class WebLogAspect {

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    @Pointcut("execution(public * com.soft1851.music.admin.controller.CapthchaController*.*(..))")
    public void webLog() {

    }

    @Before(value = "webLog() && @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        //接受请求，取得请求的request对象
        RequestAttributes at = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) at;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        log.info("请求URI" + request.getRequestURI());
        log.info("请求头的User-Agent:" + request.getHeader("User-Agent"));
        log.info("请求头的TOKEN:" + request.getHeader("Token"));
        log.info("请求方法:" + request.getMethod());
        log.info("请求地址：" + request.getRemoteAddr());
        log.info("连接点对象通过反射获得类名和方法名：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求方法名：" + controllerWebLog.name());
        log.info("是否需要持久化该条日志：" + controllerWebLog.isSaved());
        log.info("AOP拦截获得参数:" + Arrays.toString(joinPoint.getArgs()));
        //定义一个map用来记录日志信息，并将其put入threadLocal
        Map<String, Object> map = new HashMap<>(16);
        map.put("url", request.getRequestURI());
        map.put("url", request.getRequestURL());
        map.put("user-agent", request.getHeader("User-Agent"));
        map.put("request-method", request.getMethod());
        map.put("remote-address", request.getRemoteAddr());
        map.put("class-method", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        map.put("arguments", Arrays.toString(joinPoint.getArgs()));
        map.put("execute-method", controllerWebLog.name());

        threadLocal.set(map);
    }

    //执行成功后处理
    @AfterReturning(value = "webLog() && @annotation(controllerWebLog)", returning = "ret")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object ret) {
        //从当前线程变量取出数据
        Map<String, Object> threadInfo = threadLocal.get();
        System.out.println(threadInfo);
        //将请求的目标方法的执行的返回结果存入线程对象
        threadInfo.put("result", ret);

        if (controllerWebLog.isSaved()) {
            File file = new File("F:\\dev\\SpringBoot\\cloud-music-admin\\src\\main\\resources\\log\\" + LocalDate.now() + ".txt");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            try {

             FileOutputStream fos= new FileOutputStream(file,true);
                String s = LocalDateTime.now() + ":" + threadInfo.toString();

                //写入换行

                fos.write(s.getBytes());
                String newline = System.getProperty("line.separator");
                fos.write(newline.getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //真实场景可定时插入数据库操作，此处仅模拟
            log.info("日志已写入本地");
        }
        //处理完请求，返回内容
        log.info("RESPONSE :" + ret);
    }

    //通过环绕增强获取目标方法执行时间，可用于分析性能
    //可通过连接点参合反射机制，在这里调用目标方法，并返回调用结果
    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //得到开始时间
        long startTime = System.currentTimeMillis();
        //执行连接点的目标方法
        Object ob = proceedingJoinPoint.proceed();
        Map<String, Object> threadInfo = threadLocal.get();
        //计算处方法的真是执行时间，可以在目标方法中加入线程休眠体会结果
        Long takeTime = System.currentTimeMillis() - startTime;
        //存入线程变量
        threadInfo.put("takeTime", takeTime);
        log.info("耗时：" + takeTime);
        threadLocal.set(threadInfo);
        return ob;
    }

    //异常处理
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        //异常信息
        log.error("{}接口调用异常，异常信息{}", request.getRequestURI(), throwable);
    }
}