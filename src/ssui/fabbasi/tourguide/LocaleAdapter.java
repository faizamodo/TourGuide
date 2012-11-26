package ssui.fabbasi.tourguide;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocaleAdapter extends ArrayAdapter<Locale> {
	
	Context context;
	int textViewResourceId;
	Locale[] objects;

	public LocaleAdapter(Context context, int textViewResourceId,
			Locale[] objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.objects = objects;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LocaleHolder holder = null;
        
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
        else
        {
            holder = (LocaleHolder)row.getTag();
        }
        
        Locale locale = objects[position];
        holder.txtTitle.setText(locale.getName());
        holder.txtDesc.setText(locale.getDescription());
        holder.imgIcon.setImageResource(locale.getImage());
        
        return row;
    }

	static class LocaleHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDesc;
    }
}
