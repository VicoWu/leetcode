package leetcode;

public class SomeTest {
    public static void main(String[] args) {
        byte a = -128;
        byte b = -127;
        byte c = 1;
        byte d = 2;
        compareUnsignedBytes(a,b);
        compareUnsignedBytes(b,c);
        compareUnsignedBytes(c,d);
    }

    private static int compareUnsignedBytes(byte thisByte, byte thatByte)
    {
        return unsignedByteToInt(thisByte) - unsignedByteToInt(thatByte);
    }
    private static int unsignedByteToInt(byte thisByte)
    {
        return thisByte & 0xFF;
    }

}
