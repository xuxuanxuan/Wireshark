import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Statistics {

	public static void main(String[] args) throws IOException {
		//读入从wireshark导出的文件，注意修改路径及文件名
		BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\joyxxx\\Desktop\\1")));
		String line = reader.readLine();
		List<Record> list = new ArrayList<Record> ();
		
		 while (true) {		    
		    Record record = new Record();
		    
		    while((line = reader.readLine()) != null){

		    	if(line.trim().startsWith("Internet Protocol Version 4")){
		    		String temp[] = line.trim().split(" ");
		    		String ip = temp[temp.length - 2];
		    		record.setIp(ip);
		    		continue;
		    	}
		    	
		    	if(line.trim().startsWith("Host:")){
		    		String host = line.trim().substring(6, line.trim().length()-4);
		    		record.setHost(host);
		    		continue;
		    	}
		    	
		    	if(line.trim().startsWith("User-Agent:")){
		    		String ua = line.trim().substring(12, line.trim().length()-4);
		    		record.setUa(ua);
		    		continue;
		    	}
		    	
		    	if(line.trim().startsWith("Request URI:")){
		    		String url = line.trim().substring(13, line.trim().length());
		    		record.setUrl(url);
		    		continue;
		    	}
		    	
	    		if(line.trim().startsWith("No.")){
	    			break;
	    		}
		    }
		    list.add(record);
		    
		    if(line == null){
		    	break;
		    }
		 }
		 reader.close();
		 
		 HashMap<String, Integer> uaMap = new HashMap<String, Integer>();
		 HashMap<String, Integer> urlMap = new HashMap<String, Integer>();
		 HashMap<String, Integer> hostMap = new HashMap<String, Integer>();
		 HashMap<String, Integer> ipMap = new HashMap<String, Integer>();
		 
		 Iterator<Record> it = list.iterator();   
	     while(it.hasNext()){   
	    	 Record record = it.next();
	    	 String ua = record.getUa();
	    	 if(uaMap.containsKey(ua)){
					int times = uaMap.get(ua);
					uaMap.put(ua, times+1);
			}
			else{
				uaMap.put(ua, 1);
			}
	    	 
	    	String url = record.getUrl();
	    	if(urlMap.containsKey(url)){
				int times = urlMap.get(url);
				urlMap.put(url, times+1);
			}
			else{
				urlMap.put(url, 1);
			}
	    	    	 
	    	String host = record.getHost();
	    	if(hostMap.containsKey(host)){
				int times = hostMap.get(host);
				hostMap.put(host, times+1);
			}
			else{
				hostMap.put(host, 1);
			}
	    	
	    	String ip = record.getIp();
	    	if(ipMap.containsKey(ip)){
				int times = ipMap.get(ip);
				ipMap.put(ip, times+1);
			}
			else{
				ipMap.put(ip, 1);
			}
	     }
	     
	     //导出的host统计结果，注意修改路径
	     BufferedWriter writerHost = new BufferedWriter(new FileWriter(new File("C:\\Users\\joyxxx\\Desktop\\host.txt"), true));	     
	     List<Map.Entry<String, Integer>> hostList = new ArrayList<Map.Entry<String, Integer>> (hostMap.entrySet());
	     Collections.sort(hostList, new Comparator<Map.Entry<String, Integer>>(){
	    	 public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
	    		 return (o2.getValue() - o1.getValue());
	    	 }
	     });	     
	     for(int i = 0; i < hostList.size(); i++){
	    	 Entry<String, Integer> ent = hostList.get(i);
	    	 writerHost.write(ent.getKey() + ":" + ent.getValue());
			 writerHost.newLine();
	     }
	     writerHost.close();
	     
	     //导出的url统计结果，注意修改路径
		 BufferedWriter writerUrl = new BufferedWriter(new FileWriter(new File("C:\\Users\\joyxxx\\Desktop\\url.txt"), true));		 
		 Iterator iterUrl = urlMap.keySet().iterator();
		 while (iterUrl.hasNext()){
			String key = (String) iterUrl.next();
			Integer value = urlMap.get(key);
			writerUrl.write(key + ":" + value);
			writerUrl.newLine();
		 }
		 writerUrl.close();
		 
		 //导出的ua统计结果，注意修改路径
		 BufferedWriter writerUa = new BufferedWriter(new FileWriter(new File("C:\\Users\\joyxxx\\Desktop\\ua.txt"), true));
		 List<Map.Entry<String, Integer>> uaList = new ArrayList<Map.Entry<String, Integer>> (uaMap.entrySet());
	     Collections.sort(uaList, new Comparator<Map.Entry<String, Integer>>(){
	    	 public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
	    		 return (o2.getValue() - o1.getValue());
	    	 }
	     });	     
	     for(int i = 0; i < uaList.size(); i++){
	    	 Entry<String, Integer> ent = uaList.get(i);
	    	 writerUa.write(ent.getKey() + ":" + ent.getValue());
			 writerUa.newLine();
	     }
		 writerUa.close();
		 
		 //导出的ip统计结果，注意修改
		 BufferedWriter writerIp = new BufferedWriter(new FileWriter(new File("C:\\Users\\joyxxx\\Desktop\\ip.txt"), true));
		 List<Map.Entry<String, Integer>> ipList = new ArrayList<Map.Entry<String, Integer>> (ipMap.entrySet());
	     Collections.sort(ipList, new Comparator<Map.Entry<String, Integer>>(){
	    	 public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
	    		 return (o2.getValue() - o1.getValue());
	    	 }
	     });	     
	     for(int i = 0; i < ipList.size(); i++){
	    	 Entry<String, Integer> ent = ipList.get(i);
	    	 writerIp.write(ent.getKey() + ":" + ent.getValue());
			 writerIp.newLine();
	     }
		 writerIp.close();
	}
	
}
