package com.hackathon.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marcus on 02-Dec-17.
 */

public abstract class Model {

    protected abstract JSONObject getJSON() throws JSONException;

}
