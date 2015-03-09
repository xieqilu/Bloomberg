/**
 * 
Given an array of strings, return all groups of strings that are anagrams.
Note: All inputs will be in lower-case and will be valid English word.

This is a very classic string and HashTable problem. A basic version of this
problem can be found in the book cc150.

Solution 1:
To compare if two strings are anagram or not, we need to build identities for
distinct anagrams. 

In other word, we need to find a way to determine if two strings
belong to a same set of chars. So we can iterate thourgh each string and count the
appearence of each char, if the set of char is ASCII, we can store the appearence in 
an List with size of 256. In the List, value indicates the number of appearence of 
index(char). 

Then we can use a HashMap, key is the identity (above List) and Value is a List of string.
The idea is all strings belong to a specific key will be stored to the corresponding List.
Then we iterate the input string array. For each string, we first get the identity. Then 
check if the identity is already in the HashMap, if it is, we put the string into the corresponding
List. If it is not, we create a new List of string, put the current string into it, and put the
<identity,List<string>> pair into the HashMap.

After the loop above, we iterate through the HashMap. For each entryset<key,value>, if size of List
in value is more than one, we put all strings in the list to the result list. Then return result list.

Time Complexity: O(n*k)
Suppose n is the size of input string array, k is the average size of sting in the array. Then we will
iterate each string in the array, for each string, we need to iterate all chars of it. So overall the 
time complexity is O(n*k).

Space Complexity: O(n*k) the size of HashMap


Solution 2:
Instead of using a List of Integer to identity each string, we can directly sort each string. So that
strings which are anagrams will become a same string after the sorting (Note in this case, all strings
are lowercase). Then we can use the same process as Solution 1 to solve this problem.

In details, we can use HashMap in which key is the sorted string, and value is a List of strings. For each
string in the input array, we first sort them, then check if the sorted string already in the HashMap. If it
is, we add this string to the corresponding list. If it is not, we create a new list of string, put current 
string into it, and then put <sorted string, list> pair into the HashMap.

Time Complexity: O(n*klogk)
Suppose n is the size of input string array, k is the average size of sting in the array. Then it takes O(klogk)
time to sort each string. So the overall time complexity is O(n*klogk).

Space Complexity: O(n*k) the size of HashMap


Special Note:
In java, HashMap.containsKey use key.equals to determine if a key exists in the map. If we use List as the key,
List1.equals(List2) will return true if the two Lists have the same size, content and the order of element. They
don't need to be the same object. In other words, List.equals compare the content of two List objects, not the 
reference of the object. So Solution 1 will work in Java.

But in C#, things are totally different. If we use List as the key in a Dictionary, the ContainsKey method will
compare the reference of object instead of the content of object. So if we use Solution 1 in C#, since for each 
string we create a new List<int> as identity, the Dictionary.ContainsKey method will always return false. No two
Lists will be the same object, even if they have the same content.

So if we want to implement Solution 1 in C#, we need to override the IEquityComparer in the Dictionary constructer 
to specify our own way to determine if a key exists in the Dictionary. This is more comlicated than Java, so in C#,
we only implement Solution 2.
*/

//Solution 1
public class Solution {
    public List<String> anagrams(String[] strs) {
        List<String> anagrams = new ArrayList<String>();
        if(strs.length<2) return anagrams;//handle edge case
        
        //Key:Identity for each string(count appearence of each char of string)
        //Value: List of String the has the identity of its key
        Map<List<Integer>,List<String>> map = new HashMap<List<Integer>,List<String>>();
        for(String s : strs){
            //Initiliaze an ArrayList with size of 256 and all values are 0
            List<Integer> count = new ArrayList<Integer>(Collections.nCopies(256,0));
            for(int i=0;i<s.length();i++){
                count.set(s.charAt(i), count.get(s.charAt(i))+1);
            }
            if(map.containsKey(count))
                map.get(count).add(s);
            else{
                List<String> list = new ArrayList<String>();
                list.add(s);
                map.put(count,list);
            }
        }
        //iterate HashMap using Map.Entry, the best way to do this
        for(Map.Entry<List<Integer>,List<String>> entry : map.entrySet()){
            if(entry.getValue().size()>1){ //if any identity has more than one string
                for(String s : entry.getValue())
                    anagrams.add(s);
            }
        }
        return anagrams;
    }
}


//Solution 2
public class Solution {
    public List<String> anagrams(String[] strs) {
        List<String> anagrams = new ArrayList<String>();
        if(strs.length < 2) return anagrams;
        Map<String, List<String>> map = new HashMap<String,List<String>>();
        for(String s : strs){
            //convert s to char array, then sort it, then convert it back to a String
            char[] sortedChar = s.toCharArray();
            Arrays.sort(sortedChar); // Arrays.sort() is a static void method
            //Do not use char[].toString(), will cause false result
            //Can use String sortedStr = Arrays.toString(sortedChar) or below line of code
            String sortedStr = new String(sortedChar);
            if(map.containsKey(sortedStr))
                map.get(sortedStr).add(s);
            else{
                List<String> list = new ArrayList<String>();
                list.add(s);
                map.put(sortedStr, list);
            }
        }
        
        for(Map.Entry<String, List<String>> entryset : map.entrySet()){
            if(entryset.getValue().size()>1){
                for(String s : entryset.getValue())
                    anagrams.add(s);
            }
        }
        return anagrams;
    }
}
