package com.example.atividade04

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var gerenciador: Gerenciador
    private lateinit var lista: ListView
    private lateinit var button: FloatingActionButton
    private lateinit var adapter: ArrayAdapter<Senha>
    private lateinit var clipboard: ClipboardManager
    private var elemEdit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        this.gerenciador = Gerenciador()
        this.lista = findViewById(R.id.list_item)
        this.button = findViewById(R.id.adicionar)
        this.adapter = ArrayAdapter<Senha>(this, android.R.layout.simple_list_item_1, this.gerenciador.senhas)
        this.lista.adapter = adapter



        this.lista.onItemLongClickListener = CopiarSenha()
        val novaSenha = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data: Intent? = it.data
                val senha = getSerializableActivityResult(it, "senha", Senha::class.java)
                (this.lista.adapter as ArrayAdapter<Senha>).add(senha)

            }
        }

        val edicao = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data?.hasExtra("deletar") == true) {
                    this.gerenciador.senhas.removeAt(elemEdit)

                } else if (it.data?.hasExtra("alterar") == true) {
                    val data: Intent? = it.data
                    val senha = getSerializableActivityResult(it, "senha", Senha::class.java)

                    this.gerenciador.senhas[elemEdit] = senha

                }
                (this.lista.adapter as ArrayAdapter<Senha>).notifyDataSetChanged()
            }
        }

        this.lista.setOnItemClickListener { adapterView: AdapterView<*>, view2: View, i: Int, l: Long ->
            var intent = Intent(this, NovaSenha::class.java).apply {
                putExtra("tipoTela", "edicao")
                putExtra("senha", this@MainActivity.gerenciador.senhas[i])
            }
            edicao.launch(intent)
            Log.i("app", intent.getStringExtra("tipoTela")!!)
        }

        this.button.setOnClickListener{
            var intent = Intent(this, NovaSenha::class.java).apply {
                putExtra("tipoTela", "criacao")
            }
            Log.i("app", intent.getStringExtra("tipoTela")!!)

            novaSenha.launch(
                intent
            )
        }
    }

    fun <T : Serializable?> getSerializableActivityResult(activity: ActivityResult, name: String, clazz: Class<T>): T
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.data?.getSerializableExtra(name, clazz)!!
        else
            activity.data?.getSerializableExtra(name) as T
    }

    inner class CopiarSenha: OnItemLongClickListener {
        override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long): Boolean {
            val senha = this@MainActivity.gerenciador.senhas.get(p2).getSenha()
            val clip = ClipData.newPlainText(MIMETYPE_TEXT_PLAIN, senha)
            this@MainActivity.clipboard.setPrimaryClip(clip)
            return true

        }
    }
}