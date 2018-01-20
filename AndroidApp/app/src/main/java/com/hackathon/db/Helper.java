package com.hackathon.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackathon.R;
import com.hackathon.models.Model;

/**
 * Created by Marcus on 02-Dec-17.
 */

public abstract class Helper <T extends Model> {

    protected final Context context;
    protected final SharedPreferences preferences;

    public Helper(Context context, String name) {
        this.context = context;
        this.preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public abstract T getSnapshot();
    public abstract void set(T t);

}
