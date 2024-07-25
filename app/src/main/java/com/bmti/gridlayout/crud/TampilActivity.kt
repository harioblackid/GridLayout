package com.bmti.gridlayout.crud

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bmti.gridlayout.R
import com.bmti.gridlayout.databinding.ActivityTampilBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator

// QR Library
import com.google.zxing.integration.android.IntentResult

class TampilActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding:ActivityTampilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTampilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Event tombol cari
        binding.btnSearch.setOnClickListener{
            val searchkodebarang: String = binding.editSearch.text.toString()
            if (searchkodebarang.isNotEmpty()){
                readData(searchkodebarang)
            } else {
                Toast.makeText( this, "masukkan kode barang yang tepat", Toast.LENGTH_SHORT).show()
            }
        }

        // Event tombol Scan QR
        binding.btnQR.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setBeepEnabled(true)
            integrator.setPrompt("Arahkan ke QR Code untuk Scan")
            integrator.setOrientationLocked(false)
            integrator.initiateScan()
        }

    }

    private fun readData(kodeproduk: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductList")
        databaseReference.child(kodeproduk).get().addOnSuccessListener {
            if (it.exists()){
                val namaproduk = it.child("nama").value
                val jumlahproduk = it.child("jumlah").value
                val hargaproduk = it.child("harga").value
                Toast.makeText( this, "Result Found",
                    Toast.LENGTH_SHORT).show()
                binding.editSearch.text.clear()
                binding.txtNamaProduk.text = namaproduk.toString()
                binding.txtJumlahProduk.text = jumlahproduk.toString()
                binding.txtHargaProduk.text = hargaproduk.toString()
            } else {
                Toast.makeText( this, "Kode barang tidak ada",
                    Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText( this, "Something went wrong",
                Toast.LENGTH_SHORT).show()
        }
    }

    // Scan QR
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            var hasil = result.contents
            var _tvHasil = findViewById<EditText>(R.id.editSearch)
            _tvHasil.setText(hasil.toString())
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}