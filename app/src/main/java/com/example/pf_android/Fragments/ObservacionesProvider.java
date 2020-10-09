package com.example.pf_android.Fragments;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

import java.util.HashMap;

public class ObservacionesProvider extends ContentProvider {
    public ObservacionesProvider() {
    }

    static final String PROVIDER_NAME =
            "com.example.pf_android.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/observaciones";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String CODIGO = "codigo";
    static final String DESCRIPCION = "descripcion";
    static final String FENOMENO = "fenomeno";
    static final String DEPARTAMENTO = "departamento";
    static final String LOCALIDAD = "localidad";
    static final String ZONA = "zona";
    static final String LONGITUD = "longitud";
    static final String LATITUD = "latitud";
    static final String ALTITUD = "altitud";
    static final String DATE = "date";

    private static HashMap<String, String> OBSERVACIONES_PROJECTION_MAP;
    static final int OBSERVACIONES = 1;
    static final int OBSERVACION_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "observaciones", OBSERVACIONES);
        uriMatcher.addURI(PROVIDER_NAME, "observacion/#", OBSERVACION_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "GreenPlace";
    static final String OBSERVACIONES_TABLE_NAME = "observaciones";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + OBSERVACIONES_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " codigo TEXT NOT NULL, " +
                    " descripcion TEXT NOT NULL, " +
                    " fenomeno TEXT NOT NULL, " +
                    " departamento TEXT NOT NULL, " +
                    " localidad TEXT NOT NULL, " +
                    " zona TEXT NOT NULL, " +
                    " longitud TEXT NOT NULL, " +
                    " latitud TEXT NOT NULL, " +
                    " altitud TEXT NOT NULL, " +
                    " date TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int
                newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + OBSERVACIONES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(OBSERVACIONES_TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String
            selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(OBSERVACIONES_TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case OBSERVACIONES:
                qb.setProjectionMap(OBSERVACIONES_PROJECTION_MAP);
                break;
            case OBSERVACION_ID:
                qb.appendWhere(_ID + "=" +
                        uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = CODIGO;
        }
        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case OBSERVACIONES:
                count = db.delete(OBSERVACIONES_TABLE_NAME, selection,
                        selectionArgs);
                break;
            case OBSERVACION_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(OBSERVACIONES_TABLE_NAME, _ID + " = " + id
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Obtiene todas las personas
             */
            case OBSERVACIONES:
                return "vnd.android.cursor.dir/vnd.ejemplo.personas";
            /**
             * Obtiene una persona
             */
            case OBSERVACION_ID:
                return "vnd.android.cursor.item/vnd.ejemplo.personas";
            default:
                throw new IllegalArgumentException("Unsupported URI: " +
                        uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case OBSERVACIONES:
                count = db.update(OBSERVACIONES_TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case OBSERVACION_ID:
                count = db.update(OBSERVACIONES_TABLE_NAME, values, _ID + " = "
                        + uri.getPathSegments().get(1) +
                (!TextUtils.isEmpty(selection) ? " AND ("
                        +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri
                );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
