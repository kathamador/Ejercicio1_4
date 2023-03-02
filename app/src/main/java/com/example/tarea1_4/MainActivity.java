package com.example.tarea1_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tarea1_4.Configuracion.Operaciones;
import com.example.tarea1_4.Configuracion.SQLiteConexion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText nombre, descripcion;
    Button btnGuardar,btnlista,btnTomarFoto;
    Bitmap imagenBit;
    ImageView imagenV;
    String FotoPath;
    static final int PETICION_ACCESO_CAM = 100;
    static final int TAKE_PIC_REQUEST = 101;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText) findViewById(R.id.txtNombre);
        descripcion = (EditText) findViewById(R.id.txtdescripcion);
        imagenV = (ImageView) findViewById(R.id.imagen);
        btnGuardar = (Button) findViewById(R.id.btnguardar);
        btnTomarFoto = (Button) findViewById(R.id.btntake);
        btnlista = (Button) findViewById(R.id.btnlista);

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLista.class);
                startActivity(intent);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    validaciones();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Debe de tomarse una foto ",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });
    }

    private void agregarPictureSQL(){

        SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Operaciones.nombre, nombre.getText().toString());
        values.put(Operaciones.descripcion, descripcion.getText().toString());
        values.put(Operaciones.Imagen, FotoPath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(10480);

        imagenBit.compress(Bitmap.CompressFormat.JPEG, 0 , baos);

        byte[] blob = baos.toByteArray();

        values.put(Operaciones.image, blob);

        Long result = db.insert(Operaciones.tablapersona, Operaciones.id, values);

        Toast.makeText(getApplicationContext(), "Registro exitoso " + result.toString()
                ,Toast.LENGTH_LONG).show();

        db.close();

        limpiar();
    }

    private void limpiar()
    {
        nombre.setText("");
        descripcion.setText("");
        imagenV.setImageBitmap(null);
        imagenBit = null;

    }

    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESO_CAM);
        }
        else
        {
            TomarFoto();
        }
    }

    private File CrearImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpeg",
                storageDir
        );

        FotoPath = image.getAbsolutePath();
        return image;
    }

    private void TomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = CrearImagen();
            } catch (IOException ex) {
                ex.toString();
            }
            try {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.tarea1_4.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    startActivityForResult(takePictureIntent, TAKE_PIC_REQUEST);
                }
            }catch (Exception e){
                Log.i("Error", "dispatchTakePictureIntent: " + e.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_ACCESO_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                TomarFoto();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Se necesitan permisos de acceso a la camara", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Byte[] arreglo;

        if(requestCode == TAKE_PIC_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap image = BitmapFactory.decodeFile(FotoPath);
            imagenBit = image;
            imagenV.setImageBitmap(image);
            //Toast.makeText(getApplicationContext(), "La imagen se registro con exito.",Toast.LENGTH_LONG).show();
        }
    }

    public void validaciones(){
        if (nombre.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir un nombre." ,Toast.LENGTH_LONG).show();
        }else if (descripcion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir una descripcion." ,Toast.LENGTH_LONG).show();
        }else{
            agregarPictureSQL();
        }
    }
}