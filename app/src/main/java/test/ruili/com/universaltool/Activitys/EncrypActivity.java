package test.ruili.com.universaltool.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import test.ruili.com.universaltool.R;
import test.ruili.com.universaltool.Utils.Encryp.EncrypAES;
import test.ruili.com.universaltool.Utils.Encryp.EncrypDES;
import test.ruili.com.universaltool.Utils.Encryp.EncrypDES3;
import test.ruili.com.universaltool.Utils.Encryp.EncrypMD5;
import test.ruili.com.universaltool.Utils.Encryp.EncrypRSA;
import test.ruili.com.universaltool.Utils.Encryp.EncrypSHA;

public class EncrypActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryp);

        //双向加密，对称加密
        Test_EncrypDES();
        Test_EncrypDES3();
        Test_EncrypAES();
        //双向加密，非对称加密
        Test_EncrypRSA();
        //单向加密
        Test_EncrypMD5();
        Test_EncrypSHA();

    }

    /**
     * DES算法加密
     */
    private void Test_EncrypDES(){
        try {
            EncrypDES de1 = new EncrypDES();
            String msg ="郭XX-搞笑相声全集";
            byte[] encontent = de1.Encrytor(msg);
            byte[] decontent = de1.Decryptor(encontent);
            System.out.println("明文是:" + msg);
            System.out.println("加密后:" + new String(encontent));
            System.out.println("解密后:" + new String(decontent));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * DES3算法加密
     */
    private void Test_EncrypDES3(){
        try {
            EncrypDES3 des = new EncrypDES3();
            String msg ="郭XX-搞笑相声全集";
            byte[] encontent = des.Encrytor(msg);
            byte[] decontent = des.Decryptor(encontent);
            System.out.println("明文是:" + msg);
            System.out.println("加密后:" + new String(encontent));
            System.out.println("解密后:" + new String(decontent));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * AES算法加密
     */
    private void Test_EncrypAES(){
        try {
            EncrypAES de1 = new EncrypAES();
            String msg ="郭XX-搞笑相声全集";
            byte[] encontent = de1.Encrytor(msg);
            byte[] decontent = de1.Decryptor(encontent);
            System.out.println("明文是:" + msg);
            System.out.println("加密后:" + new String(encontent));
            System.out.println("解密后:" + new String(decontent));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * RSA算法加密
     */
    private void Test_EncrypRSA(){
        try {
            EncrypRSA rsa = new EncrypRSA();
            String msg = "郭XX-精品相声";
            //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024);
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            //得到公钥
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

            //用公钥加密
            byte[] srcBytes = msg.getBytes();
            byte[] resultBytes = rsa.encrypt(publicKey, srcBytes);

            //用私钥解密
            byte[] decBytes = rsa.decrypt(privateKey, resultBytes);

            System.out.println("明文是:" + msg);
            System.out.println("加密后是:" + new String(resultBytes));
            System.out.println("解密后是:" + new String(decBytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * MD5算法加密
     */
    private void Test_EncrypMD5(){
        try {
            String msg = "郭XX-精品相声技术";
            EncrypMD5 md5 = new EncrypMD5();
            byte[] resultBytes = md5.eccrypt(msg);

            System.out.println("密文是：" + new String(resultBytes));
            System.out.println("明文是：" + msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * SHA 算法加密
     */
    private void Test_EncrypSHA(){
        try {
            String msg = "郭XX-精品相声技术";
            EncrypSHA sha = new EncrypSHA();
            byte[] resultBytes = sha.eccrypt(msg);
            System.out.println("明文是：" + msg);
            System.out.println("密文是：" + new String(resultBytes));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
