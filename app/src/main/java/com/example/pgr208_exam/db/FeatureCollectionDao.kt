package com.example.pgr208_exam.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.pgr208_exam.api.AsyncListener
import com.example.pgr208_exam.gsontypes.collection.Feature
import com.example.pgr208_exam.gsontypes.collection.FeatureCollection
import com.example.pgr208_exam.gsontypes.collection.Geometry
import com.example.pgr208_exam.gsontypes.collection.Properties
import java.lang.Exception


var progress = 0;


class FeatureCollectionDao(context: Context,database: SQLiteDatabase,listener: AsyncListener<Feature>?) : AbstractDao<Feature>(context,database,listener) {


    override fun insert(features: List<Feature>) {

        var totalRows = features.size.toDouble()
        println(totalRows)

        for(feature in features) {

            val id = feature.getProperties().getId()
            var name = feature.getProperties().getName()
            val cords = feature.getGeometry().getCoordinates()
            val lat = cords[0]
            val lon = cords[1]

            var statement = database!!.compileStatement("INSERT INTO ${FEATURE_COLLECTION_TABLE} (id,name,lat,lon) VALUES(?,?,?,?)")
            statement.bindLong(1,id);
            statement.bindString(2,name);
            statement.bindDouble(3,lat)
            statement.bindDouble(4,lon);
            statement.executeInsert();

            progress += 1;

            var prog = progress/totalRows * 100

            listener?.updateBackground(prog.toInt())

        }

    }

    override fun createObject(cursor: Cursor) : Feature {

        val id = cursor.getLong(cursor.getColumnIndex("id"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val lat = cursor.getDouble(cursor.getColumnIndex("lat"))
        val lon = cursor.getDouble(cursor.getColumnIndex("lon"))

        //Create feature
        val feature = Feature()

        //Create properties object
        val properties = Properties()
        properties.setId(id)
        properties.setName(name)

        //Create Geometry object
        val geometry = Geometry()
        val cords = listOf(lat,lon)
        geometry.setCoordinates(cords)

        //Insert Geometry and Properties to the Feature
        feature.setProperties(properties);
        feature.setGeometry(geometry);

        return feature;

    }


}