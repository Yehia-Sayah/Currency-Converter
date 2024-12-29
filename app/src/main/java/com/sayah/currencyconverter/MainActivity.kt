package com.sayah.currencyconverter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val Usdt = "USDT"
    private val Ethruem = "ETH"
    private val Rubl = "XRP"
    private val Binance = "BNB"
    private val Bitcoin = "BTC"


   lateinit var toDropDownMenu : AutoCompleteTextView
   lateinit var fromDropDownMenu : AutoCompleteTextView
   lateinit var Get_Amount :TextInputEditText
   lateinit var Set_Amount : TextInputEditText
   lateinit var actionbar : Toolbar


   val values = mapOf(
       Usdt    to 1.0 ,
       Ethruem to 2.740 ,
       Rubl    to 0.73 ,
       Binance to 246.25 ,
       Bitcoin to 37.575
   )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        initializeViews()
        populateDropDownMenu()
        actionbar.inflateMenu(R.menu.options_menu)
        actionbar.setOnMenuItemClickListener{menuItem ->

            if (menuItem.itemId == R.id.share_action){
                val massage = "${Get_Amount.text} ${fromDropDownMenu.text} Equals ${Set_Amount.text} ${toDropDownMenu.text}"
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT,massage)
            if (shareIntent.resolveActivity(packageManager) != null){
              startActivity(shareIntent)
          }
            }

            if (menuItem.itemId == R.id.contact_action){

                val massage_contact = Intent(Intent.ACTION_DIAL)
                massage_contact.data = Uri.parse("tel:01009209796")

                 startActivity(massage_contact)

            }

            if (menuItem.itemId == R.id.Exite){
                finish()
            }

            true
        }



        Get_Amount.addTextChangedListener {
            calculatResult()
        }
        fromDropDownMenu.setOnItemClickListener { parent, view, position, id ->
            calculatResult()
        }
        toDropDownMenu.setOnItemClickListener { parent, view, position, id ->
            calculatResult()
        }




    }


    private  fun populateDropDownMenu(){
        val listOfCurrncye = arrayOf(Usdt,Bitcoin,Ethruem,Rubl,Binance)
        val adapter = ArrayAdapter(this,R.layout.drop_down_list_item,listOfCurrncye)
        toDropDownMenu.setAdapter(adapter)
        fromDropDownMenu.setAdapter(adapter)

    }




    private fun initializeViews(){
        Get_Amount       = findViewById(R.id.get_amount)
        Set_Amount       = findViewById(R.id.set_Result)
        toDropDownMenu   = findViewById(R.id.to_Currncye)
        fromDropDownMenu = findViewById(R.id.from_Currency_menu)
        actionbar        = findViewById(R.id.my_toolbar)


    }




    private fun calculatResult(){
        if (Get_Amount.text.toString().isNotEmpty() && Get_Amount.text.toString()!= "0" ){
            var amount = Get_Amount.text.toString().toDouble()
            var ToCurrncy = values[fromDropDownMenu.text.toString()]
            var FromCurrncy = values[toDropDownMenu.text.toString()]
            var result = amount.times(ToCurrncy!!.div(FromCurrncy!!))
            val formatResult = String.format("%.3f",result)

            Set_Amount.setText(formatResult)

        } else if (Get_Amount.text.toString().isNullOrEmpty() && Get_Amount.text.toString() != "0"){
            Get_Amount.setError("amount filed require")
            val snak = Snackbar.make(Get_Amount,"Please Enter Amount",Snackbar.LENGTH_SHORT)
            snak.show()
            snak.setAction("OK"){

            }
        }

        else {
            Get_Amount.setError("Not receive Zero")
            val snak = Snackbar.make(Get_Amount,"Please enter a valid number",Snackbar.LENGTH_SHORT)
            snak.show()
            snak.setAction("OK"){

            }
        }
    }




 }