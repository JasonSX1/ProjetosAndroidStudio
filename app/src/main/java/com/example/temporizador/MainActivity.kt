package com.example.temporizador

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null //iniciada como nula para que ela aguarde a entrada do campo de texto para começar seu funcionamento como timer

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
                val number = editText.text.toString().toLong() //Utilizamos long para trabalhar com milessegundos, que são numeros grandes

                timer = object: CountDownTimer(number * 60 * 1000, 1000) {
                    //a operação de number * 60 & 1000 serve para converter o tempo em que o metodo CountDownTime trabalha de minutos para milessegundos
                    override fun onTick(millisUntilFinished: Long) {
                        var seconds = millisUntilFinished / 1000
                        var minutes = seconds / 60
                        seconds = seconds % 60
                        result.text = String.format("%02d:%02d", minutes, seconds)
                    }

                    override fun onFinish() {
                        result.text = "O tempo acabou!"
                    }
                }

                timer?.start()

            } catch (e: NumberFormatException){
                Toast.makeText(this,  "Digite um úmero no campo de texto!", Toast.LENGTH_SHORT).show()
            }



            buttonStop.setOnClickListener(){
                timer?.cancel()
            }
        }
    }
}