package csci498.rlopez.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
	Restaurant r = new Restaurant();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RadioGroup types = (RadioGroup)findViewById(R.id.types);
        addTypeButtons(types);
        
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(onSave);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			EditText name = (EditText)findViewById(R.id.name);
			EditText address = (EditText)findViewById(R.id.addr);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			setDeliveryType(types.getCheckedRadioButtonId());
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
		one.setText("One");
		two.setText("Two");
		three.setText("Three");
		four.setText("Four");
		
		types.addView(sit_down);
		types.addView(take_out);
		types.addView(delivery);
		types.addView(one);
		types.addView(two);
		types.addView(three);
		types.addView(four);
	}
	
	private void setDeliveryType(int type) {
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
}
