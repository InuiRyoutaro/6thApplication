package com.example.a6application

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //下記はプロパティエリア
    //リストビューに表示するリストデータをプロパティエリアで指定
    private var _menuList: MutableList<MutableMap<String, Any>> = mutableListOf()

    //SimpleAdapterの引数に使うプロパティエリアで指定している
    private val _from = arrayOf("name", "price")
    private val _to = intArrayOf(R.id.tvMenu, R.id.tvMenuPriceRow)

    //ペーパーオブジェクトを生成するための設定で変数を定義している
    private var _kaguId = -1
    private var _kaguName = ""
    private val _helper = DatabaseHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SimpleAdapterで使用する定食メニューListオブジェクトをprivateメソッドを利用して用意し、プロパティに格納。
        _menuList = createMainList()
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        //変数menuにListViewから探してきたlvと言うidを代入する
        val adapter = SimpleAdapter(this@MainActivity, _menuList, R.layout.row, _from, _to)

        lvMenu.adapter = adapter
        lvMenu.onItemClickListener = ListItemClickListener() //ListItemClickListener　リストメニューを繋げるメソッド
        registerForContextMenu(lvMenu)

    }

    //ヘルパーオブイジェクトの解放
    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // オプションメニュー用xmlファイルをインフレイト。
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        // truを返している。
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 戻り値用の変数を初期値trueで用意。
        var returnVal = true
        // 選択されたメニューのIDのR値による処理の分岐。
        when (item.itemId) {
            // 定食メニューが選択された場合の処理。
            R.id.menuListOptionMain ->
                // メインメニューリストデータの生成。
                _menuList = createMainList()
            // カレーメニューが選択された場合の処理。
            R.id.menuListOptionSide ->
                //サイドメニューリストデータの生成。
                _menuList = createSideList()
            // それ以外…
            else ->
                // 親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする。
                returnVal = super.onOptionsItemSelected(item)
        }
        // 画面部品ListViewを取得。
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        // SimpleAdapterを選択されたメニューデータで生成。
        val adapter = SimpleAdapter(this@MainActivity, _menuList, R.layout.row, _from, _to)
        // アダプタの登録。
        lvMenu.adapter = adapter
        return returnVal
    }


    override fun onCreateContextMenu(
        menu: ContextMenu,
        view: View,
        menuInfo: ContextMenu.ContextMenuInfo
    ) {
        super.onCreateContextMenu(menu, view, menuInfo)
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        menu.setHeaderTitle(R.string.menu_list_context_header)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //戻り値の変数をtureで返している
        var returnVal = true
        //// 長押しされたリストのポジションを取得。
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // 長押しされたリストのポジションを取得。
        val listPosition = info.position
        // ポジションから長押しされたメニュー情報Mapオブジェクトを取得。
        val menu = _menuList[listPosition]

        // 選択されたメニューのIDのR値による処理の分岐。
        when (item.itemId) {
            // ［詳細］メニューが選択された時の処理。
            R.id.menuListContextDesc -> {
                // メニューの説明文字列を取得。
                val desc = menu["desc"] as String
                // トーストを表示。
                Toast.makeText(this@MainActivity, desc, Toast.LENGTH_LONG).show()
            }
            // ［更新］メニューが選択された時の処理。
            R.id.menuListContextOrder ->
                // 注文処理。
                order(menu)
            // それ以外…
            else ->
                // 親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする。
                returnVal = super.onContextItemSelected(item)
        }
        return returnVal
    }

    private fun createMainList(): MutableList<MutableMap<String, Any>> {
        // 定食メニューリスト用のListオブジェクトを用意。
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
        // 「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        var menu =
            mutableMapOf<String, Any>("name" to "お好み焼き定食", "price" to 5000, "desc" to "一級品です。知らんけど")
        menuList.add(menu)
        // 「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        menu = mutableMapOf("name" to "焼きそば定食", "price" to 5000, "desc" to "一級品です。知らんけど\"")
        menuList.add(menu)
        // 以下データ登録の繰り返し。
        menu = mutableMapOf("name" to "たこ焼き定食", "price" to 5000, "desc" to "一級品です。知らんけど\"")
        menuList.add(menu)
        menu = mutableMapOf("name" to "串カツ定食", "price" to 5000, "desc" to "一級品です。知らんけど\"")
        menuList.add(menu)
        menu = mutableMapOf("name" to "野菜炒め定食", "price" to 5000, "desc" to "一級品です。知らんけど\"")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ふぐ鍋定食", "price" to 5000, "desc" to "一級品です。知らんけど\"")
        menuList.add(menu)

        return menuList
    }

    private fun createSideList(): MutableList<MutableMap<String, Any>> {
        // カレーメニューリスト用のListオブジェクトを用意。
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
        // 「ビーフカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        var menu = mutableMapOf<String, Any>("name" to "枝豆", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        // 「ポークカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録。
        menu = mutableMapOf("name" to "冷やしトマト", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        // 以下データ登録の繰り返し。
        menu = mutableMapOf("name" to "納豆", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼きチーズ", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼き魚", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        menu = mutableMapOf("name" to "明太子", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ふりかけおにぎり", "price" to 500, "desc" to "めちゃうまいです。知らんけど")
        menuList.add(menu)
        return menuList
    }

    private fun order(menu: MutableMap<String, Any>) {
        // 定食名と金額を取得。Mapの値部分がAny型なのでキャストが必要。
        val menuName = menu["name"] as String
        val menuPrice = menu["price"] as Int

        // インテントオブジェクトを生成。
        val intent2MenuThanks = Intent(this@MainActivity, MainActivity2::class.java)
        // 第2画面に送るデータを格納。
        intent2MenuThanks.putExtra("menuName", menuName)
        // MenuThanksActivityでのデータ受け取りと合わせるために、金額にここで「円」を追加する。
        intent2MenuThanks.putExtra("menuPrice", "${menuPrice}円")
        // 第2画面の起動。
        startActivity(intent2MenuThanks)
    }


//    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
//        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//            // タップされた行のデータを取得。SimpleAdapterでは1行分のデータはMutableMap型!
//            val item = parent.getItemAtPosition(position) as MutableMap<String, Any>
//            // 注文処理。
//            order(item)
//        }
//    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // 注文確認ダイアログフラグメントオブジェクトを生成。
            val dialogFragment = OrderConfirmDialogFragment()
            // ダイアログ表示。
            dialogFragment.show(supportFragmentManager, "OrderConfirmDialogFragment")
        }
    }
}



