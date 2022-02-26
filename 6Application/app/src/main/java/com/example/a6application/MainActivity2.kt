package com.example.a6application

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //画面を表示するアクティビティ

        //メイン画面から渡されたデータを取得
        val menuName = intent.getStringExtra("menuName")
        val menuPrice = intent.getStringExtra("menuPrice")

        //料理名と金額を表示するtextviewを取得
        val tvmenuName = findViewById<TextView>(R.id.Name)
        val tvmenuPrice = findViewById<TextView>(R.id.Price)

        //TextViewに料理名と金額を表示
        tvmenuName.text = menuName
        tvmenuPrice.text = menuPrice

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returnVal = true

        if (item.itemId == android.R.id.home) {
            finish ()
        }
        else{
            returnVal = super.onOptionsItemSelected(item)
        }
        return returnVal
    }
}



