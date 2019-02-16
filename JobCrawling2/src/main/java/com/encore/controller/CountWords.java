package com.encore.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.encore.model.CorpDAO;

public class CountWords {

	public static void main(String[] args) {
		//List<Entry<String, Integer>> entryList1 = getList("jobInfo",  100);
		List<Entry<String, Integer>> entryList2 = getList("jobKeyword", 10000);
	}

	public static List<Entry<String, Integer>> getList(String column, int size) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		CorpDAO dao = new CorpDAO();
		List<String> list = dao.selectByColumn(column);
		//String sep = "\\s*\\:\\s*|\\s*\\/\\s*|\\s+\\-\\s+|\\s+&\\s+|\\.|\\s+";
		String sep = "\\s*\\:\\s*|\\s*\\/\\s*|\\s+\\-\\s+|\\s+&\\s+|\\.\\s+|\\s+";

		for (String s : list) {
			//System.out.println(s);
			String[] words = s.trim().split(sep);
			System.out.println(Arrays.toString(words));
			for (String word : words) {
				word = word.replaceAll("-$|^-", "");
				//if (word =="") continue; 
				System.out.print(""+word+",");
				if (map.containsKey(word)) {
					Integer oldCount = map.get(word);
					map.put(word, oldCount + 1);
				} else {
					map.put(word, 1);
				}
			}
			System.out.println();
		}
		/* sort */
		HashMap<String, Integer> sortedMap = map.entrySet().stream()
				.sorted(Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		for (String key : sortedMap.keySet())
			System.out.println(key + " : " + sortedMap.get(key));
		System.out.println(map.size());

		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(sortedMap.entrySet()).
				subList(0, Math.min(size, sortedMap.size()));
		for (Entry<String, Integer> s : entryList) {
			//System.out.println(s);
		}
		return entryList;
	}
}