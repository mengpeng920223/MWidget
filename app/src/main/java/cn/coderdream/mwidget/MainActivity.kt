package cn.coderdream.mwidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.coderdream.widget.StatusBarUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.fullScreen(this)
        setContentView(R.layout.activity_main)
    }
}
