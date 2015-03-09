//Roman to Integer
public class Solution {
    int[] table;
    public Solution() {
        table = new int[256];
        table['I'] = 1; table['V'] = 5; 
        table['X'] = 10; table['L'] = 50; 
        table['C'] = 100; table['D'] = 500; 
        table['M'] = 1000;
    }
    public int RomanToInt(string s) {
        int prev = 0;
        int total = 0;
        for (int i = 0; i < s.Length; i++) {
            int curr = table[s[i]];
            if (curr > prev) {
                total += curr - 2*prev;
            } else {
                total += curr;
            }
            prev = curr;
        }
        return total;
    }
}


//Integer to Roman
public class Solution {
    public string IntToRoman(int num) {
        string[][] roman = new string[][]{
            new string[]{"I", "V"},
            new string[]{"X", "L"},
            new string[]{"C", "D"},
            new string[]{"M"}
        };
        int digits = 0;
        string r = "";
        while (num > 0) {
            int dig = num%10;
            if (dig == 0) {
            } else if (dig <= 3) {
                for (int i = 0; i < dig; i++)
                    r = roman[digits][0] + r;
            } else if (dig == 4) {
                r = roman[digits][0] + roman[digits][1] + r;
            } else if (dig <= 8) {
                string temp = roman[digits][1];
                for (int i = 5; i < dig; i++)
                    temp += roman[digits][0];
                r = temp + r;
            } else if (dig == 9) {
                r = roman[digits][0] + roman[digits+1][0] + r;
            }
            digits++;
            num /= 10;
        }
        return r;
    }
}
