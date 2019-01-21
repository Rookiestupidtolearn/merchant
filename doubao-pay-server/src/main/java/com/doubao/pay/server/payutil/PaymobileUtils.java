package com.doubao.pay.server.payutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.doubao.pay.server.config.MerchantInfoConfig;
import com.doubao.pay.server.payutil.rea.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 工具类
 *
 * @author: yingjie.wang
 * @since : 2015-10-08 16:38
 */

@Component
public class PaymobileUtils {

    @Autowired
    private MerchantInfoConfig merchantInfoConfig;

    //编码格式UTF-8
    public final String CHARSET = "UTF-8";


    //生成AESKey: 16位的随机串
    public String buildAESKey() {
        return RandomUtil.getRandom(16);
    }

    //使用易宝公钥将AESKey加密生成encryptkey
    public String buildEncryptkey(String AESKey, String publicKey) {
        String encryptkey = "";
        try {
            encryptkey = RSA.encrypt(AESKey, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return encryptkey;
    }

    //使用易宝公钥将AESKey加密生成encryptkey
    public String buildEncyptkey(String AESKey) {
        return buildEncryptkey(AESKey, merchantInfoConfig.getYeepayPublicKey());
    }

    //生成RSA签名：sign
    public String buildSign(TreeMap<String, Object> treeMap, String privateKey) {

        String sign = "";
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            if (entry.getValue() != null) {
                buffer.append(entry.getValue());
            }
        }
        String signTemp = buffer.toString();

        if (StringUtils.isNotEmpty(privateKey)) {
            sign = RSA.sign(signTemp, privateKey);
        }
        return sign;
    }

    //使用商户私钥生成RSA签名：sign
    public String buildSign(TreeMap<String, Object> treeMap) {
        return buildSign(treeMap, merchantInfoConfig.getMerchantPrivateKey());
    }

    //生成密文：data
    public String buildData(TreeMap<String, Object> treeMap, String AESKey) {

        String data = "";

        //将商户编号放入treeMap
        treeMap.put("merchantaccount", merchantInfoConfig.getMerchantaccount());

        //生成sign，并将其放入treeMap
        String sign = buildSign(treeMap);
        treeMap.put("sign", sign);

        String jsonStr = JSON.toJSONString(treeMap);
        data = AES.encryptToBase64(jsonStr, AESKey);

        return data;
    }

    //一键支付post请求
    public TreeMap<String, String> httpPost(String url, String merchantaccount, String data, String encryptkey) {

        TreeMap<String, String> result = null;

        //请求参数为如下三者：merchantaccount、data、enrcyptkey
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("data", data);
        paramMap.put("encryptkey", encryptkey);
        paramMap.put("merchantaccount", merchantaccount);

        String responseBody = HttpClient4Utils.sendHttpRequest(url, paramMap, CHARSET, true);
        result = JSON.parseObject(responseBody, new TypeReference<TreeMap<String, String>>() {
        });

        return result;
    }

    //get请求
    public TreeMap<String, String> httpGet(String url, String merchantaccount, String data, String encryptkey) {

        TreeMap<String, String> result = null;

        //请求参数为如下三者：merchantaccount、data、enrcyptkey
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("data", data);
        paramMap.put("encryptkey", encryptkey);
        paramMap.put("merchantaccount", merchantaccount);

        String responseBody = HttpClient4Utils.sendHttpRequest(url, paramMap, CHARSET, false);
        result = JSON.parseObject(responseBody, new TypeReference<TreeMap<String, String>>() {
        });

        return result;
    }

    //解密data，获得明文参数
    public TreeMap<String, String> decrypt(String data, String encryptkey, String privateKey) {

        TreeMap<String, String> result = null;

        //1.使用商户密钥解密encryptKey。
        String AESKey = "";
        try {
            AESKey = RSA.decrypt(encryptkey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //2.使用AESKey解开data，取得明文参数；解密后格式为json
        String jsonStr = AES.decryptFromBase64(data, AESKey);

        //3.将JSON格式数据转换成TreeMap格式
        result = JSON.parseObject(jsonStr, new TypeReference<TreeMap<String, String>>() {
        });

        return result;
    }

    //解密data，获得明文参数
    public TreeMap<String, String> decrypt(String data, String encryptkey) {
        return decrypt(data, encryptkey, merchantInfoConfig.getMerchantPrivateKey());
    }

    //sign验签
    public boolean checkSign(String params, String signYeepay, String publicKey) {
        return RSA.checkSign(params, signYeepay, publicKey);
    }

    // sign验签
    public boolean checkSign(TreeMap<String, String> dataMap) {

        //获取明文参数中的sign。
        String signYeepay = StringUtils.trimToEmpty(dataMap.get("sign"));

        //将明文参数中sign之外的其他参数，拼接成字符串
        StringBuffer buffer = new StringBuffer();
        for (Entry<String, String> entry : dataMap.entrySet()) {
            String key = formatStr(entry.getKey());
            String value = formatStr(entry.getValue());
            if ("sign".equals(key)) {
                continue;
            }
            buffer.append(value);
        }

        //result为true时表明验签通过
        boolean result = checkSign(buffer.toString(), signYeepay, merchantInfoConfig.getYeepayPublicKey());

        return result;
    }

    public InputStream clearDataHttpGet(String url, String merchantaccount, String data, String encryptkey) {
        return ClearDataUtils.httpGet(url, merchantaccount, data, encryptkey);
    }

    //字符串格式化
    public String formatStr(String text) {
        return (text == null) ? "" : text.trim();
    }


}
