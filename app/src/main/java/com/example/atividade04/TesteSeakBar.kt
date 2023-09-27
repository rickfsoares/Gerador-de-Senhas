package com.example.atividade04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class TesteSeakBar : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var quantidadeCaracteres: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teste_seak_bar)
        this.seekBar = findViewById(R.id.seekBar)
        this.quantidadeCaracteres = findViewById(R.id.textView10)

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
}