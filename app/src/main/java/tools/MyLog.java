package tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

public class MyLog {
	/**
	 * true:��������־ false:�ر�������־
	 */
	public static boolean CLOSE_ALL_LOG = false;

	/**
	 * ���Ƶ�log
	 * 
	 * @param text
	 */
	static public void showLog(Context context, String text) {
		if (!CLOSE_ALL_LOG) {
			StackTraceElement mStackTraceElement = getStackTraceElement();
			Class mclass = null;
			try {
				mclass = Class.forName(mStackTraceElement.getClassName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//û��ʵ��ThisClassCloseLog��־�ӿ�
			if (mclass != null && (!ThisClassCloseLog.class.isAssignableFrom(mclass)))
				Log.d(getAppName(context) + "->" + getCallClassName(mStackTraceElement) + "->"
						+ getCallMethodName(mStackTraceElement), text);
		}
	}

	/**
	 * ���Ƶ���ͨtoast
	 * 
	 * @param context
	 * @param text
	 */
	static public void showToast(Context context, String text) {
		if (!CLOSE_ALL_LOG) {
			Toast.makeText(context, text, 0).show();
		}
	}

	/**
	 * ���Ƶ���һ��ʱ����ֻ��һ�ε�toast
	 * 
	 * @param context
	 * @param text
	 */
	static public void showSingleToast(Context context, String text) {
		if (!CLOSE_ALL_LOG) {
			MyToast.showToast(context, text);
		}
	}

	/**
	 * ��һ��ʱ����ֻ��һ�ε�toast
	 * 
	 * @author PanJunLong
	 *
	 */
	private static class MyToast {
		// ���������toast
		private static String oldMsg;
		protected static Toast toast = null;
		private static long oneTime = 0;
		private static long twoTime = 0;

		public static void showToast(Context context, String s) {
			if (toast == null) {
				toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
				toast.show();
				oneTime = System.currentTimeMillis();
			} else {
				twoTime = System.currentTimeMillis();
				if (s.equals(oldMsg)) {
					if (twoTime - oneTime > Toast.LENGTH_SHORT) {
						toast.show();
					}
				} else {
					oldMsg = s;
					toast.setText(s);
					toast.show();
				}
			}
			oneTime = twoTime;
		}

		public static void showToast(Context context, int resId) {
			showToast(context, context.getString(resId));
		}
	}

	/**
	 * ��ȡӦ�ó�������
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [��ȡӦ�ó���汾������Ϣ]
	 * 
	 * @param context
	 * @return ��ǰӦ�õİ汾����
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��õ���MyLog������call��ĵ��÷����ķ�����
	 * 
	 * @param targetStackTrace
	 * @return
	 */
	public static String getCallMethodName(StackTraceElement targetStackTrace) {
		return targetStackTrace.getMethodName();
	}

	/**
	 * ��õ���MyLog������call�������
	 * 
	 * @param targetStackTrace
	 * @return
	 */
	public static String getCallClassName(StackTraceElement targetStackTrace) {
		return targetStackTrace.getClassName();
	}

	/**
	 * ��õ���MyLog������call���StackTraceElement
	 * 
	 * @return
	 */
	private static StackTraceElement getStackTraceElement() {
		StackTraceElement targetStackTrace = null;
		boolean shouldTrace = false;
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		for (StackTraceElement mStackTraceElement : stacks) {
			boolean isLogMethod = mStackTraceElement.getClassName().equals(MyLog.class.getName());
			if (shouldTrace && !isLogMethod) {
				targetStackTrace = mStackTraceElement;
				break;
			}
			shouldTrace = isLogMethod;
		}
		return targetStackTrace;
	}

	/**
	 * ��״̬ģʽ���ر�һ�����log��Ϣ��ĳ����ʵ���˸ýӿڣ��������ӡlog
	 * 
	 * @author PanJunLong
	 *
	 */
	public interface ThisClassCloseLog {
	}

}
