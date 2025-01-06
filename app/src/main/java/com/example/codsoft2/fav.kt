package com.example.codsoft2

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codsoft2.databinding.ActivityFavBinding

class fav : AppCompatActivity()  , quoteAdapter.x{

    private lateinit var binding: ActivityFavBinding
    private var quotesList = ArrayList<String>() // List to hold quotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the saved quotes from SharedPreferences when the activity starts
        loadSavedQuotes()

        // Retrieve the quote from the intent and add it to the list if it's not already there
        val receivedQuote = intent.getStringExtra("quote")
        if (receivedQuote != null) {
            // If the quote is not already in the list, add it
            if (!quotesList.contains(receivedQuote)) {
                quotesList.add(receivedQuote)
                saveQuote(quotesList)  // Save the updated list
            }
        }

        // Set up the RecyclerView with the quotes list
        binding.rec.apply {
            layoutManager = LinearLayoutManager(this@fav)
            adapter = quoteAdapter(this@fav, quotesList ,this@fav)
        }

        displaySavedImageAndColor()

        binding.floatingActionButton.setOnClickListener {
            openAddAlertDialog();
        }
    }



    @SuppressLint("MissingInflatedId")
    private fun openAddAlertDialog() {
        // Create a custom layout for the dialog with EditText fields for quote and author
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_quote, null)

        // Get references to the EditText fields for the quote and author
        val quoteEditText = dialogView.findViewById<EditText>(R.id.quoteEditText)
        val authorEditText = dialogView.findViewById<EditText>(R.id.authorEditText)

        // Create the AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add a Quote")
            .setView(dialogView) // Set the custom layout
            .setPositiveButton("OK") { _, _ ->
                // Get the text inputted by the user
                val quote = quoteEditText.text.toString()
                val author = authorEditText.text.toString()

                // Check if both fields are not empty
                if (quote.isNotEmpty() && author.isNotEmpty()) {
                    // Create a formatted string with the quote and author
                    val fullQuote = "\"$quote\" - $author"
                    // Add the new quote to the list
                    quotesList.add(fullQuote)
                    saveQuote(quotesList) // Save the updated list
                    binding.rec.adapter?.notifyDataSetChanged() // Refresh the RecyclerView
                    Toast.makeText(this, "Quote added", Toast.LENGTH_SHORT).show()
                } else {
                    // Show a message if any field is empty
                    Toast.makeText(this, "Please fill both fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog if the user cancels
            }
            .create()

        // Show the dialog
        dialog.show()
    }

    // Save all quotes to SharedPreferences
    private fun saveQuote(quotesList: ArrayList<String>) {
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Convert the list of quotes into a single string and save it
        val quotesString = quotesList.joinToString(";") // Use a separator to store multiple quotes
        editor.putString("quotesList", quotesString)
        editor.apply()
    }

    // Load all saved quotes from SharedPreferences
    private fun loadSavedQuotes() {
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        // Retrieve the saved quotes list as a single string
        val savedQuotes = sharedPreferences.getString("quotesList", "")

        if (!savedQuotes.isNullOrEmpty()) {
            // Split the string into individual quotes and load them into the list
            quotesList = ArrayList(savedQuotes.split(";"))
            // Update the RecyclerView adapter to reflect the changes
            binding.rec.adapter = quoteAdapter(this@fav, quotesList ,this)
        }
    }

    private fun displaySavedImageAndColor() {
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val savedImage = sharedPreferences.getInt(
            "selectedImage",
            R.drawable.ba10 // Replace with your default image
        )
        val savedColor = sharedPreferences.getString(
            "selectedColor",
            "#FAF4E6" // Default color
        )
        binding.main.setBackgroundColor(Color.parseColor(savedColor))
    }

    override fun delete(position: Int) {
        // Display a confirmation dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Quote")
            .setMessage("Are you sure you want to delete this quote?")
            .setPositiveButton("Yes") { _, _ ->
                try {// If the user confirms, delete the quote
                    quotesList.removeAt(position)
                    saveQuote(quotesList)
                    binding.rec.adapter?.notifyItemRemoved(position)
                    Toast.makeText(this, "Quote deleted", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No") { dialog, _ ->
                // If the user cancels, dismiss the dialog
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

}
