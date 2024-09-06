package com.devjucelio.whatsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devjucelio.whatsapp.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializarToollbar()
    }

    private fun inicializarToollbar() {
       val toolbar = binding.includeToolbar.tbPrincipal
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Faça o seu Cadastro"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}