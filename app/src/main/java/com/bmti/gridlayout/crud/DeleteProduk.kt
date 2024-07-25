package com.bmti.gridlayout.crud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bmti.gridlayout.ProdukActivity
import com.bmti.gridlayout.R
import com.bmti.gridlayout.databinding.ActivityDeleteProdukBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteProduk : AppCompatActivity() {
    // Deklarasi variable

    private lateinit var dataBind: ActivityDeleteProdukBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBind = ActivityDeleteProdukBinding.inflate(layoutInflater)
        setContentView(dataBind.root)

        dataBind.btnDelete.setOnClickListener {
            val getKode = dataBind.refKode.text.toString()

            // Cek input code
            if(getKode.isNotEmpty()) {
                //TODO Execute deleteFunction
                deleteData(getKode)
            }
            else {
                Toast.makeText(this, "Masukan kode produk terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteData(kodeProduk: String){
        val databaseRef = FirebaseDatabase.getInstance().getReference("ProductList")
        databaseRef.child(kodeProduk).removeValue().addOnSuccessListener {
            dataBind.refKode.text.clear()
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT)
                .show()

            startActivity(Intent(this@DeleteProduk, ProdukActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT)
                .show()
        }
    }
}