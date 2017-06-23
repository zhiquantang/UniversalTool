package com.jutong.live.util;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @date 2016/6/16 zhiquan.tang
 */
public class SocketUtil {
	private static final String TAG = "Socket_Android";  
	private String ip = HttpUtils.SOCKET_URL;
	private int port = 12345;
	private String name = "123";
	private String unumber;
	private String room = "666";
	private String send_content = "发送的测试数据···";
	
	private Socket socket = null;
	private InputStream in;
	private OutputStream out;
	private int state;
	Handler inHandler;
	private int times = 0;
	private boolean isbegin;
	
	public SocketUtil(){}
	
	public void setting(String ip, int port, String name, String unumber, String room, int state ,Handler handlerin){
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.unumber = unumber;
		this.room = room;
		this.state = state;
		this.inHandler = handlerin;
	}
	
	public void startSocket(boolean isbegin){
		this.isbegin = isbegin;
		new Thread(networkTask).start();  
	}
	
	public void send(String content,boolean isbegin){
		this.isbegin = isbegin;
		send_content = content;
		new Thread(networkTask).start();  
	}
	
	public void send(String content,boolean isbegin,int state){
		this.isbegin = isbegin;
		this.state = state;
		send_content = content;
		new Thread(networkTask).start();  
	}
	
	/** 
     * 网络操作相关的子线程 
     */  
    Runnable networkTask = new Runnable() {  
      
        @Override  
        public void run() {  
        	try {
        		Log.v("", "ip >>> "+ip);
        		Log.v("", "port >>> "+port);
        		Log.v("", "name >>> "+name);
        		Log.v("", "unumber >>> "+unumber);
        		Log.v("", "state >>> "+state);
        		Log.v("", "isbegin >>> "+isbegin);
	        	if(isbegin){
	    			socket = new Socket(ip, port);
	    		}
	        	if(state == 10){
	        		isbegin = false;
	        	}
                // 发送信息给服务器
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", send_content);
                jsonObject.put("name", name);
                jsonObject.put("unumber", unumber);
                jsonObject.put("room", room);
                if(times < 1){
                	jsonObject.put("statue", state);
                	times = 1;
                }else if(state == 40){
                	jsonObject.put("statue", "40");
                }else if(state == 41){
                	jsonObject.put("statue", "41");
                }else{
                	jsonObject.put("statue", "0");
                	times++;
                }
                byte[] head = {00, 00, 01};
            	byte[] tail = {00, 00, 02};
                byte[] message_bytes_content = (jsonObject.toString()).getBytes();
                List<byte[]> bytes = new ArrayList<byte[]>();
                bytes.add(head);
                bytes.add(message_bytes_content);
                bytes.add(tail);
                byte[] newByte = sysCopy(bytes);
//            	Log.d(TAG, "C:Write To server >> " + new String(newByte).trim() ); 
                out = socket.getOutputStream();
                out.write(newByte);
                // 接收服务器返回的信息    【第一次弄，花了挺长的时间】
                try {
                	byte[] temp = new byte[256];//控制返回的大小size
                	byte[] temp2 = null;//控制返回的大小size
                	in = socket.getInputStream();
                	int i=0;  
                	int k = 0;
                	while ((i = in.read(temp)) != -1){
                		//通过结束符截取有效信息（处理粘包的问题）
                		outer:for (int j = 0; j < 256; j++) {
							if(temp[j] == tail[2]){
								if(i <3)
									throw new NullPointerException("下标越界");
								if(temp[j-1]==tail[1] && temp[j-2] == tail[0]){
									temp2 = new byte[j+1];
									System.arraycopy(temp, 0, temp2, 0, temp2.length);
									break outer;
								}
							}
						}
                		
                		String s = new String(temp2).trim();
//                    	Log.d(TAG, "C:To From server >> " + s ); 
//                    	Log.d(TAG, "C:To isbegin >> " + isbegin ); 
                    	if(!isbegin){
	                        // 在页面上进行显示     
	                    	Message msg = inHandler.obtainMessage();
	                    	msg.obj = s;
	                    	inHandler.sendMessage(msg);// 结果返回给UI处理
                    	}else if (unumber!=room){
                    		// 在页面上进行显示     
	                    	Message msg = inHandler.obtainMessage();
	                    	msg.obj = "";
	                    	inHandler.sendMessage(msg);// 结果返回给UI处理
	                    	Message msg2 = inHandler.obtainMessage();
	                    	msg2.obj = s;
	                    	inHandler.sendMessage(msg2);// 结果返回给UI处理
                    	}
                	}
                	in.close();  
                }catch (Exception e) {
                    e.printStackTrace();
                }
            } catch(UnknownHostException e) {  
                Log.e(TAG, "C:"+ip+" is unkown server!");  
            } catch(Exception e) {  
                e.printStackTrace();  
            } 
        	
    	}
    }; 
    
    public void stopSocket() {
		try {
            if (socket != null) {
//                Log.i(TAG, "close in");
//                in.close();
                Log.i(TAG, "close out");
                out.close();
                Log.i(TAG, "close client");
                socket.close();
            }
        } catch (Exception e) {
            Log.i(TAG, "close err");
            e.printStackTrace();
        }
        Log.i(TAG, "Socket已终止");
    }
    
    /**  
     * bytes转换成十六进制字符串  
     * @param byte[] b byte数组  
     * @return String 每个Byte值之间空格分隔  
     */    
    public static String byte2HexStr(byte[] b)    
    {    
        String stmp="";    
        StringBuilder sb = new StringBuilder("");    
        for (int n=0;n<b.length;n++)    
        {    
            stmp = Integer.toHexString(b[n] & 0xFF);    
            sb.append((stmp.length()==1)? "0"+stmp : stmp);    
            sb.append(" ");    
        }    
        return sb.toString().toUpperCase().trim();    
    }   
    
    /**
     * 系统提供的数组拷贝方法arraycopy
     * */
    public static byte[] sysCopy(List<byte[]> srcArrays) {
     int len = 0;
     for (byte[] srcArray:srcArrays) {
      len+= srcArray.length;
     }
        byte[] destArray = new byte[len];   
        int destLen = 0;
        for (byte[] srcArray:srcArrays) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);   
            destLen += srcArray.length;   
        }   
        return destArray;
    }  
}
