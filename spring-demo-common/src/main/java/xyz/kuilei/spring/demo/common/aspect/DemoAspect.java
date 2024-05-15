package xyz.kuilei.spring.demo.common.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.kuilei.spring.demo.common.bean.ResultBean;
import xyz.kuilei.spring.demo.common.util.SpringContextUtil;

import javax.servlet.http.HttpServletRequest;

import static cn.hutool.core.text.StrPool.DOT;

@Component
@Aspect
@Slf4j
public class DemoAspect {
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundRequestMapping(ProceedingJoinPoint point) throws Throwable {
        String function = point.getTarget().getClass().getName() + DOT + point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), "；");

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        String host = request.getRemoteAddr();
        String method = request.getMethod();
        String functionPath = request.getRequestURI();

        String logPrefix = String.format("host：%s，method：%s，path：%s；调用方法 [%s]", host, method, functionPath, function);

        try {
            log.info(String.format("%s 开始；入参：%s", logPrefix, args));

            long time = System.currentTimeMillis();
            Object result = point.proceed();
            time = System.currentTimeMillis() - time;

            // 添加 spend time
            if (result instanceof ResultBean) {
                ((ResultBean) result).setSpendTime(time);
            }

            boolean ifPrintRes = Boolean.valueOf(
                    SpringContextUtil
                            .getApplicationContext()
                            .getEnvironment()
                            .getProperty("control.ifPrintRes", "false")
            );

            if (ifPrintRes) {
                log.info(String.format("%s 结束；耗时：%s，结果：%s", logPrefix, time, JSON.toJSONString(result)));
            } else {
                log.info(String.format("%s 结束；耗时：%s", logPrefix, time));
            }

            return result;
        } catch (Throwable t) {
            log.info(String.format("%s 异常", logPrefix), t);
            throw t;
        }
    }
}
