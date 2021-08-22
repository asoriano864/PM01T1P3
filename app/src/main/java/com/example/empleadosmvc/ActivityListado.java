package com.example.empleadosmvc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ActivityListado extends AppCompatActivity implements Observer, View.OnClickListener{

    EmpleadosController empleadosController = new EmpleadosController();
    ListView listaEmpleados;
    ArrayList<Empleados> empleadosArreglo;
    ArrayList<String> empleadosString = new ArrayList<>();
    int idEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        listaEmpleados = (ListView) findViewById(R.id.lstEmpleados);
        TextView txtBuscar = (TextView) findViewById(R.id.txtBuscar);

        idEmpleado = 0;
        empleadosController.openDataBase(getApplicationContext());
        empleadosArreglo = empleadosController.listEmpleados();
        for (int i = 0; i < empleadosArreglo.size(); i++) {
            empleadosString.add(empleadosArreglo.get(i).getNombre() + " " + empleadosArreglo.get(i).getApellidos());
        }
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, empleadosString);
        listaEmpleados.setAdapter(adp);

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listaEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String nombre = "";
                for (int i = 0; i < empleadosArreglo.size(); i++){
                    if((empleadosArreglo.get(i).getNombre() + " " + empleadosArreglo.get(i).getApellidos()).equalsIgnoreCase(listaEmpleados.getItemAtPosition(position).toString())){
                        idEmpleado = empleadosArreglo.get(i).getId();
                         nombre = empleadosArreglo.get(i).getNombre() + " " + empleadosArreglo.get(i).getApellidos();
                    }
                }

                Toast.makeText(getApplicationContext(), "Ha seleccionado a: " + nombre, Toast.LENGTH_LONG).show();
            }
        });

        Button btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(this);

        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnEditar:
                if(idEmpleado == 0){
                    mostrarDialogoNoSeleccionado();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ActivityEdit.class);
                    intent.putExtra("id", String.valueOf(idEmpleado));
                    startActivity(intent);
                }
                break;

            case R.id.btnVolver:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    private void mostrarDialogoNoSeleccionado() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta de Selección")
                .setMessage("No ha seleccionado a ningún empleado")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}