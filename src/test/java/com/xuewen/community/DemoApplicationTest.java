package com.xuewen.community;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class DemoApplicationTest {
    public static void main(String[] args) {
        int[] i = {2,1,3,4,4,3,2,5,6,7,8};
        int r=test(i,3);
        System.out.println(r);
    }
    public static int test(int[] arr,int data){
        int[] SortArr=Sort(arr);
        int high = SortArr.length;
        int low = 0;
        if (SortArr==null||SortArr.length==0){
            return -1;
        }
        while(low<=high){
            int mid=(high+low)/2;
            if (SortArr[mid]>data){
                high = mid+1;
            }else if (SortArr[mid]<data){
                low = mid-1;
            }else{
                return mid;
            }
        }
        return 0;
    }
    //对数组去重、排序
    public static int[] Sort(int[] arr){
        HashSet<Integer> hashSet= new HashSet<Integer>();
        for (int i=0;i<arr.length;i++){
            hashSet.add(arr[i]);
        }
        Set<Integer> set = new TreeSet<Integer>(hashSet);
        Integer[] integers = set.toArray(new Integer[]{});
        int[] result = new int[integers.length];
        for (int j=0;j<integers.length;j++){
            result[j] = integers[j].intValue();
        }
        return  result;
    }
}