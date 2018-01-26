package com.pangmaobao.bestsign.test;

import com.pangmaobao.bestsign.test.client.BestsignOpenApiClient;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 上上签公有云SDK测试
 */
public class BestsignClientTest {

    //开发者ID
    private static String developerId = "1913678711387849312";
    //开发者私钥
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqHz5pF0q4cOVLXK9w554LFmcRZf8ZHDyHTLtFEmNI0C7V/84AzFV97mh8Xq3KQ+z9Cku6F570+egS33hAbi9JKwObJsRCZbf3rH73ydN+oLVxWlTy0G81l+kyRhw84WMSz2iYWH+7o2bql14ZWz94eg5xv183JuYiPRWbklP8aW3pj4mKQNzhyi37IEDIq/7SLOG5fg5eFhYKJRKXALqOnvY7IjwhTTs7xjoThXF3YrZ04mkqGrncr0Y9f3j1so7KloB2rmDF14dPA+CCFAj5zwiSHDeCkFDJcdD/eNncUodQtozchcFaBmaNBSF6Fo9L7E/NlyssaRtsUrWgBN/VAgMBAAECggEAF016TgjbulH0hpMmkO+ZDWm/KnnvLHBatinDiR2QrfuePr/wZtmWaE6Mz2Rkp80QHEUZxF7R7jqBGHy1yxWMspgKwWjY/0NRpMJjDev+ZVz8HY/DDROR+CRb1AAYhBpXaM8yQ5Pzwy8JvN6JDC4cjpMhgWE2WKaiZVQ6rnmnuW45hcbX/iHw7jBknHxJkH3ps/3GphZFdfsUlE6ls/zIABlAFKNo+CIt8xJJ6flMpolzZakA6vV6fvHodjFI9G1ZOvowR2BOeF7Sst9FKgdVqAlKEDF9KoYiFXPTs0JkEwZLcEOh26P3R0KETANy07tqgPUxx05S89tjBX52vls/jQKBgQD5Ozadhmo9/j6KZ3zam36dxUV151diibwqE/bmpi3CNZofLEJRgWqEVIoDZ7fXSXXSKAv34eVvcIjPGUu01k9G0oPKNgmudHCCwiDUtb1FePEPHC6i22dnPaM+YNjbo6mnPdDg0e+hq8OdDIQvpXH+qhQ9cFIvU2V4FiuRpb47cwKBgQCuvgV8oIOww/eyYXNf4WSj3oVq2u/Jy0RupT3iZ6ulIzpj9yJfgGXy5YRyxzPDE/F02e4RH2rGt6cRIdBhCHrRU+MNW1juL191bjtOYKv7gDZ1+2biE77IMVLyHyZiu6hgleAM9sWbfxldNOIM+VZzBUOW7VYHnsWsYULEXTs1lwKBgQCumGu3SM2upih73umZX2sA7YyufyU5c5HszFWf2PSfY9uXUPMYlBfhojOZFRS17dFrKwDPY2HOgsBkjKz7f1LEI3+NrfN0Uj/rakGDodl5DLOayTxmfFtg+M4eScxBedLExUpJ5OgBkwmTQIxtsHI+XDmXnNMGMl8YFDfrbXfpvwKBgBg1y5zQwG4lOJRGXC3UlJT/p4x+eOqEdx69Vi2gH2/pyZVAEEsbBwT4N7mPT+SfRrzh1NIagDi4CTWecbh/7EREUxjupwuZFKi4dQ8O5cUapnECO+bmxXAHJW7WUgMr7NA0863YItjAa0s2oHsbsJaPDCZFC75SiSCw+Qcrw6BRAoGAfJX08OpBFq7ppk4rU1s2DKQb6oJ3CK3xbFh/qZ3YdG/R9YOAaoPonPo4YVfae2D0WCmg2M+ZPFA5hr6Lp2h6iTH7Ze3p84pTyQEZe+spYlg1qTUvFXlFAgH6Y4r6K1S/IP1sdiswlGR5zpkhbfUDtCQp/DtEhO18+giKSusvLis=";


    //公有云Server的完整HOST，需要根据不同的环境配置
    /**
     * 测试环境（http）：http://openapi.bestsign.info/openapi/v2
     * 测试环境（https）：https://openapi.bestsign.info/openapi/v2
     * 正式环境（https）：https://openapi.bestsign.cn/openapi/v2
     */
    private static String serverHost = "https://openapi.bestsign.info/openapi/v2";

    //已封装的HTTP接口调用的的client，您可以使用它来调用常规api和特殊api，您可以在此BestsignOpenApiClient中添加新的接口方法方便调用。
    private static BestsignOpenApiClient openApiClient = new BestsignOpenApiClient(developerId, privateKey, serverHost);

    public static void main(String[] args) throws Exception{

        personalUserReg(); //个人用户注册       POST方法示例
        
        downloadContract("1234567890"); //下载合同pdf文件  GET方法示例
    }

    /**
     * 个人用户注册
     * [注册，设置实名信息，申请数字证书，生成默认签名]
     * @throws Exception
     */
    public static void personalUserReg() throws Exception{
        //用户注册
        String account = "18910001001"; //账号
        String name = "尚尚"; //用户名称
        String mail = ""; //用户邮箱
        String mobile = "18910001001"; //用户手机号码

        //设置个人实名信息
        String identity = "110101199301018270"; //证件号码
        String identityType = "0"; //证件类型  0-身份证
        String contactMail = ""; //联系邮箱
        String contactMobile = mobile; //联系电话
        String province = "浙江"; //所在省份
        String city = "杭州"; //所在城市
        String address = "西湖区万塘路317号华星世纪大楼102"; //联系地址
       
        //注册返回异步申请证书任务id
        String taskId = openApiClient.userPersonalReg(account, name, mail, mobile, identity, identityType, contactMail, contactMobile, province, city, address);
        //查询任务状态
        System.out.println("account="+account+" taskId:"+taskId);
        
    }
    
    /**
     * 下载合同
     * @throws Exception
     */
    public static void downloadContract(String contractId) throws Exception{
        byte[] pdf = openApiClient.contractDownload(contractId);
        byte2File(pdf, "D:\\test", contractId+".pdf"); //文件下载到本地目录
        System.out.println("download contract pdf path=" + "D:\\test\\" + contractId+".pdf");
    }

    /**
     * 辅助方法，本地文件转为byte[]
     * @param filePath
     * @return
     */
    private static byte[] inputStream2ByteArray(String filePath) {
        byte[] data = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(filePath);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            data = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                //ignore
            }
        }
        return data;
    }

    /**
     * 辅助方法，byte数组保存为本地文件
     * @param buf
     * @param filePath
     * @param fileName
     */
    private static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
