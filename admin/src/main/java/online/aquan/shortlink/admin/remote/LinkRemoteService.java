package online.aquan.shortlink.admin.remote;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import online.aquan.shortlink.admin.common.convention.result.Result;
import online.aquan.shortlink.admin.dto.req.LinkStatsReqDto;
import online.aquan.shortlink.admin.remote.dto.req.*;
import online.aquan.shortlink.admin.remote.dto.resp.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public interface LinkRemoteService {
    
    /**
     * 调用中台的接口,得到json字符串,然后解析即可
     * 第二个参数是一个 TypeReference<> 匿名类的实例。
     * TypeReference 是 Jackson 库中的一个类，用于解决 Java 泛型擦除的问题。
     * 在这里，通过使用匿名类，创建了一个泛型的 TypeReference 实例。
     * 这个匿名类的目的是为了获取包含泛型信息的类型。
     * 由于 Java 的泛型信息在运行时会被擦除，而 JSON 解析库需要知道目标对象的具体类型，
     * 因此使用 TypeReference 可以保留泛型信息，确保正确地将 JSON 转换为具体的 Java 对象。
     */
    

    


  

    

    

  
}
