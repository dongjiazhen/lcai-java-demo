/**   
* @Title:：TraditionalSocketDemo.java 
* @Package ：com.bio 
* @Description：传统Socket阻塞案例
* @author： lcai   
* @date： 2018年10月26日 下午3:27:51 
* @version ： 1.0   
*/
package com.bio;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TraditionalSocketDemo {

	/**
	 * 测试方法是： 打开windows的运行cmd窗口： 输入：  telnet   localhost 7777 ，连接到ServerSocket服务。
	 * 测试时，开启多个命令窗口进行测试。
	 * @Title：main 
	 * @Description：TODO
	 * @param ：@param args 
	 * @return ：void 
	 * @throws
	 */
	public static void main(String[] args) {
		
		try {
			/**
			 * 当有一个客户端client01访问socket服务的时候，一直占用着这个socket连接。一直等待着（长连接）客户端那边发送数据过来，执行任务。
			 * 当又有一个客户端client02访问的时候，是连接上了这个socket服务连接，不过无法发送数据到socket中，client02一直处于等待状态(wait)。只有等到客户端client01的连接关闭（退出logout），
			 * 这个时候，client02才能连接上来(connect running)。
			 */
			ServerSocket serverSocket = new ServerSocket(7777);
			System.out.println("服务端启动....................");
			while(true) {
				//获取socket套接字
				//accept()阻塞点
				Socket socket = serverSocket.accept();
				System.out.println("有新客户端连接上来了...........");
				//获取客户端输入流
				InputStream is = socket.getInputStream();
				byte[] b = new byte[1024];
				while(true) {
					//循环读取数据
					//read()阻塞点
					int data = is.read(b);
					if(data!=-1) {
						String info = new String(b,0,data,"utf-8");
						System.out.println(info);
					}else {
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
