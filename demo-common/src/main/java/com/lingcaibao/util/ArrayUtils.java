package com.lingcaibao.util;

public class ArrayUtils
{
	/**
	 * 二分查找法
	 * @param array
	 * @param from
	 * @param to
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static <E extends Comparable<E>> int binarySearch(E[] array, int from, int to, E key) throws Exception
	{
		if (from < 0 || to < 0)
		{
			throw new IllegalArgumentException("params from & length must larger than 0 .");
		}
		if (from <= to)
		{
			int middle = (from >>> 1) + (to >>> 1); // 右移即除2
			E temp = array[middle];
			if (temp.compareTo(key) > 0)
			{
				to = middle - 1;
			} else if (temp.compareTo(key) < 0)
			{
				from = middle + 1;
			} else
			{
				return middle;
			}
		}
		return binarySearch(array, from, to, key);
	}

	/**
	 * 二分查找法(二维数组)
	 * @param array
	 * @param from
	 * @param to
	 * @param key
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static <E extends Comparable<E>> int binarySearch(E[][] array, int from, int to, E key, int index) throws Exception
	{
		if (from < 0 || to < 0)
		{
			throw new IllegalArgumentException("params from & length must larger than 0 .");
		}
		if (from <= to)
		{
			int middle = (from >>> 1) + (to >>> 1); // 右移即除2
			E[] temp = array[middle];
			if (temp[index].compareTo(key) > 0)
			{
				to = middle - 1;
			} else if (temp[index].compareTo(key) < 0)
			{
				from = middle + 1;
			} else
			{
				return middle;
			}
		}
		return binarySearch(array, from, to, key, index);
	}
	
	
}
