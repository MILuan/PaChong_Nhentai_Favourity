import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Popular_CN {
	static String path_1="F:\\PaChong\\NHentai_Popular_CN\\";
	static String path_2="https://i.nhentai.net/galleries/";
	static String url ="https://nhentai.net/language/chinese/popular?page=";
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		MakeDir();
		for(int i=0;i<20;i++)
		Handle(GetHtmlSource(i,url));
		

	}
	public static void MakeDir()
	{
		String 	path="F:\\PaChong";
		File file_dir =new File(path);    
		//如果文件夹不存在则创建    
		if  (!file_dir .exists()  && !file_dir .isDirectory())      
		{ 
		    file_dir .mkdir();    
		}
		path="F:\\PaChong\\NHentai_Popular_CN";
		file_dir =new File(path);    
		//如果文件夹不存在则创建    
		if  (!file_dir .exists()  && !file_dir .isDirectory())      
		{ 
		    file_dir .mkdir();    
		}
		
		
	}

	public static String GetHtmlSource(int i,String url)
	{
		InputStream inputStream = null;
		BufferedReader in = null;
		StringBuilder htmlSource= new StringBuilder();

		try {
		//1.获取网址
		URL u = new URL(url);
		//2.打开连接
		URLConnection conn = u.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		//3.获取输入流
		inputStream = conn.getInputStream();
		//4.将源代码写入内存(设置编码)
		in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		int str = 0;
		while((str = in.read()) != (-1)){
		htmlSource.append((char)str);
		}
		} catch (Exception e) {
		e.printStackTrace();
		}finally{
		//关闭I/o
		try {
		if(in != null)in.close();
		if(inputStream != null)inputStream.close();
		} catch (IOException e) {
		e.printStackTrace();
		}

		}
		return htmlSource.toString();
	}
	
	public static void Handle(String str_all) throws InterruptedException
	{
		//jpg第一种格式

		Pattern pattern = Pattern.compile("data-src=(.+?)</a></div>");
		  // 定义一个matcher用来做匹配

		  Matcher matcher = pattern.matcher(str_all.toString());
		  // 如果找到了
		  while (matcher.find()) {
			  jpg temp = new jpg();
			  String str = new String();
			  str=""+matcher.group(1);
			  
			  Pattern pattern_id = Pattern.compile("<img src=\"https://t.nhentai.net/galleries/(.+?)/thumb");
			  // 定义一个matcher用来做匹配

			  Matcher matcher_id = pattern_id.matcher(str);
			  
			  if(matcher_id.find())
			  {
				  temp.id=""+matcher_id.group(1);
			  }
			  
			  Pattern pattern_name = Pattern.compile("<div class=\"caption\">(.+?)</div>");
			  // 定义一个matcher用来做匹配

			  Matcher matcher_name = pattern_name.matcher(str);
			  
			  if(matcher_name.find())
			  {
				  temp.name=""+matcher_name.group(1);
			  }
			  
			  download(temp);
			 
		  }
		  
		  
		  
		  
		  
// jpg第二种格式

			Pattern pattern1 = Pattern.compile("<img src=\"//t.nhentai.net/(.+?)</a></div>");
			  // 定义一个matcher用来做匹配

			  Matcher matcher1 = pattern1.matcher(str_all.toString());
			  // 如果找到了
			  while (matcher1.find()) {
				  jpg temp = new jpg();
				  String str = new String();
				  str=""+matcher1.group(1);
				  
				  Pattern pattern_id_png = Pattern.compile("galleries/(.+?)/thumb.png");
				  // 定义一个matcher用来做匹配

				  Matcher matcher_id_png = pattern_id_png.matcher(str);
				  
				  if(matcher_id_png.find())
				  {
					  temp.id=""+matcher_id_png.group(1);
					  
					  Pattern pattern_name_png = Pattern.compile("<div class=\"caption\">(.+?)</div>");
					  // 定义一个matcher用来做匹配

					  Matcher matcher_name_png = pattern_name_png.matcher(str);
					  
					  if(matcher_name_png.find())
					  {
						  temp.name=""+matcher_name_png.group(1);
						  download(temp);
						  continue;
					  }
					  
					  
				  }
				  


				  
				  
				  
				  Pattern pattern_id = Pattern.compile("galleries/(.+?)/thumb");
				  // 定义一个matcher用来做匹配

				  Matcher matcher_id = pattern_id.matcher(str);
				  
				  if(matcher_id.find())
				  {
					  temp.id=""+matcher_id.group(1);
					  Pattern pattern_name = Pattern.compile("<div class=\"caption\">(.+?)</div>");
					  // 定义一个matcher用来做匹配

					  Matcher matcher_name = pattern_name.matcher(str);
					  
					  if(matcher_name.find())
					  {
						  temp.name=""+matcher_name.group(1);
					  }
					  
					  download(temp);
				  }
				  

				 
			  }
	}

	public static void download(jpg now) throws InterruptedException
	{
		
		String type_=".jpg";
		System.out.println("Now downloading "+ now.id+" "+ now.name);
		String temp_1 =	now.name.replaceAll("\\\\", " ");	
		now.name =temp_1.replaceAll("\\\\", " "); 
		temp_1 = now.name.replaceAll("[/]", " ");
		now.name = temp_1.replaceAll("[:]", " ");
		temp_1 = now.name.replaceAll("[*]", " ");
		now.name = temp_1.replaceAll("[?]", " ");
		temp_1 = now.name.replaceAll("[<]", " ");
		now.name = temp_1.replaceAll("[>]", " ");
		temp_1 = now.name.replaceAll("\"", " ");
		now.name = temp_1.replaceAll("[|]", " ");
		//now.name = now.name.replaceAll("[|]", " ");
		String 	path=path_1+now.name;
		File file_dir =new File(path);    
		//如果文件夹不存在则创建    
		if  (!file_dir .exists()  && !file_dir .isDirectory())      
		{ 
			System.out.println("1秒后开始新文件夹的下载，当前下载内容名为 "+now.name);

					Thread.sleep(1000);
			
		    file_dir .mkdir();    
		} else   
		{  
		    return; 
		}  
		
		
		for(int i=1;;i++)
		{
			try
			{
				type_=".jpg";
				String imageUrl =path_2 +String.valueOf(now.id)+"/"+String.valueOf(i)+type_;
			//System.out.println("断点1");
				URL url = new URL(imageUrl);
//打开网络输入流
				DataInputStream dis = new DataInputStream(url.openStream());
				String newImageName=path+"//"+String.valueOf(i)+type_;
//建立一个新的文件
			//	System.out.println("断点2");
			//	System.out.println(now.name);
				FileOutputStream fos = new FileOutputStream(new File(newImageName));
			//	System.out.println("断点3");
				byte[] buffer = new byte[1024];
			//	System.out.println("断点4");
				int length;
		//开始填充数据
				//System.out.println("断点5");
				while((length = dis.read(buffer))>0){
					fos.write(buffer,0,length);
				}
				//System.out.println("断点6");
				dis.close();
				fos.close();
				System.out.println(now.name+"  "+String.valueOf(i)+type_+"下载完成");
			}
			catch(IOException e)
			{
				try
				{
					type_=".png";
					String imageUrl =path_2 +String.valueOf(now.id)+"/"+String.valueOf(i)+type_;
					URL url = new URL(imageUrl);
			//打开网络输入流
					DataInputStream dis = new DataInputStream(url.openStream());
					String newImageName=path+"//"+String.valueOf(i)+type_;
			//建立一个新的文件
					FileOutputStream fos = new FileOutputStream(new File(newImageName));
					byte[] buffer = new byte[1024];
					int length;
					//开始填充数据
					while((length = dis.read(buffer))>0){
						fos.write(buffer,0,length);
					}
					dis.close();
					fos.close();
					System.out.println(now.name+"  "+String.valueOf(i)+type_+"下载完成");
				
					
				}
				catch(IOException e_)
				{
					
					return;
				} 
			}
		}

	}
}
 class jpg {
		String name;
		//String id_html;//图床的id和html的id是不同的      发现不需要，预览页就给出了图床id
		String id;
}
