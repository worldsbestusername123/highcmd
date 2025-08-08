package net.mcreator.highcmdforge;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface DLL extends Library {

    DLL INSTANCE = Native.load("HighCMDLib", DLL.class);

    Pointer findBoolMemoryHeader(boolean value);
    Pointer findIntMemoryHeader(int value);
    Pointer findDoubleMemoryHeader(double value);
}
