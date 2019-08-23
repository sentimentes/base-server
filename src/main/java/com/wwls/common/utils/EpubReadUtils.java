package com.wwls.common.utils;

import java.io.FileInputStream;

import com.omt.epubreader.domain.Book;
import com.omt.epubreader.domain.Epub;

public class EpubReadUtils {
	
	
	public static Book getEpubBook(String path){
		try{
		Epub epub = new Epub();
		
        Book book = epub.getBook(new FileInputStream(path));
		
		return book;
		
		}catch(Exception  e){
			e.printStackTrace();
		}
		return null;
	}
	
	

}
