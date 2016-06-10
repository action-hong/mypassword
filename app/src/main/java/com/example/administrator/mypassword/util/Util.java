package com.example.administrator.mypassword.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.mypassword.db.MyPasswordDB;
import com.example.administrator.mypassword.model.MyPassword;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Util {

	private static File file;

	public static String[] strings = {"名称","账号","密码","其他"};
	public static String[] strs = {"id","名称","账号","密码","其他","创建时间","访问时间"};

	public static String getTime(String string) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		Long i = Long.parseLong(string);
		Date curData = new Date(i);
		String str = format.format(curData);
		return str;
	}

	public static void sendPassword(Context context) {
		MyPasswordDB myPasswordDB = MyPasswordDB.getInstance(context);
		List<MyPassword> mList = myPasswordDB.loadPsswords();
		Cursor cursor = myPasswordDB.getCursor();
		String allDataFileName = "test.csv";
		String someDataFileName = "test2.csv";
		ExportToCSV(cursor, allDataFileName);//全放
		ExportToCSV(mList, someDataFileName);//只放名稱,賬號,密碼,時間
		sendEmail(context,someDataFileName);
	}

	/**
	 * 从内存文件中将文件取出来发邮件
	 */
	public static void sendEmail(Context context,String fileName) {
		Intent eMail = new Intent(android.content.Intent.ACTION_SEND);
		//他妈test.csv打错
		file = new File(Environment.getExternalStorageDirectory(), fileName);
		eMail.setType("application/octet-stream");
		//Log.d("hah", Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)+"");
		String eMailTitle = "我的密码";
		String eMailContent = "我的密码";
		//设置邮件标题
		eMail.putExtra(android.content.Intent.EXTRA_SUBJECT, eMailTitle);
		//设置发送的内容
		eMail.putExtra(android.content.Intent.EXTRA_TEXT, eMailContent);
		//附件
		eMail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		//调用系统的邮件系统
		context.startActivity(Intent.createChooser(eMail, "请选择邮件发送软件"));
		file.deleteOnExit();
	}

	/**
	 * 只放名称，账号，密码，其他
	 *
	 * @param list
	 * @param fileName
	 */
	public static void ExportToCSV(List<MyPassword> list, String fileName) {
		FileWriter fw;
		BufferedWriter bfw;
		File sdCardDir = Environment.getExternalStorageDirectory();
		File saveFile = new File(sdCardDir, fileName);

		try {
			fw = new FileWriter(saveFile);
			bfw = new BufferedWriter(fw);
			// 填写表头
			for (int i = 0; i < strings.length; i++) {
				if (i != strings.length - 1)
					bfw.write(strings[i] + ",");
				else
					bfw.write(strings[i]);
			}
			// 换行
			bfw.newLine();
			for (int j = 0; j < list.size(); j++) {
				bfw.write(list.get(j).getName() + ",");
				bfw.write(list.get(j).getAccount() + ",");
				bfw.write(list.get(j).getPassword() + ",");
				bfw.write(list.get(j).getOthers() + " ");//加个空,没有填other的话取到null会报错
				bfw.newLine();
			}
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void ExportToCSV(Cursor c, String fileName) {

		int rowCount = 0;
		int colCount = 0;
		FileWriter fw;
		BufferedWriter bfw;
		File sdCardDir = Environment.getExternalStorageDirectory();
		Log.d("haha", sdCardDir.getAbsolutePath());
		File saveFile = new File(sdCardDir, fileName);
		try {
			rowCount = c.getCount();
			colCount = c.getColumnCount();
			fw = new FileWriter(saveFile);
			bfw = new BufferedWriter(fw);
			if (rowCount > 0) {
				c.moveToFirst();
				// 写入表头
				for (int i = 0; i < strs.length; i++) {
					if (i != strs.length - 1)
						bfw.write(strs[i] + ',');
					else
						bfw.write(strs[i] + ',');
				}
				// 写好表头后换行
				bfw.newLine();
				// 写入数据
				for (int i = 0; i < rowCount; i++) {
					c.moveToPosition(i);
					// Toast.makeText(mContext, "正在导出第"+(i+1)+"条",
					// Toast.LENGTH_SHORT).show();
					Log.v("导出数据", "正在导出第" + (i + 1) + "条");
					for (int j = 0; j < colCount; j++) {
						if (j != colCount - 1)
							bfw.write(c.getString(j) + ',');
						else
							bfw.write(c.getString(j));
					}
					// 写好每条记录后换行
					bfw.newLine();
				}
			}
			// 将缓存数据写入文件
			bfw.flush();
			// 释放缓存
			bfw.close();
			// Toast.makeText(mContext, "导出完毕！", Toast.LENGTH_SHORT).show();
			Log.v("导出数据", "导出完毕！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			c.close();
		}
	}





}
