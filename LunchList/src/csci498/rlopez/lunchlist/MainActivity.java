package csci498.rlopez.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	List<Restaurant> model = new ArrayList<Restaurant>();
	RestaurantAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RadioGroup types = (RadioGroup)findViewById(R.id.types);
        addTypeButtons(types);
        
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurants);
        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);
        
        //AutoCompleteTextView autoComplete = (AutoCompleteTextView)findViewById(R.id.addr);
    	//autoComplete.setAdapter(adapter);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText)findViewById(R.id.name);
			EditText address = (EditText)findViewById(R.id.addr);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			setDeliveryType(types.getCheckedRadioButtonId(), r);
			adapter.add(r);
		}
	};

	private void addTypeButtons(RadioGroup types) {
		RadioButton sit_down = new RadioButton(this);
		RadioButton take_out = new RadioButton(this);
		RadioButton delivery = new RadioButton(this);
		RadioButton one = new RadioButton(this);
		RadioButton two = new RadioButton(this);
		RadioButton three = new RadioButton(this);
		RadioButton four = new RadioButton(this);
		
		sit_down.setText("Sit-Down");
		sit_down.setId(0);
		take_out.setText("Take-Out");
		take_out.setId(1);
		delivery.setText("Delivery");
		delivery.setId(2);
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
		case 0:
			r.setType("sit_down");
			break;
		
		case 1:
			r.setType("take_out");
			break;
			
		case 2:
			r.setType("delivery");
			break;
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
}
