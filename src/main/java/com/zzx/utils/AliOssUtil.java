package com.zzx.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-12 18:43
 */
public class AliOssUtil {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    private static final String OSS_ACCESS_KEY_ID="LTAI5tPECae2fF588xrxVwmz";
    private static final String OSS_ACCESS_KEY_SECRET="munFWjMPKqrBTEJXcVbiNIS2ImwTph";
    // 填写Bucket名称，例如examplebucket。
    private static final String BUCKET_NAME = "zzx-big-event";
    public static String uploadFile(String objectName, InputStream in) throws Exception {

       /* // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = "001.jpg";*/
        String url="";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT,OSS_ACCESS_KEY_ID,OSS_ACCESS_KEY_SECRET);

        try {
            // 填写字符串。
            String content = "Hello OSS，你好世界";

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, in);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            //阿里云bucket地址https://oss-cn-beijing.aliyuncs.com
            //图像的地址https://zzx-big-event.oss-cn-beijing.aliyuncs.com/001.jpg
            url="https://"+BUCKET_NAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
