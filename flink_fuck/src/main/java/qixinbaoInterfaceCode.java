/**
 * @AUTHOR: Maynard
 * @DATE: 2022/11/25 09:55
 **/

import com.mashape.unirest.http.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.util.Calendar;
import java.net.URLEncoder;

public class qixinbaoInterfaceCode {
    // 获取字符串的md5值
    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String s = String.format("%02x", b);
                sb.append(s);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://api.qixin.com/APIService/enterprise/getPartners";
        String appkey = "a107fefb-9cbe-41cf-bd1b-24c6466f01d5";
        String secretKey = "5af2639d-072b-4a6f-8c35-b74a9c9299de";
        Calendar calendar = Calendar.getInstance();
        Long timestamp = calendar.getTime().getTime();
        String sign = getMD5Str(appkey + timestamp + secretKey);

        // 设置url参数
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put("keyword", "新疆绿翔农资有限公司");
//        urlParams.put("skip", "skip的值");

        List<String> concatParams = new ArrayList<String>();
        for (String s : urlParams.keySet()) {
            concatParams.add(s + "=" + URLEncoder.encode(urlParams.get(s), String.valueOf(StandardCharsets.UTF_8)));
        }

        Unirest.setTimeouts(0, 2000);
        try {
            // 发送get请求，得到响应
            HttpResponse<String> response = Unirest.get(baseUrl + "?" + String.join("&", concatParams))
                    .header("Auth-version", "2.0")
                    .header("appkey", appkey)
                    .header("timestamp", timestamp + "")
                    .header("sign", sign)
                    .header("Content-Type", "application/json")
                    .asString();

            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

