package com.devjucelio.whatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.devjucelio.whatsapp.databinding.ActivityLoginBinding
import com.devjucelio.whatsapp.databinding.ActivityMainBinding
import com.devjucelio.whatsapp.utils.exibirMensagem


class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate( layoutInflater )
    }

    private lateinit var email: String
    private lateinit var senha: String

    //Firebase
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )
        inicializarEventosClique()
        //firebaseAuth.signOut()

    }

    override fun onStart() {
        super.onStart()
        verificarUsuarioLogado()
    }

    private fun verificarUsuarioLogado() {
        val usuarioAtual = firebaseAuth.currentUser
        if( usuarioAtual != null ){
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    private fun inicializarEventosClique() {
        binding.textCadastro.setOnClickListener {
            startActivity(
                Intent(this, CadastroActivity::class.java)
            )
        }
        binding.btnLogar.setOnClickListener {
            if( validarCampos() ){
                logarUsuario()
            }
        }

    }

    private fun logarUsuario() {

        firebaseAuth.signInWithEmailAndPassword(
            email, senha
        ).addOnSuccessListener {
            exibirMensagem("Logado com sucesso!")
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }.addOnFailureListener { erro ->

            try {
                throw erro
            }catch ( erroUsuarioInvalido: FirebaseAuthInvalidUserException){
                erroUsuarioInvalido.printStackTrace()
                exibirMensagem("E-mail não cadastrado")
            }catch ( erroCredenciaisInvalidas: FirebaseAuthInvalidCredentialsException){
                erroCredenciaisInvalidas.printStackTrace()
                exibirMensagem("E-mail ou senha estão incorretos!")
            }

        }

    }

    private fun validarCampos(): Boolean {

        email = binding.editLoginEmail.text.toString()
        senha = binding.editLoginSenha.text.toString()

        if( email.isNotEmpty() ){//Não está vazio

            binding.textInputLayoutLoginEmail.error = null
            if( senha.isNotEmpty() ){
                binding.textInputLayoutLoginSenha.error = null
                return true
            }else{
                binding.textInputLayoutLoginSenha.error = "Preencha o e-mail"
                return false
            }

        }else{//Está vazio
            binding.textInputLayoutLoginEmail.error = "Preencha o e-mail"
            return false
        }
    }
}