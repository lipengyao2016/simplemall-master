package com.simplemall.micro.serv.prd.test.aliPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

public class AliH5PayTest {

    private static  String APP_ID = "2019032163655002";
    private static  String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDSoz8uf8iCZwBH\n" +
            "OllM4xXpYIx1GWpnSh8ZLkphBW7ISBmpyz2S8KfSd2ImDjsWhZeIowKXL124vRMU\n" +
            "tkunuSkFRnXK8h8KaynFawZcHRs8raIcxMsPogQURqChqb5Dy4U8jVl3+x9tbY75\n" +
            "9xsOoJmL6Qg2AEUFs65MCDTp5XQmc6mosi3d0lYMmd6AA5g000JUPntBZ7Nnw8/V\n" +
            "FU/loDeDXeI6G0rJWncmPczeTvUsXtwbziQyinlzUn5/cQc6SQ1qEb6wLwwZsbE2\n" +
            "vw4ap4F9+qAq1IUrqVhj1YjgYB2CytEt8x6gbThx0LMjVNo/TK+HC7TwmcwgFqSL\n" +
            "F9lsOnkrAgMBAAECggEAQXJ9sCn+GB5+5KLJmm04orHgEoZaHEvYi89pRuoUOODG\n" +
            "MI64zA5AZtG3SlHDpETQB6F2rZkHCt6criPSMAQFLeFtuigu4uLXMMMHT9mvW9Qr\n" +
            "ebrfyTSzBcXIJVMDjjcPZ/gXY4NAiZSwBxTzcEEQVHuMlJLS32mr723hWwdzZnqb\n" +
            "WqUN0pqOtcU8r4sV51DzapyqRSu9ygJLmjj2Yphrd0KEKmY8rUMdM6WTQLCAGpzh\n" +
            "bOurY7AFgKxIkui8PFHp1l9HnpZ5zm61z/s1sV7kPmkV0HaUa7kyaU/9y5m9XBNJ\n" +
            "ZUF+bjsz5W4PoVyMOi29WdRRTtIu0JXRv3QbiYPPyQKBgQDsDpkMfMv4x/NlwwnB\n" +
            "wZ+oFEDhGI1p2iWB3ZWwNJqIbl1dtkIj09Ly8hIjFfaX1ge6IpMpAYP31PKiTAz6\n" +
            "fswDyWVWYHBEtntbyDbVUxxdGaYGeizRRIjIc7i5zdGzxRFOYWbfPcFAhIGursKI\n" +
            "sOwv+UH8yk47mtEQ3Ez6xNYB1QKBgQDkbuI5LogaATpvrTnvkD6LGz3/HQW/pLlt\n" +
            "RtUKq7G3SGvGauaT81T4zEEzM6yF4UWRw8Lg8xqhpJZua9JkCwDZ5sKIsrwLuqfE\n" +
            "JKJ50ZswSyuM73JGImuzzId6u7dMD7W+/G9v2m9/FCTAC/jFgm8jRQBUYLxDTrCF\n" +
            "bEOigKEO/wKBgBkBGtklxv1dQL0AweHSPrUxIAY38ZLK1rpA3IBDsjLNbfplOfBN\n" +
            "mcS+O4mX4/0FYOAolWn8jRwNZH4ojB9kmvuzs5ZItNuycLKyLIzANvrtopaBDopE\n" +
            "NjD1gRW9ZX8jWsfwrVLsWbIZ1xrHzDdqdjpGruS2ET9jEM42Z7kQbJLZAoGBAOMS\n" +
            "4qiK+9o13FwFK7FbhwCK3Q8LT5qY6YaaQc6wrCGQySndPEZYPeBwUmPOezI+pARp\n" +
            "QACTSKbkUHGFlwfB9F+nRzvRyKI0Mu8Ep8pGtWjFYI5hvAvBzaRWrVow3d2rRDSN\n" +
            "BU2AKxN896c8f1f+9/Fsdw5pdtvFji2jWHxiNzgLAoGBAMLchnv9uboHwyMzo7BK\n" +
            "X+a1s/Vks4q895xRrDMb6qmyEGL8UQhlfEkMN/QXLG5ejyHgr+RS4Vij32W5d8zK\n" +
            "S/RbIrGwfhxpPExbesW8XJkvWUSl9nSLRM34JoLvHTqEMhcYubhlEqTKxEBv7XuO\n" +
            "tZ1pi3VLwqXunUCz/7pijEv0";
    private static  String payCharset = "utf-8";
    private static  String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAomoiElAbVAfL5s7jatl8ev5vKuIRreXyPQYqYhROOmjmZAzw7pMqXRG6gnRyb0y4Jccmcv+jy4GGbIdaKAsakZIgbBI0PddH5jQe/yyar5+noqV5S62IB1BbqhUYb8QTjpoE9At328A8ePXuHXGRveKjcrfRxU0FH3RjJjeXJdX1qxeE/rJzzRQZAIipbY55XMwD1/MFUx+yxCe8pdORezUSJrzdiGxWgqDNNMVHYU3TDm0BtM5sppyBWgLSLM8rN9yauRvIBo8ZK6waka1He1mrzU748XgWDa4Tsh6w3zQoyZd+WkZNzlf9I1/acb099re/ormYfzRlFs7kax5NxQIDAQAB";

    public    void testH5Pay()
    {
//实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, "json", payCharset, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20190320010101002\"," +
                "    \"total_amount\":0.01," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"product_code\":\"QUICK_WAP_WAY\"" +
                "  }");//填充业务参数
        try {
            String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            System.out.println("h5 pay ret form:" + form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    public   void testTradeQuery()
    {
//实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, "json", payCharset, ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                " \"out_trade_no\":\"20190320010101002\"}");//设置业务参数
        AlipayTradeQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.print(response.getBody());
//根据response中的结果继续业务逻辑处理
    }



    public static void main(String[] args) throws Exception {
        AliH5PayTest test1 = new AliH5PayTest();
        //test1.testH5Pay();
        test1.testTradeQuery();
    }
}
