/**   
* @Title:：TraditionalMultiThreadSocketDemo.java 
* @Package ：com.bio 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月29日 下午12:22:48 
* @version ： 1.0   
*/
package com.bio;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class TraditionalMultiThreadSocketDemo {

	/**
	 * 测试方法是： 打开windows的运行cmd窗口： 输入：  telnet   localhost 7777 ，连接到ServerSocket服务。
	   * 测试时，开启多个命令窗口进行测试。
	 * 实现NIO：相当于 餐厅  （请了多个服务员、一对一为顾客服务）、餐厅大门：（IP和port）
	 * 引入多线程。这时来一个客人就会 new  Thread一个线程。来100个顾客。就创建100个线程。而且创建的线程是不可复用的。一直占用着资源。这会使这个服务ServerSocket一直处于忙碌状态。占用资源太多cpu。导致无法做其它事情和最终服务器会down掉。
	 * 所以这种办法不可取。解决办法是引入伪异步IO（主要用到线程池）
	 * @Title：main 
	 * @Description：TODO
	 * @param ：@param args 
	 * @return ：void 
	 * @throws
	 */
	public static void main(String args[])throws Exception {
		
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
			final Socket socket = serverSocket.accept();
			System.out.println("有新客户端连接上来了...........");
			//*********获取一个客户端连接之后，交给一个线程来处理后续的请求**********************
			new Thread(new Runnable() {

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
								System.out.println(info+"\t");
							}else {
								break;
							}
						}
						
					}catch (Exception e) {
					}
					
				}
				
			}).start();
        }
    }
}
