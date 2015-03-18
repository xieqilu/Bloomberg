/**
 * Solution:
 * use a hashMap<element, index> to store elements in the given arrays
 * for each int i in the given array,
 * first check if hashmap contains (target-i)
 * if it does, then return the index of i and (target - i)
 * it it doesn't, add <index, i> to the hashMap
 * 
 * Time:O(n), Space:O(n)
 * 
 * Note we must use element as key and index as value, because
 * we need to find if hashMap contains key fast.
 * */

public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();
        int[] result = new int[2];
        for(int i=0;i<numbers.length;i++){
            if(map.containsKey(target-numbers[i])){
                result[0] = map.get(target-numbers[i])+1;
                result[1] = i+1;
                break;
            }
            else  //update index of numbers[i] to i, if there is duplicate, update to last index
                map.put(numbers[i], i);
            //if we are required to find first index of numbers[i] when there are duplicate, use below code
            //don't use above code
            //else if(!map.containsKey(numbers[i]))
                //map.put(numbers[i],i);
        }
        return result;
    }
}
