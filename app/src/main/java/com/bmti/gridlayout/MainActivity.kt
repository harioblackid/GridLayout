package com.bmti.gridlayout

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    private lateinit var cHome: CardView
    private lateinit var cPDF: CardView
    private lateinit var cBrowser: CardView
    private lateinit var cInfo: CardView
    private lateinit var cProduk: CardView
    private lateinit var cSiswa: CardView
    private lateinit var cSurvey: CardView
    private lateinit var cGallery: CardView
    private lateinit var cExit: CardView

    val urlSekolah: String = "https://smkpgritelagasari1.sch.id"
    val urlWA: String =
        "https://wa.me/+6282123622435?text=Hallo, saya perlu bantuan anda segera mohon segera dilaksanakan, terima kasih"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ID from layout
        cHome = findViewById(R.id.cardHome)
        cPDF = findViewById(R.id.cardPDF)
        cBrowser = findViewById(R.id.cardBrowser)
        cInfo = findViewById(R.id.cardInfo)
        cProduk = findViewById(R.id.cardProduk)
        cSiswa = findViewById(R.id.cardSiswa)
        cSurvey = findViewById(R.id.cardSurvey)
        cGallery = findViewById(R.id.cardGallery)
        cExit = findViewById(R.id.cardExit)

        cHome.setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        cPDF.setOnClickListener {
            startActivity(Intent(this@MainActivity, PdfActivity::class.java))
        }

        cBrowser.setOnClickListener {
            val openWeb = Intent()
                .setAction(Intent.ACTION_VIEW)
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .setData(Uri.parse(urlSekolah))
            startActivity(openWeb)
        }

        cInfo.setOnClickListener() {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Bantuan")
            builder.setMessage("Perlu bantuan?")

            builder.setPositiveButton("OK") { dialog, which ->
                val openWeb = Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(urlWA))
                startActivity(openWeb)

            }
            builder.setNegativeButton("Batal") { dialog, which ->
                dialog.cancel()
            }
            builder.show()
        }
        cProduk.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProdukActivity::class.java))
        }

//        Logout
        cExit.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Keluar")
            builder.setMessage("Anda yakin ingin keluar aplikasi?")

            builder.setPositiveButton("OK") { dialog, which ->
                finishAndRemoveTask()
            }

            builder.setNegativeButton("Batal") { dialog, which ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    //Create method appInstalledOrNot
    private fun appInstalledOrNot(url: String): Boolean {
        val packageManager = packageManager
        var appInstalled: Boolean
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES)
            appInstalled = true
        } catch (e: PackageManager.NameNotFoundException) {
            appInstalled = false
        }
        return appInstalled
    }
}