package com.pangmaobao.bestsign.test.client;

import java.io.IOException;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pangmaobao.bestsign.test.utils.HttpClientSender;
import com.pangmaobao.bestsign.test.utils.RSAUtils;

/**
 * 上上签混合云SDK客户端
 */
public class BestsignOpenApiClient {

	private String developerId;

	private String privateKey;

	private String serverHost;
	
	private static String urlSignParams = "?developerId=%s&rtick=%s&signType=rsa&sign=%s";

	public BestsignOpenApiClient(String developerId, String privateKey,
			String serverHost) {
		this.developerId = developerId;
		this.privateKey = privateKey;
		this.serverHost = serverHost;
	}

	/**
	 * POST方法示例
	 * 个人用户注册
	 * 
	 * @param account
	 *            用户账号
	 * @param name
	 *            姓名
	 * @param mail
	 *            用来接收通知邮件的电子邮箱
	 * @param mobile
	 *            用来接收通知短信的手机号码
	 * @param identity
	 *            证件号码
	 * @param identityType
	 *            枚举值：0-身份证，目前仅支持身份证
	 * @param contactMail
	 *            电子邮箱
	 * @param contactMobile
	 *            手机号码
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param address
	 *            地址
	 * @return 异步申请任务单号
	 * @throws IOException
	 */
	public String userPersonalReg(String account, String name, String mail,
			String mobile, String identity, String identityType,
			String contactMail, String contactMobile, String province,
			String city, String address) throws Exception {
		String host = this.serverHost;
		String method = "/user/reg/";

		// 组装请求参数，作为requestbody
		JSONObject requestBody = new JSONObject();
		requestBody.put("account", account);
		requestBody.put("name", name);
		requestBody.put("userType", "1");
		requestBody.put("mail", mail);
		requestBody.put("mobile", mobile);

		JSONObject credential = new JSONObject();
		credential.put("identity", identity);
		credential.put("identityType", identityType);
		credential.put("contactMail", contactMail);
		credential.put("contactMobile", contactMobile);
		credential.put("province", province);
		credential.put("city", city);
		credential.put("address", address);
		requestBody.put("credential", credential);
		requestBody.put("applyCert", "1");

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, null,
				requestBody.toJSONString());
		// 签名参数追加为url参数
		String urlParams = String.format(urlSignParams, this.developerId,
				rtick, paramsSign);
		// 发送请求
		String responseBody = HttpClientSender.sendHttpPost(host, method,
				urlParams, requestBody.toJSONString());
		// 返回结果解析
		JSONObject userObj = JSON.parseObject(responseBody);
		// 返回errno为0，表示成功，其他表示失败
		if (userObj.getIntValue("errno") == 0) {
			JSONObject data = userObj.getJSONObject("data");
			if (data != null) {
				return data.getString("taskId");
			}
			return null;
		} else {
			throw new Exception(userObj.getIntValue("errno") + ":"
					+ userObj.getString("errmsg"));
		}
	}

	/**
	 * GET方法示例
	 * 下载合同PDF文件
	 * 
	 * @param contractId
	 *            合同编号
	 * @return
	 * @throws Exception
	 */
	public byte[] contractDownload(String contractId) throws Exception {
		String host = this.serverHost;
		String method = "/storage/contract/download/";

		// 组装url参数
		String urlParams = "contractId=" + contractId;

		// 生成一个时间戳参数
		String rtick = RSAUtils.getRtick();
		// 计算参数签名
		String paramsSign = RSAUtils.calcRsaSign(this.developerId,
				this.privateKey, host, method, rtick, urlParams, null);
		// 签名参数追加为url参数
		urlParams = String.format(urlSignParams, this.developerId, rtick,
				paramsSign) + "&" + urlParams;
		// 发送请求
		byte[] responseBody = HttpClientSender.sendHttpGet(host, method,
				urlParams);
		// 返回结果解析
		return responseBody;
	} 
}
