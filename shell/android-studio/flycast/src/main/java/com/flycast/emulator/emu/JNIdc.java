package com.flycast.emulator.emu;

import android.view.Surface;

import com.flycast.emulator.Emulator;
import com.flycast.emulator.periph.SipEmulator;

public final class JNIdc
{
	static { System.loadLibrary("flycast"); }

	public static native String initEnvironment(Emulator emulator, String filesDirectory, String homeDirectory, String locale);
	public static native void setExternalStorageDirectories(Object[] pathList);
	public static native void setGameUri(String fileName);
	public static native void pause();
	public static native void resume();
	public static native void stop();
	public static native void disableOmpAffinity();

	public static native void rendinitNative(Surface surface, int w, int h);

	public static native void setupMic(SipEmulator sip);

	public static native void screenCharacteristics(float screenDpi, float refreshRate);
	public static native void guiOpenSettings();
	public static native boolean guiIsOpen();
	public static native boolean guiIsContentBrowser();
	public static native void guiSetInsets(int left, int right, int top, int bottom);
	public static native void postRestore(String filesDir);

	// MainUI State API (io.github.mainui.stateapi.FlycastStates)
	public static native boolean mainuiRunning();
	public static native boolean mainuiReady();
	/** Saves (or loads) state [index]: what went wrong, or "". */
	public static native String mainuiState(boolean save, int index);
	/** Stops (or starts again) the game, the menu closed: false if it wasn't running (stopped). */
	public static native boolean mainuiPause(boolean pause);
	/** When state [index] was saved, ms since 1970 (0: none). */
	public static native long mainuiStateTime(int index);
	public static native String mainuiStatePath(int index);
}
