package com.pangmaobao.bestsign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pangmaobao.bestsign.service.BestSignService;
import com.pangmaobao.bestsign.util.*;
import com.pangmaobao.controller.TestController;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;


/**
 * @author: he.feng
 * @date: 13:38 2018/1/9
 * @desc:
 **/
public class BestSignServiceImpl implements BestSignService {

    private static final Logger logger = LogManager.getLogger(BestSignServiceImpl.class);

    /**
     * 上上签 测试服务地址
     */
    private static final String BEST_SIGN_SERVICE_URL = "https://openapi.bestsign.info";

    /**
     * 必传的四个个参数
     */
    private static final String MUST_PARAMS = "?developerId={0}&rtick={1}&signType=rsa&sign={3}";

    /**
     * 注册个人用户并申请证书
     */
    private static final String USER_REG_URL = "/openapi/v2/user/reg";



    /**
     * 查询证书编号
     */
    private static final String USER_GET_CERT_URL = "/openapi/v2/user/getCert";


    /**
     * 异步申请状态查询
     */
    private static final String USER_ASYNC_APPLY_CERT_STATUS_URL = "/openapi/v2/storage/user/async/applyCert/status";



    /**
     * 生成用户签名/印章图片
     */
    private static final String STORAGE_SIGNATURE_IMAGE_USER_CREATE_URL = "/openapi/v2/storage/signatureImage/user/create";


    /**
     * 上传合同文件
     */
    private static final String STORAGE_UPLOAD_URL = "/openapi/v2/storage/upload";


    /**
     * 下载文件
     */
    private static final String STORAGE_DOWNLOAD_URL = "/openapi/v2/storage/download";


    /**
     * 上传并创建合同
     */
    private static final String STORAGE_CONTRACT_UPLOAD_URL = "/openapi/v2/storage/contract/upload";


    /**
     * 签署合同
     */
    private static final String STORAGE_CONTRACT_SIGN_CERT_URL = "/openapi/v2/storage/contract/sign/cert";

    /**
     * 撤销合同
     */
    private static final String CONTRACT_CANCEL_URL = "/openapi/v2/contract/cancel";

    /**
     * 锁定并结束合同
     */
    private static final String STORAGE_CONTRACT_LOCK_URL = "/openapi/v2/storage/contract/lock";


    /**
     * 私钥
     */
    private static final String BEST_SIGN_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqHz5pF0q4cOVLXK9w554LFmcRZf8ZHDyHTLtFEmNI0C7V/84AzFV97mh8Xq3KQ+z9Cku6F570+egS33hAbi9JKwObJsRCZbf3rH73ydN+oLVxWlTy0G81l+kyRhw84WMSz2iYWH+7o2bql14ZWz94eg5xv183JuYiPRWbklP8aW3pj4mKQNzhyi37IEDIq/7SLOG5fg5eFhYKJRKXALqOnvY7IjwhTTs7xjoThXF3YrZ04mkqGrncr0Y9f3j1so7KloB2rmDF14dPA+CCFAj5zwiSHDeCkFDJcdD/eNncUodQtozchcFaBmaNBSF6Fo9L7E/NlyssaRtsUrWgBN/VAgMBAAECggEAF016TgjbulH0hpMmkO+ZDWm/KnnvLHBatinDiR2QrfuePr/wZtmWaE6Mz2Rkp80QHEUZxF7R7jqBGHy1yxWMspgKwWjY/0NRpMJjDev+ZVz8HY/DDROR+CRb1AAYhBpXaM8yQ5Pzwy8JvN6JDC4cjpMhgWE2WKaiZVQ6rnmnuW45hcbX/iHw7jBknHxJkH3ps/3GphZFdfsUlE6ls/zIABlAFKNo+CIt8xJJ6flMpolzZakA6vV6fvHodjFI9G1ZOvowR2BOeF7Sst9FKgdVqAlKEDF9KoYiFXPTs0JkEwZLcEOh26P3R0KETANy07tqgPUxx05S89tjBX52vls/jQKBgQD5Ozadhmo9/j6KZ3zam36dxUV151diibwqE/bmpi3CNZofLEJRgWqEVIoDZ7fXSXXSKAv34eVvcIjPGUu01k9G0oPKNgmudHCCwiDUtb1FePEPHC6i22dnPaM+YNjbo6mnPdDg0e+hq8OdDIQvpXH+qhQ9cFIvU2V4FiuRpb47cwKBgQCuvgV8oIOww/eyYXNf4WSj3oVq2u/Jy0RupT3iZ6ulIzpj9yJfgGXy5YRyxzPDE/F02e4RH2rGt6cRIdBhCHrRU+MNW1juL191bjtOYKv7gDZ1+2biE77IMVLyHyZiu6hgleAM9sWbfxldNOIM+VZzBUOW7VYHnsWsYULEXTs1lwKBgQCumGu3SM2upih73umZX2sA7YyufyU5c5HszFWf2PSfY9uXUPMYlBfhojOZFRS17dFrKwDPY2HOgsBkjKz7f1LEI3+NrfN0Uj/rakGDodl5DLOayTxmfFtg+M4eScxBedLExUpJ5OgBkwmTQIxtsHI+XDmXnNMGMl8YFDfrbXfpvwKBgBg1y5zQwG4lOJRGXC3UlJT/p4x+eOqEdx69Vi2gH2/pyZVAEEsbBwT4N7mPT+SfRrzh1NIagDi4CTWecbh/7EREUxjupwuZFKi4dQ8O5cUapnECO+bmxXAHJW7WUgMr7NA0863YItjAa0s2oHsbsJaPDCZFC75SiSCw+Qcrw6BRAoGAfJX08OpBFq7ppk4rU1s2DKQb6oJ3CK3xbFh/qZ3YdG/R9YOAaoPonPo4YVfae2D0WCmg2M+ZPFA5hr6Lp2h6iTH7Ze3p84pTyQEZe+spYlg1qTUvFXlFAgH6Y4r6K1S/IP1sdiswlGR5zpkhbfUDtCQp/DtEhO18+giKSusvLis=";

    /**
     * 开发者编号
     */
    private static final String BEST_SIGN_DEVELOPERID = "1913678711387849312";

    /**
     * 加密方式
     */
    private static final String SIGN_TYPE = "rsa";

    private static final String DATA = "data";

    /**
     * 注册个人用户并申请证书
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignUserReg(Map<String, Object> paramsMap) {
        logger.info("上上签，注册个人用户并申请证书");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,USER_REG_URL);
            result = getResponseData(httpResp, "上上签 注册个人用户并申请证书");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 查询证书编号
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignUserGetCert(Map<String, Object> paramsMap) {
        logger.info("上上签，查询证书编号");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,USER_GET_CERT_URL);
            result = getResponseData(httpResp, "上上签，查询证书编号");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步申请状态查询
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignAsyncApplyCertStatus(Map<String, Object> paramsMap) {
        logger.info("上上签，异步申请状态查询");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,USER_ASYNC_APPLY_CERT_STATUS_URL);
            result = getResponseData(httpResp, "上上签，异步申请状态查询");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成用户签名/印章图片
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignSignatureImageUserCreate(Map<String, Object> paramsMap) {
        logger.info("上上签，生成用户签名/印章图片");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,STORAGE_SIGNATURE_IMAGE_USER_CREATE_URL);
            result = getResponseData(httpResp, "上上签 生成用户签名/印章图片");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传合同文件
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignFileUpload(Map<String, Object> paramsMap) {
        logger.info("上上签，上传合同文件");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,STORAGE_UPLOAD_URL);
            result = getResponseData(httpResp, "上上签 上传合同文件");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载合同文件
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignFileDownload(Map<String, Object> paramsMap) {
        logger.info("上上签，下载合同文件");
        String result = null;
        try {
            HttpResp httpResp = getRequest(paramsMap,STORAGE_DOWNLOAD_URL);
            result = getResponseData(httpResp, "上上签 下载合同文件");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传并创建合同
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignContractUpload(Map<String, Object> paramsMap) {
        logger.info("上上签，上传并创建合同");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,STORAGE_CONTRACT_UPLOAD_URL);
            result = getResponseData(httpResp, "上上签 上传并创建合同");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签署合同
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignContractSignCert(Map<String, Object> paramsMap) {
        logger.info("上上签，签署合同");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,STORAGE_CONTRACT_SIGN_CERT_URL);
            result = getResponseData(httpResp, "上上签 签署合同");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 撤销合同
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignContractCancel(Map<String, Object> paramsMap) {
        logger.info("上上签，撤销合同");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,CONTRACT_CANCEL_URL);
            result = getResponseData(httpResp, "上上签 撤销合同");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 锁定并结束合同
     *
     * @param paramsMap
     * @return
     */
    @Override
    public String bestSignContractLock(Map<String, Object> paramsMap) {
        logger.info("上上签，锁定并结束合同");
        String result = null;
        try {
            HttpResp httpResp = postRequest(paramsMap,STORAGE_CONTRACT_LOCK_URL);
            result = getResponseData(httpResp, "上上签 锁定并结束合同");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取访问连接
     *
     * @param paramsMap
     * @param visitUrl
     * @return
     */
    private String obtainPostUrl(Map<String, Object> paramsMap, String visitUrl) {
        logger.info("获取 post 请求url");
        try {
            //获取原文字符串
            String paramsJson = JSONObject.toJSONString(paramsMap);
            Map<String, Object> map = new HashMap<String, Object>();
            //1.参数字典序排序
            String sortStr = sortPara(map);
            //2.requestBody md5值
            String md5Str = Md5Util.GetMD5Code(paramsJson);
            //3.获取原文字符串
            String originalStr = sortStr + visitUrl + "/" + md5Str;
            //4.使用SHA1withRSA, base64Encode 加密
            String base64Sign = Base64Util.base64Encode(RsaUtil.signWithPriKey(originalStr, BEST_SIGN_PRIVATE_KEY));
            //5.到的字符串使用UTF-8进行urlEncode
            String sign = URLEncoder.encode(base64Sign,"UTF-8");
            String url = BEST_SIGN_SERVICE_URL + visitUrl +
                    MessageFormat.format(MUST_PARAMS, BEST_SIGN_DEVELOPERID, map.get("rtick"), SIGN_TYPE, sign);
            logger.info("访问连接【" + url + "】");
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取GET请求路径
     * @param paramsMap
     * @param visitUrl
     * @return
     */
    private String obtainGetUrl(Map<String, Object> paramsMap, String visitUrl) {
        logger.info("获取 get 请求url");
        try {
            Map<String,Object> getMap = paramsMap;
            //1.参数字典序排序
            String sortStr = sortPara(paramsMap);
            //2.获取原文字符串
            String originalStr = sortStr + visitUrl;
            //3.使用SHA1withRSA, base64Encode 加密
            String base64Sign = Base64Util.base64Encode(RsaUtil.signWithPriKey(originalStr,BEST_SIGN_PRIVATE_KEY));
            //5.到的字符串使用UTF-8进行urlEncode
            String sign = URLEncoder.encode(base64Sign,"UTF-8");
            String url = BEST_SIGN_SERVICE_URL + visitUrl +
                    MessageFormat.format(MUST_PARAMS, BEST_SIGN_DEVELOPERID, paramsMap.get("rtick"), SIGN_TYPE, sign)+
                    jointParamsMap(getMap);
            logger.info("访问连接【" + url + "】");
            return url;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    /**
     * 参数字典序排序
     *
     * @param map
     * @return
     */
    private String sortPara(Map<String, Object> map) {
        map.put("developerId", BEST_SIGN_DEVELOPERID);
        map.put("rtick", System.currentTimeMillis() + NumberUtil.randNumber(1000L, 9999L));
        map.put("signType", SIGN_TYPE);
        logger.info("参数字典序排序");
        Collection<String> keySet = map.keySet();
        List<String> list = new ArrayList<String>(keySet);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (String param : list) {
            sb.append(param).append("=").append(map.get(param));
        }
        logger.info("排序结果：【" + sb.toString() + "】");
        return sb.toString();
    }

    /**
     * get请求参数拼接
     * @param paramsMap
     * @return
     */
    private static String  jointParamsMap(Map<String,Object>paramsMap) {
        StringBuilder sb = new StringBuilder();
        for(String key : paramsMap.keySet()) {
            sb.append("&").append(key).append("=").append(paramsMap.get(key));
        }
        return sb.toString();
    }


    /**
     * GET 请求
     * @param paramsMap
     * @param visitUrl
     * @return
     */
    private HttpResp getRequest(Map<String,Object> paramsMap,String visitUrl) {
        try {
            String url = obtainGetUrl(paramsMap, visitUrl);
            String paramsJson = JSONObject.toJSONString(paramsMap);
            logger.info("访问参数【" + paramsJson + "】");
            HttpResp httpResp = HttpsClientUtil.httpPostForText(url);
            return httpResp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * post请求
     * @param paramsMap
     * @param visitUrl
     * @return
     */
    private HttpResp postRequest(Map<String,Object> paramsMap,String visitUrl) {
        try {
            String url = obtainPostUrl(paramsMap, visitUrl);
            String paramsJson = JSONObject.toJSONString(paramsMap);
            logger.info("访问参数【" + paramsJson + "】");
            HttpResp httpResp = HttpsClientUtil.httpPostByJSON(url, paramsJson);
            return httpResp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取响应信息
     * create by he.feng
     *
     * @param httpResp
     * @param descriptionInfo
     * @return
     */
    private String getResponseData(HttpResp httpResp, String descriptionInfo) {
        String result = null;
        if (httpResp.isTransSuccess()) {
            logger.info(descriptionInfo + "访问成功");
            logger.info("响应报文【" + httpResp.getResponseContext() + "】");
            result = JSONObject.parseObject(httpResp.getResponseContext()).getString(DATA);
        } else {
            String errorInfo = descriptionInfo + "接口访问失败code=" + httpResp.getResponseCode()
                    + "response:" + httpResp.getResponseContext();
            logger.error(errorInfo);
            throw new RuntimeException(errorInfo);
        }
        return result;
    }

}
