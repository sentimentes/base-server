package com.wwls.common.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wwls.common.mapper.JsonMapper;

public class Demo3 {
	public static void main(String []args){
		
		
		
		List list =  readFileByLines("E:/uploadfile/a.txt");
        //List list = new ArrayList<String>();
        //list.add("80e989c760794a40ac5ff3c40a820c72");
    	Map<String,List<String>> result = new HashMap<String,List<String>>();
		result.put("productIdList", list);
		String productIdList = JsonMapper.toJsonString(result);
	    System.out.println(productIdList);
		//HttpClientUtils.post(productIdList,"api/product/server/mcProduct/getProduct");
		
	}
	public static List<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                list.add(tempString.trim());
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }

}
