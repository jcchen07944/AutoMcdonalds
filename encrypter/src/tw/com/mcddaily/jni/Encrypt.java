package tw.com.mcddaily.jni;

public class Encrypt {

	public static native int checkSignature(Object obj);

	public static native String encode(Object obj, String str);

	public native String HelloLoad();

	static {
		System.loadLibrary("encrypt");
	}
}
