package ssui.fabbasi.tourguide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class generates the individual ListActivity items with a given Locale array.
 * @author Faiz
 *
 */
public class LocaleAdapter extends ArrayAdapter<Locale> {
	
	Context context;
	int textViewResourceId;
	Locale[] objects;

	/**
	 * Generate a LocaleAdapter which creates the list items and adds the necessary data of a ListView.
	 * @param context
	 * @param textViewResourceId id of the list item xml spec for the ListView
	 * @param objects an array of Locale objects to be used to generate the list items
	 */
	public LocaleAdapter(Context context, int textViewResourceId,
			Locale[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;
	}
	
	/**
	 * This method gets the view and adds the appropriate data to the view.
	 */
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LocaleHolder holder = null;
        
        //If the row is empty, we create a LocaleHolder object, which is to contain the necessary views, and
        //associate it with the current row.
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(textViewResourceId, parent, false);
            
            holder = new LocaleHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imageView1);
            holder.txtTitle = (TextView)row.findViewById(R.id.textView1);
            holder.txtDesc = (TextView)row.findViewById(R.id.textView2);
            
            row.setTag(holder);
        }
        //If the row exists, we get the LocaleHolder at the given row.
        else
        {
            holder = (LocaleHolder)row.getTag();
        }
        
        //We retrieve the locale at the given position of the data array, and place the data into the appropriate Views of our holder
        //object, to be shown at the row.
        Locale locale = objects[position];
        holder.txtTitle.setText(locale.getName());
        holder.txtDesc.setText(locale.getDescription());
        holder.imgIcon.setImageResource(locale.getImage());
        
        return row;
    }

	/**
	 * View container of a row.
	 * @author Faiz
	 *
	 */
	static class LocaleHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;
    }
}
