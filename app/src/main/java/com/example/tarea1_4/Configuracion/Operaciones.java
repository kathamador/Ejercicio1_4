package com.example.tarea1_4.Configuracion;

//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga

public class Operaciones
{

    public static final String NameDatabase = "PM01BD";
    public static final String tablapersona = "persona";

    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String descripcion = "descripcion";
    public static final String Imagen = "Imagen";
    public static final String image = "image";

    public static final String CreateTablePersona = "CREATE TABLE persona " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre TEXT, descripcion TEXT, Imagen TEXT, image BLOB)";

    public static final String DropTableFotos = "DROP TABLE IF EXISTS " + tablapersona;
    public static final String SELECT_ALL_TABLE_PICTURE = "SELECT * FROM " + tablapersona;
}
