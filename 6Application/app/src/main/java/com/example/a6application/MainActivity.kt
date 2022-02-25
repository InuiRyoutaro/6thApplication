package com.example.a6application

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        _menuList  = createMainMenuList()
        val lvMenu =findViewById<ListView>(R.id.lvMenu)
        //変数menuにListViewから探してきたlvと言うidを代入する
        val menuList:MutableList<MutableMap<String,String>> = mutableListOf()
        //MutableList：要素の追加や削除、入れ替えが可能なリスト＜文字、文字＞　
        //mutableListOf関数 :
        var menu = mutableMapOf("name" to "唐揚げ定食","price" to "850円")
            menuList.add(menu)
        menu = mutableMapOf("name" to "ハンバーグ定食","price" to "750円")
            menuList.add(menu)
        menu = mutableMapOf("name" to "生姜焼き定食","price" to "900円")
            menuList.add(menu)
        menu = mutableMapOf("name" to "焼きそば定食","price" to "650円")
            menuList.add(menu)
        menu = mutableMapOf("name" to "お好み焼き定食","price" to "750円")
            menuList.add(menu)
        //val: 再代入できない参照を保持するための変数 (immutable reference)
        // var: 再代入可能な変数 (mutable reference)。variable の略
        val from = arrayOf("name","price")
        val to = intArrayOf(android.R.id.text1,android.R.id.text2)
        val adapter = SimpleAdapter(this@MainActivity,menuList,android.R.layout.simple_list_item_2,from, to)

        lvMenu.adapter = adapter

        lvMenu.onItemClickListener = ListItemClickListener()

        registerForContextMenu(lvMenu)
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        //リストがタップされた時のアクティビティ
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long){
            val item = parent.getItemAtPosition(position) as MutableMap<String,String>
            //タップされた行のデータを取得。SimpleAdapter　1行分のデータはMutableMap型
            val menuName = item["name"] as  String
            val menuPrice = item["price"] as String
            //料理名と金額のデータを取得
            val intent2Activity1 = Intent(this@MainActivity,MainActivity2 ::class.java)
            //インデントオブジェクトを作る
            intent2Activity1.putExtra("mateName",menuName)
            intent2Activity1.putExtra("matePrice","${menuPrice}円")
            //遷移先の画面に送るデータを格納している
            //intent：他の機能や画面との橋渡しを行う
            //putExtra：画面の中の値を受け渡す
            //遷移先に送るデータは、menuNameとmenuPrice
            startActivity(intent2Activity1)
            //遷移先の画面を起動
        }

    }

    override fun onCreateContextMenu(menu: ContextMenu,view: View,menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo)
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        menu.setHeaderTitle(R.string.lvmenu)
    }


//    private fun order(menu: MutableMap<String,Any>)
//        //タップされた行のデータを取得。SimpleAdapter　1行分のデータはMutableMap型
//        val menuName = menu["name"] as  String
//        val menuPrice = menu["price"] as Int
//        //料理名と金額のデータを取得
//        val intent2Activity1 = Intent(this@MainActivity,MainActivity2 ::class.java)
//        //インデントオブジェクトを作る
//        intent2Activity1.putExtra("mateName",menuName)
//        intent2Activity1.putExtra("matePrice","${menuPrice}円")
//        //遷移先の画面に送るデータを格納している
//        //intent：他の機能や画面との橋渡しを行う
//        //putExtra：画面の中の値を受け渡す
//        //遷移先に送るデータは、menuNameとmenuPrice
//        startActivity(intent2Activity1)
//        //遷移先の画面を起動
//    }

//    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long){
//        val item =parent.getItemAtPosition(position) as MutableMap<String, Any>
//        //タップされた行のデータを取得
//        order(item)
//    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        var returnVal = true
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val listPosition = info.position
        val menu = ListView[listPosition]

        when(item.itemId){
            R.id.ContextDetails-> {
                val desc = menu["desc"] as String
                Toast.makeText(this@MainActivity, desc, Toast.LENGTH_LONG).show()
            }
            R.id.contentOrder-> order(menu)
            order(menu)->

            else ->
                returnVal = super.onContextItemSelected(item)
        }
        return returnVal
    }


}


