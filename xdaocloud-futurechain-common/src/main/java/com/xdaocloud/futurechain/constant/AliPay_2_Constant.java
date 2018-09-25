package com.xdaocloud.futurechain.constant;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 麦链科技
 */
public class AliPay_2_Constant {

    // 支付宝支付appid
    public static String APP_ID = "2018060560266678";

    // 应用私钥
    public static String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCv3pTYq913/qWuiz6aHd7bpDEPp+YfdOkDPEhrbj84aDqED15NGZj+yo7H53m" +
            "Atpe8qaPg+KnrUl9zF/nJ+hyBKcB0diztYAOgdpt3lp7AmVQ9omNsvLq1i+8qdb/pUqW8VCIXKkaRn2EyXAHXb6/vYiFR0EUdHyLPRvPJHraDD+FeQwfUCnzMBMT40uqbpTjipTkB4h5v8N" +
            "cK4ExtAoQmJHf8IuOh5u6A172xnKk9Irm33hKFl80QB9slaQXxfHCrppA8lw3AuO5zym3sf9d7CFn9ely6RxqY1k4MI2PreHuqCv81+SeDi285iFjz8BfILA8bchFee/Rs+9Cg+pllAgMBA" +
            "AECggEBAKZ4x2ZNUMbr2ULd9XUGv1QjGHmcUV0HWiVA48AlTFyFdJZawcWzbDoJ3JtnBYEL1Ze08DXcy2K/xy/3yu8f++lbbxwOXLqY/vXeL41EmqLuwGnTE8/y2b1NZydG7FDcdZtN/DL" +
            "3RnNDbUqxAuE9jEjJF4cM4M8PEPEpVUvT8akeQNW/JnkhukjTfZSaMMhmsVVcx1uygeXYsvVpbQ8NDP2gDaxyqcvBAzoGQKqXHtT7QLJL6pcwkWtj/WJInCVmC+XXNv2Lxi/OCf47c09tt" +
            "o5sjXCT0TdxXQViDR9+FCi3uZ8N7dSbilFOqM+DEKfmebjmI+S0sfAymyx4fDS7oyECgYEA+nfWEjHeo/V9QB7ZPgpZzQItiV8HnL9uEYpuyM2NysteU5jYEufCUKy58SB+RNce+wg6ipwK" +
            "jhiQ2RUjb0pDs9f857MVQeHf2+ea4Cas50wtESKrD4w41SU9QM+/3UMhajNTfDTK9t/yZLhtV5VaHe2760Nw2FOtDvqpq4lv+F0CgYEAs8D1l/5JZYkgMFLmqBqpjoQh0I6YmtYOpqiGRP+" +
            "JmuBH6MFDnguc+dunZrENUXomAY6aHT6vWcQA0fpkXzX/GlSBlJw7GDyjgjkqw7voTE+kjX1QaynYGBI9ijxQTAjTt87S5JQKPQx2KFPJW5u0tYewogsIfL7O/3cRaBFD9KkCgYA5dOgcwG" +
            "fsgNI8tVbsjn9PMiK0EqKyaI+dYn5Lfiv6y3BWCQf4PkYrL5RwJh4ROLAYsiiLx4P8WNJRmjHIw+aICRIxLLnBpUkvLxAuP3EGlkj4V1WaeXZIHwTQK1g+L7oonKGYKpwsye7XAB/FKbPE" +
            "jda//3aRiZB3kTT+7sZK2QKBgQCfz5wJ5+AlT4a4r9u19Q5uUHcnzr9KWalo7keBrJcQhPopqzmMl9KWKX1pRVwL5kdH9xp5ibOoYSRBixYVuBH3vM3/6+52G+Na8axdnQBoaB5qVNFclT" +
            "U0IXNpJTpWfzqMHmtNIXkA0cVzERriQUqmYd7eEmUXUyUB50Aof8ElEQKBgQCB/2NxZzOVGDnzcasZ9jD6aDKErK2xb8P5J760v2HMMDU+18CV/o5ebRla4ogQXprfi5J9vjc0wvIPja0g" +
            "Dvh3V/6Qmau7nvSSR22OKI8EzhAQclf9/NkF2aYFBIRuW1ksFC+1PV1V/7xXdp3tGp3rR4R9eI+ro/NNuzFRv5rliw==";

    //签名方式
    public static String SIGN_TYPE = "RSA2";

    //支付宝网关（固定）
    public static String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArroM5oHTPJX4sgr5oN/ZDxUZPVjs5Pd6VpKkR+BEA2Xww7NeDytDKKmRmm4WkZr035" +
            "ijIVdyWHLDZEwrPyOELelGWFl3KngZA0OUKshM9epyaVZKblF24laT/WKPM4w1bi2ylYUlC5C/AlkiFmEqM1mkmB3+Iye8zYyYV0r4R3ZBG/+mK2JTo9YwQfn7eUAW2711D3hXejlv+zYh" +
            "2ymSHO2L8neac24FoHPKWdC2wzbww33NVbdxF7xiYfOo1ke80gdC/fUv9AlyfU3QotHw/58cfsCld2yyOGuYSsJZzG5KzVJNS5Bo3ZSiFE+X6yE/VRrUzkUXhOpvU9G/YEVzjQIDAQAB";


    private static final AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, SIGN_TYPE);

    public static AlipayClient getInstance() {
        return alipayClient;
    }
}
