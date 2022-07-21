package org.cuko.cloud.service.gatekeeper;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.cuko.cloud.service.gatekeeper.config.RouterConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@RestController
public class CukoCloudServiceGatekeeper implements ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(CukoCloudServiceGatekeeper.class,args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动完成");
    }

    @GetMapping("/status")
    public Map<String,Object> status(){
        Map<String,Object> res = new HashMap<>();
        res.put("time",System.currentTimeMillis());
        res.put("status","OK!");
        return res;
    }

    @RequestMapping("**")
    public Object requests(@RequestBody(required = false) Map<String,Object> data, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String path = request.getServletPath();
        String url = path.replaceFirst(RouterConfig.get(path), RouterConfig.match(path));

        if(request.getParameterMap().size() > 0){
            StringBuffer params = new StringBuffer(url + "?");
            Map<String, String[]> r = request.getParameterMap();
            for (String s : r.keySet()) {
                params.append(s+"="+request.getParameter(s));
            }
            url = params.toString();
        }
        System.out.println("router >>>["+path+"] >>>>> ["+url+"]");
        HttpRequest req = HttpUtil.createRequest(Method.valueOf(request.getMethod()), url);
        req.header("token",request.getHeader("token"));
        req.header("Content-Type",request.getHeader("Content-Type"));
        req.header("Accept",request.getHeader("Accept"));
        req.header("Host",request.getHeader("Host"));
        req.header("User-Agent",request.getHeader("User-Agent"));
        if(null != data) {
            req.body(JSONUtil.toJsonStr(data));
        }

        if(url.indexOf("download")>-1){
            response.getOutputStream().write(req.execute().bodyBytes());
            return "file download success~!";
        }else{
            return req.execute().body();
        }

    }




}
