package com.abbott.mutiimgloader.qq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;

import com.abbott.mutiimgloader.circularavatar.JoinBitmaps;

import java.util.Arrays;

/**
 * 
 */
public class GroupFaceUtil {

	/** 图片之间的距离 */
	private static int PADDING = 2;
	/** 圆角值 */
	private static final int ROUNDPX = 0;

	/** 头像模式 圆的 */
	public static final int FACETYPE_QQ = 1;
	/** 头像模式 方的 最多9个 */
	public static final int FACETYPE_WS = 2;

	public static Bitmap createGroupFace(int type, Context context,
			Bitmap[] bitmapArray) {
		if (type == FACETYPE_QQ) {
			return createGroupBitCircle(bitmapArray, context);
		}
		return createTogetherBit(bitmapArray, context);
	}

	private static Bitmap scaleBitmap(float paramFloat, Bitmap paramBitmap) {
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(paramFloat, paramFloat);
		return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight(), localMatrix, true);
	}

	/**
	 * 拼接群头像 圆形版的
	 * 
	 * @param bitmapArray
	 * @param context
	 * @return
	 */
	private static Bitmap createGroupBitCircle(Bitmap[] bitmapArray,
			Context context) {
		if (bitmapArray.length < 1 && bitmapArray.length > 9) {
			return null;
		}
		// 先取一个获取宽和高
		Bitmap tempBitmap = (Bitmap) bitmapArray[0];
		if (tempBitmap == null) {
			return null;
		}
		// 画布的宽
		int tempWidth = tempBitmap.getWidth();
		// 画布的高
		int tempHeight = tempBitmap.getHeight();
		Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight,
				Config.ARGB_8888);
		Canvas localCanvas = new Canvas(canvasBitmap);
		localCanvas.drawColor(Color.GRAY);
		JoinBitmaps.join(localCanvas, Math.min(tempWidth, tempHeight),
				Arrays.asList(bitmapArray));
		return canvasBitmap;
	}

	/**
	 * 拼接群头像
	 * 
	 * @param paramList
	 *            群id
	 * @param context
	 * @return 头像本地路径
	 */
	@SuppressWarnings("unused")
	private static Bitmap createTogetherBit(Bitmap[] paramList,
			final Context context) {
		if (paramList.length < 1 && paramList.length > 9) {
			return null;
		}
		// 先取一个获取宽和高
		Bitmap tempBitmap = (Bitmap) paramList[0];
		if (tempBitmap == null) {
			return null;
		}
		// 画布的宽
		int tempWidth = tempBitmap.getWidth();
		// 画布的高
		int tempHeight = tempBitmap.getHeight();
		// 创建一个空格的bitmap
		Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight,
				Config.ARGB_8888);
		// 头像的数量
		int bitmapCount = paramList.length;
		Canvas localCanvas = new Canvas(canvasBitmap);
		localCanvas.drawColor(Color.GRAY);
		int colum = 0;
		if (bitmapCount > 0 && bitmapCount < 5) {
			colum = 2;
		} else if (bitmapCount > 4 && bitmapCount < 10) {
			colum = 3;
		}
		float scale = 1.0F / colum;
		// 根据列数缩小
		Bitmap scaledBitmap = scaleBitmap(scale, tempBitmap);
		if (PADDING > 0) {
			PADDING = dip2px(context, PADDING);
			// 如果有内边距 再次缩小
			float paddingScale = (float) (tempWidth - (colum + 1) * PADDING)
					/ colum / scaledBitmap.getWidth();
			scaledBitmap = scaleBitmap(paddingScale, scaledBitmap);
			scale = scale * paddingScale;
		}
		// 第一行的 头像个数
		int topRowCount = bitmapCount % colum;
		// 满行的行数
		int rowCount = bitmapCount / colum;
		if (topRowCount > 0) {
			// 如果第一行头像个数大于零 行数加1
			rowCount++;
		} else if (topRowCount == 0) {
			// 6 或者 9 第一行头像个数和列数一致
			topRowCount = colum;
		}
		// 缩小后头像的宽
		int scaledWidth = scaledBitmap.getWidth();
		// 缩小后头像的高
		int scaledHeight = scaledBitmap.getHeight();
		// 第一个头像与画布顶部的距离
		int firstTop = ((tempHeight - (rowCount * scaledHeight + (rowCount + 1)
				* PADDING)) / 2)
				+ PADDING;
		// 第一个头像与画布左部的距离
		int firstLeft = ((tempWidth - (topRowCount * scaledWidth + (topRowCount + 1)
				* PADDING)) / 2)
				+ PADDING;
		for (int i = 0; i < paramList.length; i++) {
			if (i == 9) {// 达到上限 停止
				break;
			}
			// 按照最终压缩比例压缩
			Bitmap bit = scaleBitmap(scale, (Bitmap) paramList[i]);
			if (ROUNDPX > 0) {
				// 圆角化
				bit = GetRoundedCornerBitmap(bit);
			}
			localCanvas.drawBitmap(bit, firstLeft, firstTop, null);
			firstLeft += (scaledWidth + PADDING);
			if (i == topRowCount - 1 | tempWidth - firstLeft < scaledWidth) {
				firstTop += (scaledHeight + PADDING);
				firstLeft = PADDING;
			}
			bit.recycle();
		}
		// 重置padding
		PADDING = 2;
		localCanvas.save(Canvas.ALL_SAVE_FLAG);
		localCanvas.restore();
		return canvasBitmap;
	}

	/**
	 * 圆角
	 *
	 * @param bitmap
	 * @return
	 */
	private static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight()));
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);
			canvas.drawRoundRect(rectF, ROUNDPX, ROUNDPX, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

			final Rect src = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}

	private static int dip2px(Context context, float value) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, context.getResources().getDisplayMetrics()) + 0.5f);
	}
}
