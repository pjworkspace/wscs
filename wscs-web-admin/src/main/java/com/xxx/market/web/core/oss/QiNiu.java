package com.xxx.market.web.core.oss;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.log4j.Logger;
import java.io.File;


/**
 * https://developer.qiniu.com/dora/manual/1297/file-hash-value-qhash
 * https://developer.qiniu.com/kodo/kb/1321/how-to-acquire-the-outside-storage-file-links
 * @author jun
 */
public class QiNiu {
    private static Logger logger = Logger.getLogger(QiNiu.class);

    protected static String accessKey ;
    protected static String secretKey ;
    protected static String bucket ;
    protected static String domain ;
    protected static Long expireSeconds ;
    static {
        accessKey = PropKit.get("qiniu.accessKey");
        secretKey = PropKit.get("qiniu.secretKey");
        bucket = PropKit.get("qiniu.bucket");
        domain = PropKit.get("qiniu.domain");
        expireSeconds = PropKit.getLong("qiniu.expireSeconds");
    }

    public static String getUpToken(){
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":\"$(fsize)\",\"fname\":\"$(fname)\",\"ftype\":\"$(mimeType)\"}");
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":\"$(fsize)\",\"fname\":\"$(fname)\",\"ftype\":\"$(mimeType)\"}");
        putPolicy.put("callbackBodyType", "application/json");
        String token = Auth.create(accessKey, secretKey).uploadToken(bucket,null,expireSeconds,putPolicy);
        return token;
    }

    /**
     * 获取文件信息
     * @param key
     * @return
     */
    public static FileInfo getFileInfo(String key){
        Configuration cfg = new Configuration(Zone.autoZone());
        BucketManager bucketManager = new BucketManager(Auth.create(accessKey, secretKey), cfg);
        FileInfo fileInfo = null;
        try {
            fileInfo = bucketManager.stat(bucket, key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }

    /**
     * 上传文件
     * @param key 文件名
     * @param file 文件
     * @return 文件地址
     */
    public static String upload(String key, byte[] file){

        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        JSONObject jsonObject = new JSONObject();
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        //调用put方法上传
        Response res = null;
        try {
            res = uploadManager.put(file,key,getUpToken());
            logger.info(res.bodyString());
            jsonObject = JSON.parseObject(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.info(r.toString());
        }
        return domain+"/"+jsonObject.get("key");
    }

    /**
     * 上传文件
     * @param key 文件名
     * @param file 文件
     * @return 文件地址
     */
    public static String upload(String key, File file) {
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        JSONObject jsonObject = new JSONObject();
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        try {
            //调用put方法上传
            Response res = uploadManager.put(file,key,getUpToken());
            //打印返回的信息
            logger.info(res.bodyString());
            jsonObject = JSON.parseObject(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.info(r.toString());
        }
        return domain+"/"+jsonObject.get("key");
    }


}
