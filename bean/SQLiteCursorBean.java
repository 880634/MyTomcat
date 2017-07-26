package com.hjq.mytomcat.bean;

import java.util.ArrayList;
import org.json.JSONObject;
/**
 * @author HJQ
 */
public class SQLiteCursorBean {

	/**
	 * ���ݿ������䷵��ֵ����Cursor������
	 * Database operation statements return values or Cursor count
	 */
	public long result;
	
	/**
	 * ���ص�cursor����
	 * return cursor column name
	 */
	public ArrayList<String> columnNames;
	
	/**
	 * ����cursor�еĽ������������Զ�����ı�JSONObject��Ϊ����
	 * return cursor the results in, Here you can customize class change the JSONObject such as generics
	 */
	public ArrayList<JSONObject> cursor;
}
