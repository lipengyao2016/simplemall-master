package com.simplemall.micro.serv.zuul.filter;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class RateLimitZuulFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(RateLimitZuulFilter.class);
    private final RateLimiter rateLimiter = RateLimiter.create(3);
    private Jedis jedis ;
    private String luaScript;

    public RateLimitZuulFilter()
    {
        File directory = new File("");
        String limitPath = directory.getAbsolutePath() + "/limit.lua";

        try {
            File file = ResourceUtils.getFile("classpath:limit.lua");
            luaScript = Files.toString( file, Charset.defaultCharset());
            log.info(luaScript);
            jedis = new Jedis("192.168.7.188", 6379);
            log.info("create jedis ok..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  boolean acquire() throws Exception {
        String key = "ip:" + System.currentTimeMillis()/ 1000; //此处将当前时间戳取秒数
        String limit = "3"; //限流大小
        log.info(" key:" + key);
        return (Long)jedis.eval(luaScript, Lists.newArrayList(key), Lists.newArrayList(limit)) == 1;
    }

    /*
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     *
     * @see com.netflix.zuul.IZuulFilter#run()
     */
    @Override
    public Object run() {
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();
           // if (!rateLimiter.tryAcquire())
            if(!this.acquire())
            {
                log.error("has too many requests ,will return!!!");
                HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                response.setStatus(httpStatus.value());
                response.getWriter().append(httpStatus.getReasonPhrase());
                currentContext.setSendZuulResponse(false);
                throw new ZuulException(
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        httpStatus.getReasonPhrase()
                );
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }

    /*
     * 这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
     *
     * @see com.netflix.zuul.IZuulFilter#shouldFilter()
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /*
     * (non-Javadoc) pre：路由之前 routing：路由之时 post： 路由之后 error：发送错误调用
     *
     * @see com.netflix.zuul.ZuulFilter#filterType()
     */
    @Override
    public String filterType() {
        return "pre";
    }
}
