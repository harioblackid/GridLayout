package com.bmti.gridlayout.crud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bmti.gridlayout.ProdukActivity
import com.bmti.gridlayout.databinding.ActivityUpdateProdukBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateProduk : AppCompatActivity() {
    // Deklarasi Variable

    private lateinit var binding: ActivityUpdateProdukBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdate.setOnClickListener {
            if(binding.refKode.text.isNotEmpty()) {
                val inputnama = binding.inputNama.text
                val inputharga = binding.inputHarga.text
                val inputjumlah = binding.inputJumlah.text

                if(inputnama.isEmpty() or inputharga.isEmpty() or inputjumlah.isEmpty()) {
                    // Get Data
                    readData(binding.refKode.text.toString())

                }
                else {
                    // Set data to all field
                    val referenceKodeBarang     = binding.refKode.text.toString()
                    val updateNamaProduk        = binding.inputNama.text.toString()
                    val updateJumlahProduk      = binding.inputJumlah.text.toString()
                    val updateHargaProduk       = binding.inputHarga.text.toString()
                    updateData(
                        referenceKodeBarang, updateNamaProduk,
                        updateJumlahProduk, updateHargaProduk
                    )
                }

            }
            else {
                Toast.makeText(this, "Masukan kode produk", Toast.LENGTH_SHORT).show()
            }
//
        }

    }

    private fun updateData(
        kodeproduk: String,
        namaproduk: String,
        jumlahproduk: String,
        hargaproduk: String
    ) {
        databaseReference =
            FirebaseDatabase.getInstance().getReference("ProductList")
        val ProdukData = mapOf<String, String>(
            "nama" to namaproduk,
            "jumlah" to jumlahproduk,
            "harga" to hargaproduk,
            "kode" to kodeproduk
        )
        databaseReference.child(kodeproduk).updateChildren(ProdukData).addOnSuccessListener {
            binding.refKode.text.clear()
            binding.inputNama.text.clear()
            binding.inputJumlah.text.clear()
            binding.inputHarga.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this@UpdateProduk, ProdukActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show()
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

                binding.inputNama.setText(namaproduk.toString())
                binding.inputHarga.setText(hargaproduk.toString())
                binding.inputJumlah.setText(jumlahproduk.toString())

                // Change text button
                binding.btnUpdate.text = "UPDATE"
            } else {
                Toast.makeText( this, "Kode barang tidak ada",
                    Toast.LENGTH_SHORT).show()

                // Change text button
                binding.btnUpdate.text = "CARI DATA"
            }
        }.addOnFailureListener{
            Toast.makeText( this, "Something went wrong",
                Toast.LENGTH_SHORT).show()
        }
    }
}