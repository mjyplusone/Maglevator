package com.example.version1;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

/**
 * 倒计时Button帮助�?
 * 
 * @author zhaokaiqiang
 * @see http://blog.csdn.net/zhaokaiqiang1992
 */
public class CountDownButtonHelper {

	// 倒计时timer
	private CountDownTimer countDownTimer;
	// 计时结束的回调接�?
	private OnFinishListener listener;

	private Button button;

	/**
	 * 
	 * @param button
	 *            �?要显示�?�计时的Button
	 * @param defaultString
	 *            默认显示的字符串
	 * @param max
	 *            �?要进行�?�计时的�?大�??,单位是秒
	 * @param interval
	 *            倒计时的间隔，单位是�?
	 */
	public CountDownButtonHelper(final Button button,
			final String defaultString, int max, int interval) {

		this.button = button;
		// 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致�?后一秒不会调用onTick()
		// 因此，设置间隔的时�?�，默认减去�?10ms，从而减去误差�??
		// 经过以上的微调，�?后一秒的显示时间会由�?10ms延迟的积累，导致显示时间�?1s长max*10ms的时间，其他时间的显示正�?,总时间正�?
		countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {

			@Override
			public void onTick(long time) {
				// 第一次调用会�?1-10ms的误差，因此�?�?+15ms，防止第�?个数不显示，第二个数显示2s
				button.setText(defaultString + "(" + ((time + 15) / 1000)
						+ "�?)");
				Log.d("CountDownButtonHelper", "time = " + (time) + " text = "
						+ ((time + 15) / 1000));
			}

			@Override
			public void onFinish() {
				button.setEnabled(true);
				button.setText(defaultString);
				if (listener != null) {
					listener.finish();
				}
			}
		};
	}

	/**
	 * �?始�?�计�?
	 */
	public void start() {
		button.setEnabled(false);
		countDownTimer.start();
	}

	/**
	 * 设置倒计时结束的监听�?
	 * 
	 * @param listener
	 */
	public void setOnFinishListener(OnFinishListener listener) {
		this.listener = listener;
	}

	/**
	 * 计时结束的回调接�?
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	public interface OnFinishListener {
		public void finish();
	}

}
