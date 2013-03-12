package com.secretary.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ToDos extends ListActivity {

	//UI items
	private ListView listView;
	
	private String [] valuesTasks = {"Siivous","Kokkaus","Läksyt","Verot"};
	private String [] valuesDates = {"2013-3-13","2013-4-2","2013-1-14","2013-5-23"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todos_list);
		
		initializeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_dos, menu);
		return true;
	}
	
	
	/**
	* Initializes the UI elements 
	*/
	private void initializeUI(){
	
		listView = (ListView) findViewById(R.id.list);
		
	}//initializeUI
	
	private void updateList(){
		ArrayList<Map<String, String>> list = createRows();
	

		//helps assigning right data to right place on row layout
		String[] from = {"task","dates"}; //key-value pairs from array list map
		int[] to = { R.id.task, R.id.deadline};
		
		//creates a new data adapter and sets that to the listview
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.todos_row, from, to);
		listView.setAdapter(simpleAdapter);
		
		//add context menu for listview
		registerForContextMenu(listView);
		
	}//updateList
	
	/** creates the rows needed for list view*/
	//private ArrayList<Map<String, String>> createRows(List<String> listOfValues) { //values
	private ArrayList<Map<String, String>> createRows() {	
		ArrayList<Map<String, String>> valuesList = new ArrayList<Map<String, String>>();
		
		// loops through given list of locations, and strips location's values (longitude, latitude and date) to the right places in row item using putData method 
		for(int i = 0; i <= valuesTasks.length; i++){
			valuesList.add(putData(valuesTasks[i], valuesDates[i]));
		}//for

	    return valuesList;
	}//createRows

	/** helper method for createRows*/
	private Map<String, String> putData(String task, String date) {
		HashMap<String, String> item = new HashMap<String, String>();
	    item.put("task", task);
	    item.put("dates", date);
	    return item;
	}//puData

}
