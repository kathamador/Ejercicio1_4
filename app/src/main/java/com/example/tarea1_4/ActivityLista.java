package com.example.tarea1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.tarea1_4.Configuracion.Operaciones;
import com.example.tarea1_4.Configuracion.SQLiteConexion;
import com.example.tarea1_4.clases.personas;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView lista;

    ArrayList<String> ArregloImagenes;
    ArrayList<personas> ListaImagenes;

    Button atras;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        conexion  = new SQLiteConexion(this, Operaciones.NameDatabase, null, 1);
        lista = (ListView) findViewById(R.id.lista);
        atras = (Button) findViewById(R.id.btnAtras);
        ObtenerLista();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloImagenes);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarImagen(i);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ObtenerLista()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        personas list_emple = null;
        ListaImagenes = new ArrayList<personas>();

        Cursor cursor = db.rawQuery(Operaciones.SELECT_ALL_TABLE_PICTURE,null);

        while(cursor.moveToNext())
        {
            list_emple = new personas();

            list_emple.setCodigo(cursor.getInt(0));                 //ID
            list_emple.setNombres(cursor.getString(1));         //Nombre
            list_emple.setDescripcion(cursor.getString(2));     //Descripcion
            list_emple.setPathImage(cursor.getString(3));
            list_emple.setImage(cursor.getBlob(4));

            ListaImagenes.add(list_emple);
        }

        cursor.close();
        llenarlista();
    }
    private void mostrarImagen(int i){

        personas personas = ListaImagenes.get(i);

        Bundle bundle = new Bundle();
        bundle.putSerializable("personas", personas);

        Intent intent = new Intent(getApplicationContext(), ActivityDetalles.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    private void llenarlista()
    {
        ArregloImagenes = new ArrayList<String>();

        for(int i=0; i<ListaImagenes.size(); i++)
        {
            ArregloImagenes.add(ListaImagenes.get(i).getCodigo() +" | " +
                    ListaImagenes.get(i).getNombres() +" \n "+
                    ListaImagenes.get(i).getDescripcion());
        }

    }
}