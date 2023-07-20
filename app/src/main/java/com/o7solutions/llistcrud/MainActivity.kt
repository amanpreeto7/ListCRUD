package com.o7solutions.llistcrud

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.o7solutions.llistcrud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var array = mutableListOf<String>("Shabnam", "Ameesha", " Sonam", "Shivani")
    lateinit var adapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
        binding.listView.adapter = adapter

        //will be reason of crash
      //  binding.listView.setOnClickListener {}

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Delete or update")
                .setMessage("Do you want to delete or update")
                .setPositiveButton("Delete", {_,_->
                    array.removeAt(position)
                    adapter.notifyDataSetChanged()
                })
                .setNegativeButton("Update", {_,_->
                    showUpdateDialog(position)
                })
                .show()
        }

        binding.listView.setOnItemLongClickListener { parent, view, position, id ->
            array.removeAt(position)
            adapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true
        }

        binding.fab.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.add_item_layout)
            dialog.show()
            var etName = dialog.findViewById<EditText>(R.id.etName)
            var btnAdd = dialog.findViewById<Button>(R.id.btnAdd)

            btnAdd.setOnClickListener {
                if(etName.text.toString().isNullOrEmpty()){
                    etName.error = "Enter your name"
                }else{
                    array.add(etName.text.toString())
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }

        }
    }

    fun showUpdateDialog(position : Int){
        array.set(position, "Anurag")
        adapter.notifyDataSetChanged()
    }
}