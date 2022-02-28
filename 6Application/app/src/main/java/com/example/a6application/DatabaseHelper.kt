package com.example.a6application

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//DB   Android端末にはPCのような大きなデータベースはない
//SQLite    Android OSにあらかじめ備わっているデータベースのこと
//手順は大きく分けて４つ存在する
//①ヘルパークラスを作成してオブジェクト化する（ヘルパーオブジェクト）
//②ヘルパーオブジェクトをDBオブジェクトとつなぐ
//③
//④



//①DBヘルパークラスの作成↓
class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATAB ASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "ryourmemos.db"
        private const val DATABASE_VERSION = 1
    }

    //OnCreateメソッドで作ります。
    // 何を？→SQLiteDatabaseというものを
    // どこに？→DBを端末内に。
    override fun onCreate(db: SQLiteDatabase){
        val sb = StringBuilder() //StringBuilder　文字として組み立てられたものを変数のsbに代入する
        //SB　StringBuilder
        //sb.append     StringBuilderクラスのappendメソッドで文字列を結合することができる
        sb.append("CREATE TABLE kagumemos(")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("name TEXT,")
        sb.append("note TEXT")
        sb.append(");")
        val sql = sb.toString()

        db.execSQL(sql)//execSQLメソッド：  SQLの実行をする
    }

    override fun onUpgrade(db:SQLiteDatabase,oldVersion:Int,newVersion:Int){

    }
}
