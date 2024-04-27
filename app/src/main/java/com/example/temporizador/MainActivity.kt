package com.example.temporizador

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null // Iniciada como nula para que ela aguarde a entrada do campo de texto para começar seu funcionamento como timer
    private var timerRunning = false // Variavel de controle criada para verificar se o código está rodando ou não
    private var pauseOffset: Long = 0 // Variável que armazena o tempo restante ao pausar
    private var timeLeftInMillis: Long = 0 // Variável que armazena o tempo restante em milissegundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.edit_value)
        val buttonStart: Button = findViewById(R.id.btn_start)
        val buttonStop: Button = findViewById(R.id.btn_stop)
        val result: TextView = findViewById(R.id.txt_result)
        val buttonPause: Button = findViewById(R.id.btn_pause)

        buttonStart.setOnClickListener{
            try{
                val number = editText.text.toString().toLong() // Utilizamos long para trabalhar com milessegundos, que são numeros grandes
                var timeLeftInMillis: Long = number * 60 * 1000

                timer = object: CountDownTimer(number * 60 * 1000, 1000) {
                    // A operação de number * 60 & 1000 serve para converter o tempo em que o metodo CountDownTime trabalha de minutos para milessegundos
                    override fun onTick(millisUntilFinished: Long) {
                        var seconds = millisUntilFinished / 1000
                        var minutes = seconds / 60
                        seconds = seconds % 60
                        result.text = String.format("%02d:%02d", minutes, seconds)
                        timeLeftInMillis = millisUntilFinished // Armazena o tempo restante a cada tick
                    }

                    override fun onFinish() {
                        result.text = "O tempo acabou!"
                    }
                }

                timer?.start()
                timerRunning = true


            } catch (e: NumberFormatException){
                Toast.makeText(this,  "Digite um úmero no campo de texto!", Toast.LENGTH_SHORT).show()
            }

            buttonStop.setOnClickListener(){
                timer?.cancel()
                timerRunning = false
            }

            buttonPause.setOnClickListener(){
                pauseTimer()
            }
        }
    }

    fun pauseTimer(){
        if(timerRunning){
            timer?.cancel()
            timerRunning = false
            pauseOffset = timeLeftInMillis
        }
    }
}
