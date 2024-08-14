package com.bcrjl.rss.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bcrjl.rss.common.constant.AppConstant.CONFIG_PATH;

/**
 * @author yanqs
 * @since 2024-08-07
 */
@AllArgsConstructor
@RestController
@RequestMapping("/system")
public class SystemController {

    @GetMapping("/config")
    public Object getConfig(){
        String str = ResourceUtil.readUtf8Str(CONFIG_PATH);
        return str;
    }
}
