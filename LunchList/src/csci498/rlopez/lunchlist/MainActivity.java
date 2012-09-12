

package csci498.rlopez.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.TabActivity;
import android.graphics.Color;
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

public class MainActivity extends TabActivity {
	List<Restaurant> model = new ArrayList<Restaurant>();
	RestaurantAdapter adapter;
	EditText name;
	EditText address;
	EditText notes;
	Restaurant current;
	RadioGroup types;
	int progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.name);
        address = (EditText)findViewById(R.id.addr);
        notes = (EditText)findViewById(R.id.notes);
        types = (RadioGroup)findViewById(R.id.types);
        
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurants);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(onListClick);
        
        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.restaurants);
        spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);
        
        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
        
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	return(super.onCreateOptionsMenu(menu));
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == R.id.toast) {
    		String message="No restaurant selected";
    		
    		if (current != null) {
    			message = current.getNotes();
    		}
    		
    		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    		
    		return(true);
    		
    	} else if (item.getItemId() == R.id.run){
    		setProgressBarVisibility(true);
    		progress = 0;
    		
    		new Thread(longTask).start();
    		
    		return(true);
    	}
    	
    	return(super.onOptionsItemSelected(item));
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			current = new Restaurant();
			current.setName(name.getText().toString());
			current.setAddress(address.getText().toString());
			current.setNotes(notes.getText().toString());
			
			setDeliveryType(types.getCheckedRadioButtonId(), current);
			adapter.add(current);
		}
	};
	
	private void addTypeButtons(RadioGroup types) {
		RadioButton sit_down = new RadioButton(this);
		RadioButton take_out = new RadioButton(this);
		RadioButton delivery = new RadioButton(this);
		
		sit_down.setText("Sit-Down");
		sit_down.setId(R.id.sit_down);
		take_out.setText("Take-Out");
		take_out.setId(R.id.take_out);
		delivery.setText("Delivery");
		delivery.setId(R.id.delivery);
		
		types.addView(sit_down);
		types.addView(take_out);
		types.addView(delivery);
	}
	
	private void setDeliveryType(int type, Restaurant r) {
		switch (type) {
		case R.id.sit_down:
			r.setType("sit_down");
			break;
		
		case R.id.take_out:
			r.setType("take_out");
			break;
			
		case R.id.delivery:
			r.setType("delivery");
			break;
		}
	}
	
    class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    	RestaurantAdapter() {
    		super(MainActivity.this, android.R.layout.simple_list_item_1, model);
    	}
    	
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View row = convertView;
    		RestaurantHolder holder = null;
    		
    		if (row == null) {
    			LayoutInflater inflater = getLayoutInflater();
    			row = inflater.inflate(R.layout.row, null);
    			holder = new RestaurantHolder(row);
    			row.setTag(holder);
    		} else {
    			holder = (RestaurantHolder)row.getTag();
    		}
    		
    		holder.populateFrom(model.get(position));
    		
    		return(row);
    	}
    }
    
    static class RestaurantHolder {
    	private TextView name = null;
    	private TextView address = null;
    	private ImageView icon = null;
    	
    	RestaurantHolder(View row) {
    		name = (TextView)row.findViewById(R.id.title);
    		address = (TextView)row.findViewById(R.id.address);
    		icon = (ImageView)row.findViewById(R.id.icon);
    	}
    	
    	void populateFrom(Restaurant r) {
    		name.setText(r.getName());
    		address.setText(r.getAddress());
    		
    		if (r.getType().equals("sit_down")) {
    			icon.setImageResource(R.drawable.ball_red);
    			name.setTextColor(Color.RED);
    			address.setTextColor(Color.RED);
    		} else if (r.getType().equals("take_out")) {
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
    		current = model.get(position);
    		
    		name.setText(current.getName());
    		address.setText(current.getAddress());
    		notes.setText(current.getNotes());
    		
    		if (current.getType().equals("sit_down")) {
    			types.check(R.id.sit_down);
    		} else if (current.getType().equals("take_out")) {
    			types.check(R.id.take_out);
    		} else {
    			types.check(R.id.delivery);
    		}
    		
    		getTabHost().setCurrentTab(1);
    	}
	};
	
	private void doSomeLongWork(final int incr) {
		runOnUiThread(new Runnable() {
			public void run() {
				progress+=incr;
				setProgress(progress);
			}
		});
		
		SystemClock.sleep(250);
	}
	
	private Runnable longTask = new Runnable() {
		public void run() {
			for (int i = 0; i < 20; i++) {
				doSomeLongWork(500);
			}
			
			runOnUiThread(new Runnable() {
				public void run() {
					setProgressBarVisibility(false);
				}
			});
		}
	};
}