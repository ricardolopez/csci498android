

package csci498.rlopez.lunchlist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.app.ListActivity;
import android.app.TabActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.content.Context;
import android.content.Intent;
//import android.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ViewFlipper;

public class MainActivity extends ListActivity {
	
	Cursor model;
	RestaurantAdapter adapter;
	EditText name;
	EditText address;
	EditText notes;
	RadioGroup types;
	RestaurantHelper helper;
	public final static String ID_EXTRA = "csci498.rlopez.lunchlist._ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new RestaurantHelper(this);
        model = helper.getAll();
        startManagingCursor(model);
        adapter = new RestaurantAdapter(model);
        setListAdapter(adapter);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.add) {
    		startActivity(new Intent(MainActivity.this, DetailForm.class));
    		
    		return(true);
    	}
    	
    	return(super.onOptionsItemSelected(item));
    }
    
    class RestaurantAdapter extends CursorAdapter {
		RestaurantAdapter(Cursor c) {
    		super(MainActivity.this, c);
    	}
    	
    	@Override
    	public void bindView(View row, Context ctxt, Cursor c) {
    		RestaurantHolder holder = (RestaurantHolder)row.getTag();
    		
    		holder.populateFrom(c, helper);
    	}
    	
    	@Override
    	public View newView(Context ctxt, Cursor c, ViewGroup parent) {
    		LayoutInflater inflater = getLayoutInflater();
    		View row = inflater.inflate(R.layout.row, parent, false);
    		RestaurantHolder holder = new RestaurantHolder(row);
    		
    		row.setTag(holder);
    		
    		return(row);
    	}
    }
    
    static class RestaurantHolder {
    	private TextView name;
    	private TextView address;
    	private ImageView icon;
    	
    	RestaurantHolder(View row) {
    		name = (TextView)row.findViewById(R.id.title);
    		address = (TextView)row.findViewById(R.id.address);
    		icon = (ImageView)row.findViewById(R.id.icon);
    	}
    	
    	void populateFrom(Cursor c, RestaurantHelper helper) {
    		name.setText(helper.getName(c));
    		address.setText(helper.getAddress(c));
    		
    		if (helper.getType(c).equals("sit_down")) {
    			icon.setImageResource(R.drawable.ball_red);
    			name.setTextColor(Color.RED);
    			address.setTextColor(Color.RED);
    		} else if (helper.getType(c).equals("take_out")) {
    			icon.setImageResource(R.drawable.ball_yellow);
    			name.setTextColor(Color.YELLOW);
    			address.setTextColor(Color.YELLOW);
    		} else {
    			icon.setImageResource(R.drawable.ball_green);
    			name.setTextColor(Color.GREEN);
    			address.setTextColor(Color.GREEN);
    		}
    	}
    }
    
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		
    		Intent i = new Intent(MainActivity.this, DetailForm.class);
    		i.putExtra(ID_EXTRA, String.valueOf(id));
    		
    		startActivity(i);
    	}
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		helper.close();
	}
}