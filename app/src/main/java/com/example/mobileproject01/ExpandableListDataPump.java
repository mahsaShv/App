
package com.example.mobileproject01;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ExpandableListDataPump {
    private MessageController messageController;

    ExpandableListDataPump(Context context){
        super();
        messageController = MessageController.getInstance(context);
    }
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();



        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");

        expandableListDetail.put("Sports", cricket);
        expandableListDetail.put("Nature", football);
        expandableListDetail.put("Cinema", basketball);
        return expandableListDetail;
    }
}

