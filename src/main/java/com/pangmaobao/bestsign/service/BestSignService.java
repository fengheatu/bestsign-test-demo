package com.pangmaobao.bestsign.service;

import java.util.Map;

/**
 * @author: he.feng
 * @date: 13:37 2018/1/9
 * @desc:
 **/
public interface BestSignService {


    /**
     * 注册个人用户并申请证书
     * @param paramsMap
     * @return
     */
    String bestSignUserReg(Map<String,Object> paramsMap);

    /**
     * 查询证书编号
     * @param paramsMap
     * @return
     */
    String bestSignUserGetCert(Map<String,Object> paramsMap);


    /**
     * 异步申请状态查询
     * @param paramsMap
     * @return
     */
    String bestSignAsyncApplyCertStatus(Map<String,Object> paramsMap);


    /**
     * 生成用户签名/印章图片
     * @param paramsMap
     * @return
     */
    String bestSignSignatureImageUserCreate(Map<String,Object> paramsMap);

    /**
     * 上传合同文件
     * @param paramsMap
     * @return
     */
    String bestSignFileUpload(Map<String,Object> paramsMap);


    /**
     * 下载合同文件
     * @param paramsMap
     * @return
     */
    String bestSignFileDownload(Map<String,Object> paramsMap);


    /**
     * 上传并创建合同
     * @param paramsMap
     * @return
     */
    String bestSignContractUpload(Map<String,Object> paramsMap);


    /**
     * 签署合同
     * @param paramsMap
     * @return
     */
    String bestSignContractSignCert(Map<String,Object> paramsMap);


    /**
     * 撤销合同
     * @param paramsMap
     * @return
     */
    String bestSignContractCancel(Map<String,Object> paramsMap);


    /**
     * 锁定并结束合同
     * @param paramsMap
     * @return
     */
    String bestSignContractLock(Map<String,Object> paramsMap);




}
