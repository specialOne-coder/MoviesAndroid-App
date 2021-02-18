package com.ferdinand.movies.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DatabaseHelper(context: Context):SQLiteOpenHelper(context,"Database",null,1) {

    /***
     * Création de la table de la base de donnée
     */
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("CREATE TABLE filmsfavoris(id Integer primary key AUTOINCREMENT NOT NULL,image VARCHAR(255), titre VARCHAR(255),vote VARCHAR(255),description VARCHAR(255),sortie VARCHAR(255))")
        }
    }

    /***
     * Suppression de la table si elle existe
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE if exists filmsfavoris")
        }
    }

    /***
     * Méthode d'insertion de donnée dans la bse
     */

    fun insert(image:String,titre:String,vote:String,description:String,sortie:String):Boolean{
        val database = this.writableDatabase
        val contentValue = ContentValues()

        contentValue.put("image",image)
        contentValue.put("titre",titre)
        contentValue.put("vote",vote)
        contentValue.put("description",description)
        contentValue.put("sortie",sortie)

        val insertion = database.insert("filmsfavoris",null,contentValue)
        if(insertion.equals(-1)) return false
        else return true

    }

    /***
     * Méthode de suppression de donnée dans la base
     */

    fun retirer(title:String):Boolean{
        val db = this.readableDatabase
        val ret = db.execSQL("DELETE FROM filmsfavoris WHERE titre ='"+ title+"'")
        if(ret.equals(-1)) return false
        else return true
    }


}