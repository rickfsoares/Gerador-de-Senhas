package com.example.atividade04

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import java.io.Serializable
import kotlin.random.Random

class NovaSenha : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var quantidadeCaracteres: TextView
    private lateinit var checkBoxMaiusculo: CheckBox
    private lateinit var checkBoxNumerico: CheckBox
    private lateinit var checkBoxSimbolo: CheckBox
    private lateinit var descricao: EditText
    private lateinit var gerarSenha: Button
    private lateinit var cancelar: Button
    private lateinit var alterar: Button
    private lateinit var excluir: Button
    private lateinit var titulo: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_senha)

        val intent = intent


        this.seekBar = findViewById(R.id.seekBar2)
        this.quantidadeCaracteres = findViewById(R.id.textView4)

        this.checkBoxMaiusculo = findViewById(R.id.checkBox)
        this.checkBoxNumerico = findViewById(R.id.checkBox2)
        this.checkBoxSimbolo = findViewById(R.id.checkBox3)

        this.descricao = findViewById(R.id.editTextText)

        this.gerarSenha = findViewById(R.id.button)

        this.cancelar = findViewById(R.id.button2)
        this.alterar = findViewById(R.id.button3)
        this.excluir = findViewById(R.id.button4)
        this.titulo = findViewById(R.id.textView)


        this.cancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        this.excluir.setOnClickListener {
            val intent = Intent().apply {
                putExtra("deletar", true)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        var senha = getSerializable(this, "senha", Senha::class.java)

        this.alterar.setOnClickListener {
            if(this.descricao.text.toString() != senha.getDescricao()) {
                senha.setDescricao(this.descricao.text.toString())
            }

            if(this.seekBar.progress != senha.getSenha().length ||
                this.checkBoxMaiusculo.isChecked != senha.getTemMaiusculo() ||
                this.checkBoxNumerico.isChecked != senha.getTemNumerico() ||
                this.checkBoxSimbolo.isChecked != senha.getTemSimbolo()) {
                senha.setSenha(this.gerarSenha())

                Log.i("app", senha.getSenha())
            }

            val intent = Intent().apply {
                putExtra("alterar", true)
                putExtra("senha", senha)

            }



            setResult(RESULT_OK, intent)
            finish()
        }


        this.gerarSenha.setOnClickListener(GerarSenha())


        if (intent.getStringExtra("tipoTela").equals("criacao")) {
            this.titulo.text = "Nova Senha"
            this.alterar.visibility = View.GONE
            this.excluir.visibility = View.GONE

        }
        else {
            this.gerarSenha.visibility = View.GONE
            this.titulo.text = "Alterar Senha"
            this.descricao.setText(senha.getDescricao())
            this.seekBar.progress = senha.getSenha().length
            this.quantidadeCaracteres.text = senha.getSenha().length.toString()
            this.checkBoxMaiusculo.isChecked = senha.getTemMaiusculo()
            this.checkBoxNumerico.isChecked = senha.getTemNumerico()
            this.checkBoxSimbolo.isChecked = senha.getTemSimbolo()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Atualize o valor do TextView com o valor atual do SeekBar
                quantidadeCaracteres.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Este método é chamado quando o usuário toca no SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Este método é chamado quando o usuário solta o dedo do SeekBar
            }
        })

    }

    fun gerarSenha(): String {
        val maiusculo =  this.checkBoxMaiusculo.isChecked
        val numerico = this.checkBoxNumerico.isChecked
        val simbolo = this.checkBoxSimbolo.isChecked
        val upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numberChars = "0123456789"
        val specialChars = "!@#$%^&*()_-+=<>?/[]{}|"

        val allowedChars = StringBuilder()

        if (maiusculo) allowedChars.append(upperCaseChars)
        if (numerico) allowedChars.append(numberChars)
        if (simbolo) allowedChars.append(specialChars)

        val random = Random.Default
        var result = (1..this@NovaSenha.seekBar.progress)
            .map { allowedChars[random.nextInt(allowedChars.length)] }
            .joinToString("")

        return result

    }

    fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.intent.getSerializableExtra(name, clazz)!!
        else
            activity.intent.getSerializableExtra(name) as T
    }

    inner class GerarSenha: OnClickListener {
        override fun onClick(p0: View?) {
            var result = this@NovaSenha.gerarSenha()

            val descricao = this@NovaSenha.descricao.text.toString()

            var senha = Senha(result, descricao,
                this@NovaSenha.checkBoxMaiusculo.isChecked,
                this@NovaSenha.checkBoxNumerico.isChecked,
                this@NovaSenha.checkBoxSimbolo.isChecked)


            val intent = Intent().apply {
                putExtra("senha", senha)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
