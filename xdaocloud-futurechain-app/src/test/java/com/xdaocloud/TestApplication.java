package com.xdaocloud;

import com.alibaba.fastjson.JSONObject;
import com.xdaocloud.futurechain.httpapi.IMTeamService;
import com.xdaocloud.futurechain.util.YunXinUtils;
import io.eblock.eos4j.api.utils.Generator;
import io.eblock.eos4j.utils.JsonFormatTool;
import retrofit2.Call;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 测试
 *
 * @author LuoFuMin
 * @data 2018/9/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {
    @Autowired
    IMTeamService imTeamService;

    @Test
    public void queryDetail() throws IOException {
        println(imTeamService.queryDetail(YunXinUtils.getheaders(), "1388420751").execute().body());
    }
    
    private static void println(Object object) {

        if (object instanceof Call) {
            object = Generator.executeSync((Call)object);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + object.getClass());
        System.out.println(JsonFormatTool.formatJson(JSONObject.toJSONString(object)));
    }
}
