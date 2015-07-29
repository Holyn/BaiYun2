package com.baiyun2.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

public class GBlurPic {

	private Bitmap mBitmap;

	private RenderScript mRS;
	private Allocation mInAllocation;
	private Allocation mOutAllocation;
	private ScriptIntrinsicBlur mBlur;

	public GBlurPic(Context context) {
		super();
		this.mRS = RenderScript.create(context);
		this.mBlur = ScriptIntrinsicBlur.create(mRS, Element.U8_4(mRS));
	}

	/**
	 * 错误信息如下：
	 * android.support.v8.renderscript.RSRuntimeException: Error loading RS jni library: 
	 * java.lang.UnsatisfiedLinkError: Couldn't load RSSupport from loader dalvik.system.PathClassLoader
	 * 导入官方jar   renderscript-v8.jar  报这个错误 android.support.v8.renderscript.RSRuntimeException: Error loadin 
	 * 或者 java.lang.UnsatisfiedLinkError: Couldn't load RSSupport from loader dalvik.system.PathClassLoader
	 * 这个错误原因是因为在4.4以上的手机上自带 librsjni.so和libRSSupport.so 而在4.0以下,或者某些奇葩手机是没有这两个jni 的.
	 * 所以我们得把这两个jni 导入到我们的工程下  这两个jni文件 在 android sdk  路径下  androidsdk\sdk\build-tools\22.0.0\renderscript\lib\packaged
	 * 然后把 armeabi-v7a x86 复制到工程的libs下就OK了
	 * 
	 */
	
	public Bitmap gBlurBitmap(Bitmap bitmap, float radius) {
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
		mBitmap = bitmap.copy(bitmap.getConfig(), true);

		mInAllocation = null;
		mOutAllocation = null;
		mInAllocation = Allocation.createFromBitmap(mRS, mBitmap,
				Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
		mOutAllocation = Allocation.createTyped(mRS, mInAllocation.getType());

		mBlur.setRadius(radius);
		mBlur.setInput(mInAllocation);
		mBlur.forEach(mOutAllocation);

		mOutAllocation.copyTo(mBitmap);

		return mBitmap;
	}

}
