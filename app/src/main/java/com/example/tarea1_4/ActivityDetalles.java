package com.example.tarea1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tarea1_4.clases.personas;

import java.io.ByteArrayInputStream;

public class ActivityDetalles extends AppCompatActivity {
    EditText MostrarNombre, MostrarDescripcion;
    ImageView MostrarImagen;
    Button atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        atras = (Button) findViewById(R.id.btnAtras);

        MostrarNombre = (EditText) findViewById(R.id.txtnombre);
        MostrarImagen = (ImageView) findViewById(R.id.imageView);

        MostrarDescripcion = (EditText) findViewById(R.id.txtdesc);
        Bundle objetEnvia = getIntent().getExtras();


        personas imagen = null;
        if(objetEnvia != null){
            imagen = (personas) objetEnvia.getSerializable("personas");

            MostrarNombre.setText(imagen.getNombres());
            MostrarDescripcion.setText(imagen.getDescripcion());

            mostrarImagen(imagen.getImage());
            Bitmap image = BitmapFactory.decodeFile(imagen.getPathImage());

            MostrarImagen.setImageBitmap(image);
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityLista.class);
                startActivity(intent);
            }
        });

    }
    private void mostrarImagen(byte[] image) {
        Bitmap bitmap = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(image);
        bitmap = BitmapFactory.decodeStream(bais);
        MostrarImagen.setImageBitmap(bitmap);
    }
}