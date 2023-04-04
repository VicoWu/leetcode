package leetcode; /**
 * Problem: Find length of longest substring without repeating characters
 * Question 3
 * Solution: Use two pointers to bound the substring and use boolean array to mark characters used in the substring
 */

import java.util.HashMap;
import java.util.Map;

public class LongestSubstring {
	public int lengthOfLongestSubstring(String s){
		if(s==null || s.length()==0){
			return 0;
		}
		int start = 0;
		int end = 0;
		int len = s.length();
		int sublen = 0;
		//mark characters used in the substring
		boolean[] usage = new boolean[256];
		while(start<len && end<len){
			//character not used: mark used, move forward
			if(!usage[s.charAt(end)]){
				usage[s.charAt(end)] = true;
				end++;
			}
			//charcter used: unmark usage[start] and move start forward
			else{
				usage[s.charAt(start)] = false;
				start++;
			}
			sublen = Math.max(sublen, end-start);
		}
		return sublen;
	}

	public int lengthOfLongestSubstring2(String s) {
		Map<Character, Integer> loc = new HashMap<Character, Integer>();
		int maxLength = 0;
		int currentLength = 0;
		int curr = 0;
		int start = 0;
		while(curr < s.length()){
			// 如果这个字符在map中存在，并且index是在start之后，说明遇到了重复字符
			if(loc.containsKey(s.charAt(curr)) && loc.get(s.charAt(curr)) >= start){
				start = loc.get(s.charAt(curr)) + 1; // start更新为该重复字符在前面出现的位置
			}
			// 更新maxlength的值
			maxLength = Math.max(maxLength, curr - start + 1);
			// 更新这个char 的最新位置
			loc.put(s.charAt(curr), curr);
			curr++;
		}
		return maxLength;
	}

	public static void main(String[] args) {
		new LongestSubstring().lengthOfLongestSubstring2("pwwkew");
	}
}