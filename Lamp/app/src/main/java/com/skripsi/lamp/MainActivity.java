package com.skripsi.lamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skripsi.lamp.adapter.MenuAdapter;
import com.skripsi.lamp.model.MenuModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private ArrayList<MenuModel> menuModels = new ArrayList<>();
    private MenuAdapter adapter;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new MenuAdapter(this,menuModels);
        gridView.setAdapter(adapter);

        Cekjaringan();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idMenu = menuModels.get(position).getIdMenu();
                String namaMenu = menuModels.get(position).getNamaMenu();
                Intent intent = new Intent(getApplicationContext(), RenameSaklar.class);
                Bundle b = new Bundle();
                b.putString("id_saklar",idMenu);
                b.putString("nama_saklar",namaMenu);
                intent.putExtras(b);
                startActivity(intent);
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idMenu = menuModels.get(position).getIdMenu();
                String statusMenu = menuModels.get(position).getStatusMenu();
                String stts = "";
                if(idMenu.equals("4")){
                    if(statusMenu.equals("mati") || statusMenu.equals("Padam")){
                        stts = "hidup";
                    }
                    else {
                        stts = "mati";
                    }
                }
                else{
                    if(statusMenu.equals("mati") || statusMenu.equals("Padam")){
                        stts = "mati";
                    }
                    else {
                        stts = "hidup";
                    }
                }
                final String finalStts = stts;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_saklar,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Error while sending data",Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded";
                    }

                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_saklar",idMenu);
                        params.put("status_saklar", finalStts);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

    }
    private void Cekjaringan(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.url_cekjaringan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GetSaklar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("! Alert");
                        builder.setMessage("Terjadi masalah saat menghubungi Server !");
                        builder.setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setPositiveButton("Abaikan",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cek", "coba");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);;
    }
    private void GetSaklar(){
        JsonArrayRequest saklarReq = new JsonArrayRequest(Constant.url_saklar,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                MenuModel menuModel = new MenuModel();
                                menuModel.setNamaMenu(obj.getString("nama_saklar"));
                                menuModel.setIdMenu(obj.getString("id_saklar"));
                                String id = obj.getString("id_saklar");
                                String status = obj.getString("status_saklar");
                                if(id.equals("4")){
                                    if(status.equals("mati")){
                                        menuModel.setStatusMenu("Padam");
                                        menuModel.setIcon(R.drawable.gasoff);
                                    }
                                    else {
                                        menuModel.setIcon(R.drawable.gason);
                                        menuModel.setStatusMenu("Menyala");
                                    }
                                }
                                else{
                                    if(status.equals("hidup")){
                                        menuModel.setStatusMenu("Padam");
                                        menuModel.setIcon(R.drawable.lampoff);
                                    }
                                    else{
                                        menuModel.setStatusMenu("Menyala");
                                        menuModel.setIcon(R.drawable.lampon);
                                    }
                                }


                                menuModels.add(menuModel);

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error while getting data",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(saklarReq);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.senter) {
            Intent intent = new Intent(getApplicationContext(),Senter.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
