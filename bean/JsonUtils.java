package com.hjq.mytomcat.bean;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;
/**
 * @author HJQ
 */
public class JsonUtils {
	
	//�ͻ��˵�ת������
	//The client's conversion tool
	
	/**
	 * ���JSONObject����
	 * Add a JSONObject object
	 */
	public static void addJSONObject(JSONObject json, String name, String string) throws JSONException{
		if (string != null && !string.equals("")) {
			json.put(name, string);
		}
	}
	
	/**
	 * ���JSONArray����
	 * Add a JSONArray object
	 */
	public static void addJSONArray(JSONObject json, String name, Object[] objects) throws JSONException{
		if (objects != null && objects.length != 0) {
			json.put(name, ArrayToJSONArray(objects));
		}
	}
	
	/**
	 * ���ContentValues����
	 * Add a ContentValues object
	 */
	public static void addContentValues(JSONObject json, String name, ContentValues values) throws JSONException{
		if (values != null && values.size() != 0) {
			json.put(name, ContentValuesToJSONArray(values));
		}
	}
	
	
	/**
	 * ����תJsonArray
	 * Array conversion to JsonArray
	 */
	public static JSONArray ArrayToJSONArray(Object[] array) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < array.length; i++) {
			jsonArray.put(array[i]);
		}
		return jsonArray;
	}
	
	/**
	 * ��ContentValuesת����JSONArray
	 * ContentValues conversion to JSONArray
	 */
	public static JSONArray ContentValuesToJSONArray(ContentValues values) throws JSONException {
		
		JSONArray jsonArray = new JSONArray();
		Set<Entry<String, Object>> valueSet = values.valueSet();
		
		for(Iterator<Entry<String, Object>> iterator = valueSet.iterator(); iterator.hasNext(); ){
			Entry<String, Object> entry = iterator.next();
			jsonArray.put(new JSONObject().put(entry.getKey(), entry.getValue()));
		}
		
		return jsonArray;		
	}
	
	
	
	//��������ת������
	//Server conversion tools
	
	/**
	 * ��ȡ��ѯ�Ľ������Cursor��װ��Json��
	 * Get the results of the query, the Cursor encapsulated in Json
	 */
	public static String getJsonString(Cursor cursor) throws JSONException {
		
		int count = cursor.getCount();
		
		if (count > 0) {
			
			JSONObject  resultJsonObject = new JSONObject();
			
			JSONArray columnNamesJsonArray = new JSONArray();
			String[] columnNames = cursor.getColumnNames();
			for (int i = 0; i < columnNames.length; i++) {
				columnNamesJsonArray.put(columnNames[i]);
			}
			resultJsonObject.put("columnNames", columnNamesJsonArray);
			
			JSONArray cursorJsonArray = new JSONArray();
			JSONObject  valuesJsonObject;
			
			while(cursor.moveToNext()){
				valuesJsonObject = new JSONObject();
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					valuesJsonObject.put(columnName, cursor.getString(i));
				}
				
				cursorJsonArray.put(valuesJsonObject);
			}
			//�ر�cursor
			cursor.close();
			
			resultJsonObject.put("cursor", cursorJsonArray);
			
			return resultJsonObject.toString();
		}
		
		return null;
	}
	
	/**
	 * ��JsonArrayת���ַ�������
	 * JsonArray conversion to array of strings
	 */
	public static String[] JsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
		
		if (jsonArray != null) {
			
			int length = jsonArray.length();
			
			if (length > 0) {
				String[] strings = new String[length];
				for (int i = 0; i < length; i++) {
					strings[i] = jsonArray.getString(i);
				}
				return strings;
			}
		}
		
		return null;
	}
	
	/**
	 * ��JsonArrayת��ContentValues
	 * JsonArray conversion to ContentValues
	 */
	public static ContentValues JsonArrayToContentValues(JSONArray jsonArray) throws JSONException {
		
		if (jsonArray != null) {
			
			int length = jsonArray.length();
			
			if (length > 0) {
				
				ContentValues contentValues = new ContentValues();
				
				JSONObject jsonObject;
				for (int i = 0; i < length; i++) {
					
					jsonObject = jsonArray.getJSONObject(i);
					
					JSONArray namesJSONArray = jsonObject.names();
					
					int length2 = namesJSONArray.length();
					for (int j = 0; j < length2; j++) {
						String name = namesJSONArray.getString(j);
						String value = jsonObject.getString(name);
						contentValues.put(name, value);
					}
				}
				return contentValues;
			}
		}
		
		return null;
	}
}
