

package csci498.rlopez.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
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
	RestaurantAdapter adapter = null;
	EditText name = null;
	EditText address = null;
	//EditText date = null;
	EditText notes = null;
	RadioGroup types = null;
	//ViewFlipper flip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.name);
        address = (EditText)findViewById(R.id.addr);
        //date = (EditText)findViewById(R.id.date);
        notes = (EditText)findViewById(R.id.notes);
        types = (RadioGroup)findViewById(R.id.types);
        
        //RadioGroup types = (RadioGroup)findViewById(R.id.types);
        //addTypeButtons(types);
        
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(onSave);
        
        //Button button = (Button)findViewById(R.id.flip);
        //flip = (ViewFlipper)findViewById(R.id.view_flipper);
        
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
        
        //AutoCompleteTextView autoComplete = (AutoCompleteTextView)findViewById(R.id.addr);
    	//autoComplete.setAdapter(adapter);
    }
    
    //public void ClickHandler(View v) {
    //	flip.showNext();
    //}
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText)findViewById(R.id.name);
			EditText address = (EditText)findViewById(R.id.addr);
			EditText notes = (EditText)findViewById(R.id.notes);
			//EditText date = (EditText)findViewById(R.id.date);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			r.setNotes(notes.getText().toString());
			//r.setDate(date.getText().toString());
			
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			setDeliveryType(types.getCheckedRadioButtonId(), r);
			adapter.add(r);
		}
	};
	
	private void addTypeButtons(RadioGroup types) {
		RadioButton sit_down = new RadioButton(this);
		RadioButton take_out = new RadioButton(this);
		RadioButton delivery = new RadioButton(this);
		//RadioButton one = new RadioButton(this);
		//RadioButton two = new RadioButton(this);
		//RadioButton three = new RadioButton(this);
		//RadioButton four = new RadioButton(this);
		
		sit_down.setText("Sit-Down");
		sit_down.setId(R.id.sit_down);
		take_out.setText("Take-Out");
		take_out.setId(R.id.take_out);
		delivery.setText("Delivery");
		delivery.setId(R.id.delivery);
		//one.setText("One");
		//two.setText("Two");
		//three.setText("Three");
		//four.setText("Four");
		
		types.addView(sit_down);
		types.addView(take_out);
		types.addView(delivery);
		//types.addView(one);
		//types.addView(two);
		//types.addView(three);
		//types.addView(four);
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
	
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.activity_main, menu);
    //    return true;
    //}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	
    	return(super.onCreateOptionsMenu(menu));
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
    	
    	/*@Override
    	public int getViewTypeCount() {
    	    return model.size() < 3 ? model.size() : model.size() % 3;
    	}

    	@Override
    	public int getItemViewType(int position) {
    	    return position % 3;
    	}*/
    }
    
    static class RestaurantHolder {
    	private TextView name = null;
    	private TextView address = null;
    	//private TextView date = null;
    	private ImageView icon = null;
    	
    	RestaurantHolder(View row) {
    		name = (TextView)row.findViewById(R.id.title);
    		address = (TextView)row.findViewById(R.id.address);
    		//date = (TextView)row.findViewById(R.id.date);
    		icon = (ImageView)row.findViewById(R.id.icon);
    	}
    	
    	void populateFrom(Restaurant r) {
    		name.setText(r.getName());
    		address.setText(r.getAddress());
    		//date.setText(r.getDate());

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
    		Restaurant r = model.get(position);
    		
    		name.setText(r.getName());
    		address.setText(r.getAddress());
    		notes.setText(r.getNotes());
    		//date.setText(r.getDate());
    		
    		if (r.getType().equals("sit_down")) {
    			types.check(R.id.sit_down);
    		} else if (r.getType().equals("take_out")) {
    			types.check(R.id.take_out);
    		} else {
    			types.check(R.id.delivery);
    		}
    		
    		getTabHost().setCurrentTab(1);
    	}
	};
	
}
