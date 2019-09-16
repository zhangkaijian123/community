package com.xuewen.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.xuewen.community.exception.CustomizeErrorCode;
import com.xuewen.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 16:01
 **/
@Service
public class UCloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.region}")
    private String region;

    @Value("${ucloud.ufile.suffix}")
    private String suffix;

    @Value("${ucloud.ufile.expires}")
    private Integer expires;


    public String upload(InputStream fileStream, String mimeType, String fileName){
        // 对象相关API的授权器
        ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(
                publicKey, privateKey);

        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(region, suffix);

        String generateFileName = "";
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1){
            generateFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        }else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_ERROR);
        }
        try {

            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();

                    if (response != null && response.getRetCode() == 0){
                        String url = UfileClient.object(OBJECT_AUTHORIZER,config)
                                    .getDownloadUrlFromPrivateBucket(generateFileName,bucketName,expires)
                                    .createUrl();
                        return url;
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_ERROR);
                    }
        } catch (UfileClientException e) {
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
