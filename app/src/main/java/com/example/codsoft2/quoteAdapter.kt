package com.example.codsoft2

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class quoteAdapter(
    private val context: Context,
    private val quotes: List<String>,
    var interX: x
) : RecyclerView.Adapter<quoteAdapter.QuoteViewHolder>() {

    interface x {
        fun delete(position: Int)
    }

    // ViewHolder class for caching view references
    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        var card: CardView = itemView.findViewById(R.id.crd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        // Inflate the custom item layout
        val view = LayoutInflater.from(context).inflate(R.layout.card, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        // Bind the quote to the TextView
        val quote = quotes[position]
        holder.textView.text = quote

        holder.card.setOnLongClickListener {
            interX.delete(position)
            true
        }

        holder.card.setOnClickListener {
            shareVia(holder.card)
        }
    }

    private fun shareVia(view: View) {
        try {
            val bitmap = getBitmapFromView(view)
            val uri = saveBitmapToFile(bitmap)

            if (uri != null) {
                shareImage(uri)
            } else {
                Toast.makeText(context, "Failed to share content", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri? {
        val fileName = "shared_text.png"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Use FileProvider to get the content URI
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun shareImage(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    override fun getItemCount(): Int = quotes.size
}
