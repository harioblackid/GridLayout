package com.bmti.gridlayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bmti.gridlayout.crud.DeleteProduk
import com.bmti.gridlayout.crud.TampilActivity
import com.bmti.gridlayout.crud.UpdateProduk
import com.bmti.gridlayout.crud.UploadProduk
import com.bmti.gridlayout.databinding.ActivityProdukBinding

class ProdukActivity : AppCompatActivity() {
    // Deklarasi Variable
    private lateinit var data: ActivityProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        data = ActivityProdukBinding.inflate(layoutInflater)
        setContentView(data.root)

        data.bUpload.setOnClickListener {
            startActivity(Intent(this@ProdukActivity, UploadProduk::class.java))
            finish()
        }

        data.bUpdate.setOnClickListener {
            startActivity(Intent(this@ProdukActivity, UpdateProduk::class.java))
            finish()
        }

        data.bPrint.setOnClickListener {
            startActivity(Intent(this@ProdukActivity, TampilActivity::class.java))
            finish()
        }

        data.bDelete.setOnClickListener {
            startActivity(Intent(this@ProdukActivity, DeleteProduk::class.java))
            finish()
        }
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}