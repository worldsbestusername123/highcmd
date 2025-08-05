package net.mcreator.highcmdforge;
import com.sun.jna.Pointer;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Unsafestuff {

    private static final Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access Unsafe", e);
        }
    }

    public static boolean allreturn(boolean value) {
        Pointer ptr = DLL.INSTANCE.findBoolMemoryHeader(value);
        long address = Pointer.nativeValue(ptr);
        byte raw = unsafe.getByte(address);
        return raw != 0;
    }

}

