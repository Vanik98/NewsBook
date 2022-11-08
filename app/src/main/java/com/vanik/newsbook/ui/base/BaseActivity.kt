package com.vanik.newsbook.ui.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.vanik.newsbook.ui.web.ResultWebActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    @SuppressLint("SuspiciousIndentation")
    protected fun <T: BaseActivity> openAnotherActivity(anyActivity: Class<T>, vararg saveAny: HashMap<String,Any>) {
        val intent = Intent(this, anyActivity)
        for (any in saveAny) {
            intent.putExtra("${any.keys}", Json.encodeToString(any.values))
        }
        startActivity(intent)
    }

    protected fun <T : BaseActivity>getStringExtra(anyActivity: Class<T>, name: String): Any? {
        val intent = Intent(this, anyActivity::class.java)
        val any = intent.getStringExtra(name)
        return any?.let { Json.decodeFromString(it) }
    }
}