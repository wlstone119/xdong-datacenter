package com.xdong.datacenter.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatusController {

    @Value("${spring.redis.host}")
    private String     redisHost;
    @Value("${spring.redis.port}")
    private Integer    redisPort;
    @Value("${spring.datasource.url}")
    private String     datasourceUrl;
    @Value("${spring.datasource.username}")
    private String     datasourceUsername;
    @Value("${spring.dubbo.registry.address}")
    private String     registryAddress;
    @Value("${druid.whiteIps:192.168.0.0/16,172.20.1.0/24,172.20.100.0/24,127.0.0.1}")
    private String     whiteIps;

    @Autowired
    private DataSource dataSource;

    @ResponseBody
    @RequestMapping(value = "/status.jsp")
    public String status() {
        return "ok";
    }

    private Map<String, Object> checkZookeeper() {
        Map<String, Object> map = new HashMap<String, Object>();
        ZkClient zkClient = null;
        map.put("registryAddress", registryAddress);
        try {
            zkClient = new ZkClient(registryAddress);
            map.put("status", "ok");
        } catch (Exception e) {
            map.put("status", parseError(e.getMessage()));
        } finally {
            zkClient.close();
        }
        return map;
    }

    private Map<String, Object> checkDb() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("datasourceUrl", datasourceUrl);
        map.put("datasourceUsername", datasourceUsername);
        try {
            dataSource.getConnection();
            map.put("status", "ok");
        } catch (Exception e) {
            map.put("status", parseError(e.getMessage()));
        }
        return map;
    }

    private String parseError(String content) {
        return "<span style=\"color:red;\">" + content + "</span>";
    }

    private static String getClientIp(HttpServletRequest httpservletrequest) {
        if (httpservletrequest == null) return null;
        String s = httpservletrequest.getHeader("X-Forwarded-For");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("WL-Proxy-Client-IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("HTTP_CLIENT_IP");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
            s = httpservletrequest.getHeader("HTTP_X_FORWARDED_FOR");
        if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) s = httpservletrequest.getRemoteAddr();
        if ("127.0.0.1".equals(s) || "0:0:0:0:0:0:0:1".equals(s)) {
            try {
                s = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException unknownhostexception) {
                return null;
            }
        }
        return s;
    }

    private boolean isInRange(String ip) {
        String[] whiteIpArr = whiteIps.split(",");
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16)
                     | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        for (String whiteIp : whiteIpArr) {
            if ("127.0.0.1".equals(whiteIp)) {
                continue;
            }
            int type = Integer.parseInt(whiteIp.replaceAll(".*/", ""));
            int mask = 0xFFFFFFFF << (32 - type);
            String cidrIp = whiteIp.replaceAll("/.*", "");
            String[] cidrIps = cidrIp.split("\\.");
            int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) | (Integer.parseInt(cidrIps[1]) << 16)
                             | (Integer.parseInt(cidrIps[2]) << 8) | Integer.parseInt(cidrIps[3]);
            if ((ipAddr & mask) == (cidrIpAddr & mask)) {
                return true;
            }
        }
        return false;
    }
}
