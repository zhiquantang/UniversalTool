package test.ruili.com.universaltool.Utils.Encryp;

/**
 * Created by TOM on 2017/5/27.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncrypSHA {

    public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA");
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }
}
