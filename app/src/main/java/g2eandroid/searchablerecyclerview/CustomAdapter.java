package g2eandroid.searchablerecyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by Tan on 3/14/2016.
 */

public class CustomAdapter extends CursorAdapter {
    TextView txtId,txtName,txtEmail;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View   view    =    mInflater.inflate(R.layout.item, parent, false);
        ViewHolder holder  =   new ViewHolder();
        holder.txtId    =   (TextView)  view.findViewById(R.id.txtId);
        holder.txtName    =   (TextView)  view.findViewById(R.id.txtName);

        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        //If you want to have zebra lines color effect uncomment below code
        /*if(cursor.getPosition()%2==1) {
             view.setBackgroundResource(R.drawable.item_list_backgroundcolor);
        } else {
            view.setBackgroundResource(R.drawable.item_list_backgroundcolor2);
        }*/

        final ViewHolder holder  =   (ViewHolder)    view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex(Student.KEY_email)));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex(Student.KEY_name)));

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("testing","id = "+cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                Log.e("testing","name = "+cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                Log.e("testing","email = "+cursor.getString(cursor.getColumnIndex(Student.KEY_email)));
                Log.e("testing","name2 = "+  holder.txtName.getText().toString());
                Log.e("testing","email2 = "+  holder.txtId.getText().toString());

            /*    Intent intent = new Intent(context, Activity_NewBrands.class);
                SharedPreferences prefuserdata = context.getSharedPreferences("productid", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("productid", "" + holder.txtId.getText().toString());

                prefeditor.commit();


                context.startActivity(intent);
*/

            }
        });
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtName;

    }
}