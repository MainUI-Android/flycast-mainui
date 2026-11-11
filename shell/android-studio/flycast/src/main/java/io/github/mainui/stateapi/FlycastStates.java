package io.github.mainui.stateapi;

import com.flycast.emulator.emu.JNIdc;

/**
 * Flycast's side of the MainUI State API ({@link MainUIStateApi}). MainUI's slots 0-9 are
 * Flycast's own (the menu's 10 slots); MainUI's resume state is slot 99 ("<game>_99.state"),
 * which the menu doesn't reach. States are saved and loaded the way the menu does: emulation
 * stopped meanwhile, a screenshot with each save.
 */
public final class FlycastStates implements MainUIStateApi.Emulator {
    public static final FlycastStates INSTANCE = new FlycastStates();

    private static final int SLOTS = 10;
    private static final int RESUME = 99;

    private FlycastStates() {}

    private static int index(int slot) { return slot == MainUIStateApi.RESUME_SLOT ? RESUME : slot; }

    @Override public boolean isRunning() { return JNIdc.mainuiRunning(); }

    @Override public boolean isReady() { return JNIdc.mainuiReady(); }

    @Override public int slotCount() { return SLOTS; }

    @Override
    public String save(int slot) throws Exception {
        int index = index(slot);
        long start = System.currentTimeMillis() / 1000 * 1000; // (the state's date is in whole seconds)
        String error = JNIdc.mainuiState(true, index);
        if (!error.isEmpty()) throw new IllegalStateException(error);
        // (Flycast tells of a failed write only on screen: the state's date says it was written.)
        if (JNIdc.mainuiStateTime(index) < start) throw new IllegalStateException("Flycast couldn't write the state");
        return JNIdc.mainuiStatePath(index);
    }

    @Override
    public void load(int slot) throws Exception {
        String error = JNIdc.mainuiState(false, index(slot));
        if (!error.isEmpty()) throw new IllegalStateException(error);
    }

    @Override public long slotTime(int slot) { return JNIdc.mainuiStateTime(index(slot)); }
}
