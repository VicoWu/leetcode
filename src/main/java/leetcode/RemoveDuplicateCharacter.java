package leetcode;

public class RemoveDuplicateCharacter {
    public String removeDuplicateLetters(String s) {
        int[] occr = new int[26];
        boolean[] inStack = new boolean[26];
        for(int i = 0;i<s.length();i++){
            occr[s.charAt(i) - 'a'] = occr[s.charAt(i) - 'a'] + 1;
        }
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<s.length();i++){
            char c = s.charAt(i);
            occr[c - 'a'] = occr[c - 'a'] - 1;
            if(sb.length() == 0 || (c > sb.charAt(sb.length() - 1) && !inStack[c - 'a'])){ // 栈为空或者这个元素大与栈顶元素，直接入栈
                sb.append(c);
                inStack[c - 'a'] = true;
            }else if(!inStack[c - 'a']){ // 如果已经在栈中，不做任何处理
                while(sb.length() > 0
                        && sb.charAt(sb.length() - 1) > c
                        && occr[sb.charAt(sb.length() - 1) - 'a'] > 0){ // 这个退栈元素在后面还会出现，那么就退栈
                    inStack[sb.charAt(sb.length() - 1) - 'a'] = false;
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(c);
                inStack[c - 'a'] = true;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new RemoveDuplicateCharacter().removeDuplicateLetters("cbacdcbc");
    }
}
