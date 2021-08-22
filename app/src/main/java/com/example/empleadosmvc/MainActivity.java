package com.example.empleadosmvc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, View.OnClickListener{

    EmpleadosController empleadosController = new EmpleadosController();
    Empleados empleado = new Empleados();
    TextView txtNombre, txtApellidos, txtEdad, txtDireccion, txtPuesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombre = (TextView) findViewById(R.id.txtNombreEmpleado);
        txtApellidos = (TextView) findViewById(R.id.txtApellidoEmpleado);
        txtEdad = (TextView) findViewById(R.id.txtEdadEmpleado);
        txtDireccion = (TextView) findViewById(R.id.txtDireccionEmpleado);
        txtPuesto = (TextView) findViewById(R.id.txtPuestoEmpleado);

        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);

        Button btnListar = (Button) findViewById(R.id.btnListarEmpleados);
        btnListar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnGuardar:
                if(!txtNombre.getText().toString().isEmpty() && !txtApellidos.getText().toString().isEmpty() && !txtDireccion.getText().toString().isEmpty() &&
                        !txtEdad.getText().toString().isEmpty() && !txtPuesto.getText().toString().isEmpty()) {

                    empleado = new Empleados(txtNombre.getText().toString(),
                            txtApellidos.getText().toString(),
                            Integer.valueOf(txtEdad.getText().toString()),
                            txtDireccion.getText().toString(),
                            txtPuesto.getText().toString());

                    empleadosController.openDataBase(getApplicationContext());
                    empleadosController.createEmpleado(empleado);
                    Toast.makeText(getApplicationContext(), "Empleado Guardado", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    mostrarDialogoVacios();
                }
                break;

            case R.id.btnListarEmpleados:
                Intent intent = new Intent(getApplicationContext(), ActivityListado.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    private void mostrarDialogoVacios() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta de Vacíos")
                .setMessage("No puede dejar ningún campo vacío")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtPuesto.setText("");
    }
}