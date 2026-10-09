package iticbcn.xifratge;

public class TextXifrat {
    private byte[] byteArray;
    
    public byte[] getBytes() {
        return byteArray;
    }

    public TextXifrat(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    @Override 
    public String toString() {
        return String.format("%s", new String(byteArray));
    }
}
