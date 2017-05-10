package cn.linhos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by fprok on 2017/5/10.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    public void addViewController(ViewControllerRegistry registry){
        registry.addViewController("/ws").setViewName("/ws");
    }
}
