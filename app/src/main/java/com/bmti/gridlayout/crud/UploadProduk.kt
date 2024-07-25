package com.bmti.gridlayout.crud

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bmti.gridlayout.ProdukActivity
import com.bmti.gridlayout.R
import com.bmti.gridlayout.data.ProductList
import com.bmti.gridlayout.databinding.ActivityUploadProdukBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.util.EnumMap

class UploadProduk : AppCompatActivity() {
    // Deklarasi variable
    private lateinit var binding: ActivityUploadProdukBinding
    private lateinit var dataReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Generate QR Button
        binding.btnQR.setOnClickListener {
            // generateQR(String: editKode)
            val hasilGenerate = generateQR(binding.editKode.text.toString())

            // Buat image QR hasil generate ke ImageView
            binding.imageQR.setImageBitmap(hasilGenerate)

            // Save image QR ke Gallery
            // saveToGallery(context, imageQR: Bitmap, filename: String)
            saveToGallery(this, hasilGenerate!!, "QR1")
        }

        binding.btnSimpan.setOnClickListener {
            // Terima input dari user ke variable
            val kodeProduk = binding.editKode.text.toString()
            val namaProduk = binding.editUploadNama.text.toString()
            val jumlahProduk = binding.editJumlahPoduk.text.toString()
            val hargaProduk = binding.editJumlahPoduk.text.toString()

            // Validasi input produk
            if(kodeProduk.isEmpty() or namaProduk.isEmpty() or jumlahProduk.isEmpty() or hargaProduk.isEmpty()) {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show()
            }
            else {
                // Fungsi untuk menampung data
                val barangData = ProductList(kodeProduk, namaProduk, jumlahProduk, hargaProduk)

                // Buka koneksi database
                // getReference("namaClassData")
                dataReference = FirebaseDatabase.getInstance().getReference("ProductList")
                dataReference.child(kodeProduk).setValue(barangData).addOnSuccessListener {
                    binding.editKode.text.clear()
                    binding.editUploadNama.text.clear()
                    binding.editJumlahPoduk.text.clear()
                    binding.editHarga.text.clear()

                    Toast.makeText(this, "SIMPAN", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@UploadProduk, ProdukActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Generate QR Code
    fun generateQR(isiData : String) : Bitmap? {
        val bitMatrix: BitMatrix = try {
            val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            MultiFormatWriter().encode(
                isiData,
                BarcodeFormat.QR_CODE,
                600, 600,
                hints
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val qrCodeWidth = bitMatrix.width
        val qrCodeHeight = bitMatrix.height
        val datapixels = IntArray(qrCodeWidth * qrCodeHeight)
        for (y in 0 until qrCodeHeight) {
            val offset = y * qrCodeWidth
            for (x in 0 until qrCodeWidth) {
                datapixels[offset + x] = if (bitMatrix[x, y]) {
                    resources.getColor(R.color.black, theme)
                } else {
                    resources.getColor(R.color.white, theme)
                }
            }
        }
        val bitmap = Bitmap.createBitmap(qrCodeWidth, qrCodeHeight, Bitmap.Config.RGB_565)
        bitmap.setPixels(datapixels, 0,qrCodeWidth, 0,0,qrCodeWidth,qrCodeWidth)
        return bitmap
    } // End Generate QR Code

    // Save to Gallery
    fun saveToGallery(context : Context, bitmap: Bitmap, filename:
    String) {
        val contentVal = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES)
            }
        }
        val resolver = context.contentResolver
        val uri =
            resolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentVal)
        uri.let {
            if (uri != null) {
                resolver.openOutputStream(uri).use {outputStream ->
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                            outputStream)
                    }
                }
            }
        }
    } // End Save Gallery
}