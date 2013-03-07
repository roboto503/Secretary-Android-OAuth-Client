package com.secretary.activities;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.widget.ListView;

public class ToDos extends ListActivity {

	//UI items
	private ListView list;
	
	private String [] testRows = {"one","two","three","four"};
	
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
	
		list = (ListView) findViewById(R.id.list);
		
	}//initializeUI
	
	private void updateList(){}//updateList

}
