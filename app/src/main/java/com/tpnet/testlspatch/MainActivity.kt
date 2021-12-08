package com.tpnet.testlspatch

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : Activity() {

    private var mImageTest: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageTest = findViewById(R.id.mIvTest)
    }

    fun setImage(id: Int) {
        mImageTest?.setImageResource(id)
    }
}