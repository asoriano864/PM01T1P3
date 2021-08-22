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

public class ActivityEdit extends AppCompatActivity implements Observer, View.OnClickListener{

    String idEmpleado;
    EmpleadosController empleadosController= new EmpleadosController();
    Empleados empleado;
    TextView txtNombre, txtApellidos, txtEdad, txtDireccion, txtPuesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtNombre = (TextView) findViewById(R.id.txtNombreEmpleado2);
        txtApellidos = (TextView) findViewById(R.id.txtApellidoEmpleado2);
        txtEdad = (TextView) findViewById(R.id.txtEdadEmpleado2);
        txtDireccion = (TextView) findViewById(R.id.txtDireccionEmpleado2);
        txtPuesto = (TextView) findViewById(R.id.txtPuestoEmpleado2);

        Intent intent = getIntent();
        idEmpleado = intent.getStringExtra("id");

        empleadosController.openDataBase(getApplicationContext());
        empleado = empleadosController.readEmpleado(Integer.valueOf(idEmpleado));

        txtNombre.setText(empleado.getNombre());
        txtApellidos.setText(empleado.getApellidos());
        txtEdad.setText(String.valueOf(empleado.getEdad()));
        txtDireccion.setText(empleado.getDireccion());
        txtPuesto.setText(empleado.getPuesto());

        Button btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(this);

        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);

        Button btnVolverLista = (Button) findViewById(R.id.btnVolverLista);
        btnVolverLista.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnActualizar:
                if(!txtNombre.getText().toString().isEmpty() && !txtApellidos.getText().toString().isEmpty() && !txtDireccion.getText().toString().isEmpty() &&
                        !txtEdad.getText().toString().isEmpty() && !txtPuesto.getText().toString().isEmpty()) {

                    empleado = new Empleados(Integer.valueOf(idEmpleado),
                            txtNombre.getText().toString(),
                            txtApellidos.getText().toString(),
                            Integer.valueOf(txtEdad.getText().toString()),
                            txtDireccion.getText().toString(),
                            txtPuesto.getText().toString());

                    empleadosController.openDataBase(getApplicationContext());
                    empleadosController.updateEmpleado(empleado);
                    Toast.makeText(getApplicationContext(), "Empleado Actualizado", Toast.LENGTH_LONG).show();
                } else {
                    mostrarDialogoVacios();
                }
                break;

            case R.id.btnEliminar:
                if(Integer.valueOf(idEmpleado) != 0) {
                    empleadosController.openDataBase(getApplicationContext());
                    empleadosController.deleteEmpleado(Integer.valueOf(idEmpleado));
                    Toast.makeText(getApplicationContext(), "Empleado Eliminado", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ActivityListado.class);
                    startActivity(intent);
                } else {
                    mostrarDialogoVacios();
                }
                break;

            case R.id.btnVolverLista:
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

}