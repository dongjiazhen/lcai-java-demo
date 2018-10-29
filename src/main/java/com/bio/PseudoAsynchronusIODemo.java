/**   
* @Title:：PseudoAsynchronusIODemo.java 
* @Package ：com.bio 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月29日 下午1:13:36 
* @version ： 1.0   
*/
package com.bio;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 采用伪异步IO解决多线程出现Down掉情况
 * @author Administrator
 *
 */
public class PseudoAsynchronusIODemo {

	  /**
	   * 测试方法是： 打开windows的运行cmd窗口： 输入：  telnet   localhost 7777 ，连接到ServerSocket服务。
	   * 测试时，开启多个命令窗口进行测试。
	   * @Title：main 
	   * @Description：TODO
	   * @param ：@param args
	   * @param ：@throws Exception 
	   * @return ：void 
	   * @throws
	  */
      public static void main(String[] args)throws Exception 
      {
	
		//创建一个线程池,在线程池中是不限制线程数量。创建Thread，是在newCachedThreadPool这里面实现了封装new线程。里面的线程是可以复用的。
    	//ExecutorService threadPool = Executors.newCachedThreadPool();
		//创建一个线程池，在线程池中线程数目是固定的,例如下面例子：创建了一个固定的100个线程的线程池。如果超过了100个线程，超过的线程的任务是不能执行了。
    	  ExecutorService threadPool = Executors.newFixedThreadPool(100);
		//创建socket服务，监听7777端口。
		ServerSocket serverSocket = new ServerSocket(7777);
		System.out.println("服务端启动....................");
		while(true) {
			//获取socket套接字
			//accept()阻塞点
			final Socket socket = serverSocket.accept();
			System.out.println("有新客户端连接上来了...........");
			//在线程池中执行线程Task。首先创建Runnable，实现Run方法。执行Task任务,执行客户端发过来的请求
			threadPool.execute(new Runnable() {

				public void run() {
					
					try {
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
					}catch(Exception e) {
						e.getMessage();
					}
				}
				
			});
		}
	}
	
}
