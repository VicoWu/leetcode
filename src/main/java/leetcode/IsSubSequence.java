package leetcode;

public class IsSubSequence {
    public boolean isSubsequence(String s, String t) {
        int k = 0;
        for(int i = 0;i < t.length();i++){
            char cur = t.charAt(i);
            while(k < s.length() && s.charAt(k) != cur){
                k++;
            }
            if(k == s.length()){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new IsSubSequence().isSubsequence("")
    }
}
