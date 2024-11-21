package com.jeison.myapplication

import java.text.DecimalFormat // Cambia esta importación
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val SUMA = "+"
    val RESTA = "-"
    val MULTIPLICACION = "*"
    val DIVISION = "/"
    val PORCENTAJE = "%"

    var operacionActual = ""
    var primerNumero: Double = Double.NaN
    var segundoNumero: Double = Double.NaN

    lateinit var tvTemp: TextView
    lateinit var tvResult: TextView

    lateinit var formatoDecimal: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar DecimalFormat
        formatoDecimal = DecimalFormat("#.####")
        tvTemp = findViewById(R.id.tvTemp)
        tvResult = findViewById(R.id.tvResult)

    }


    fun cambiarOperador(b: View) {
        val boton: Button = b as Button

        if (tvTemp.text.isNotEmpty() || !primerNumero.isNaN()) {
            if (tvTemp.text.isNotEmpty()) {
                calcular() // Si hay un número pendiente, calcula primero
            }

            operacionActual = when (boton.text.toString().trim()) {
                "÷" -> "/"
                "x" -> "*"
                else -> boton.text.toString().trim()
            }

            // Usa el resultado previo como primerNumero para la próxima operación
            tvResult.text = formatoDecimal.format(primerNumero) + operacionActual
            tvTemp.text = ""
        }
    }



    fun calcular() {
        try {
            if (!primerNumero.isNaN()) {
                if (tvTemp.text.toString().isNotEmpty()) {
                    segundoNumero = tvTemp.text.toString().toDouble()

                    when (operacionActual) {
                        "+" -> primerNumero += segundoNumero
                        "-" -> primerNumero -= segundoNumero
                        "*" -> primerNumero *= segundoNumero
                        "/" -> {
                            if (segundoNumero != 0.0) {
                                primerNumero /= segundoNumero
                            } else {
                                tvResult.text = "Error"
                                return
                            }
                        }
                        "%" -> primerNumero %= segundoNumero
                    }

                    tvTemp.text = ""
                    segundoNumero = Double.NaN // Reinicia segundoNumero después del cálculo
                }
            } else {
                if (tvTemp.text.toString().isNotEmpty()) {
                    primerNumero = tvTemp.text.toString().toDouble()
                    tvTemp.text = ""
                }
            }
        } catch (e: Exception) {
            tvResult.text = "Error"
        }
    }


    fun seleccionarNumero(b:View){

    val boton:Button = b as Button
if(tvTemp.text.toString()=="0"){

    tvTemp.text=""
}
tvTemp.text = tvTemp.text.toString() + boton.text.toString()

}

    fun igual(b: View) {
        if (operacionActual.isNotEmpty() && tvTemp.text.toString().isNotEmpty()) {
            calcular()
            tvResult.text = formatoDecimal.format(primerNumero)
            // Reiniciar el operador pero mantener el primerNumero para encadenar
            operacionActual = ""
        }
    }



    fun borrar(b:View){



        val boton:Button = b as Button

        if(boton.text.toString().trim()=="C") {

           if(tvTemp.text.toString().isNotEmpty()){

           var datosActuales: CharSequence= tvTemp.text as CharSequence
               tvTemp.text= datosActuales.subSequence(0,datosActuales.length-1)
           }else{

               primerNumero=Double.NaN
               segundoNumero=Double.NaN
               tvTemp.text=""
               tvResult.text=""
           }

        }else if(boton.text.toString().trim()=="CA") {

            primerNumero=Double.NaN
            segundoNumero=Double.NaN
            tvTemp.text=""
            tvResult.text=""

        }


    }



}


