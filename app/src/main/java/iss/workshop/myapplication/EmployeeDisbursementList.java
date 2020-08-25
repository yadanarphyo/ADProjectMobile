package iss.workshop.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementAPImodel;


public class EmployeeDisbursementList extends AppCompatActivity {
    ListView listView;
    List<DisbursementAPImodel> Listofdisbursements = new ArrayList<>();
    List<Integer> listofdisbursementid = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_disbursement_list);
        String CurrentEmployeeid;
        SharedPreferences pref = getSharedPreferences("loggedInUser", MODE_PRIVATE);
        if (pref.contains("id") && pref.contains("role")) {
            CurrentEmployeeid = String.valueOf(pref.getInt("id",0));

            //(GET) Retrieve all disbursements using employeeid
            String server_url4 = "http://10.0.2.2:5000/api/disbursementemployeeAPI/GetAllDisbursements/"+CurrentEmployeeid;
            JsonArrayRequest request = new JsonArrayRequest
                    (server_url4,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    ObjectMapper mapper = new ObjectMapper();
                                    try {
                                        List<DisbursementAPImodel> objects = mapper.readValue(String.valueOf(response), new TypeReference<List<DisbursementAPImodel>>() {
                                        });
                                        Listofdisbursements = objects;
                                        for (DisbursementAPImodel i : Listofdisbursements) {
                                            listofdisbursementid.add(i.Id);
                                        }

                                        DisbursementListEmployeeAdapter disbursementListEmployeeAdapter = new DisbursementListEmployeeAdapter(getApplicationContext(), Listofdisbursements);
                                        listView = (ListView) findViewById(R.id.disbursement_List);
                                        ViewGroup header = (ViewGroup) getLayoutInflater().inflate(R.layout.disbursementlistheader, listView, false);
                                        listView.addHeaderView(header);
                                        if (Listofdisbursements != null) {
                                            listView.setAdapter(disbursementListEmployeeAdapter);
                                        }

                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);


        }
    }
}
