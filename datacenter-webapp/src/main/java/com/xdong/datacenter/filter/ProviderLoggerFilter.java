package com.xdong.datacenter.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.dubbo.common.Constants;

@Activate(Constants.PROVIDER)
public class ProviderLoggerFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(ProviderLoggerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Result result = null;
        String clientIp = "";
        try {
            clientIp = RpcContext.getContext().getRemoteHost();
        } catch (Exception e) {
            LOGGER.info("ProviderLoggerFilter获取远程ip异常");
        }

        String params = jsonFilter(JSON.toJSONString(invocation.getArguments()));
        String wholeNm = String.format("%s@%s", invoker.getInterface().getSimpleName(), invocation.getMethodName());
        try {
            result = invoker.invoke(invocation);
            String rlt = jsonFilter(JSON.toJSONString(result));

            LOGGER.info(wholeNm + "[" + clientIp + "] -> param:" + params + ", result:" + rlt);
        } catch (Exception e) {
            if (result == null) {
                throw e;
            }
        }

        return result;
    }

    private String jsonFilter(String params) {
        return params.replaceAll("\"id[c|C]ard[^,|}]*",
                                 "idcard:***").replaceAll("\"bank[c|C]ard[^,|}]*",
                                                          "bankcard:***").replaceAll("\"password[^,|}]*",
                                                                                     "password:***").replaceAll("\"image[^,|}]*",
                                                                                                                "image:***").replaceAll("\"delta[^,|}]*",
                                                                                                                                        "delta:***");
    }
}
