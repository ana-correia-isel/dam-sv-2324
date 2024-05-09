package com.isel.dam.a37726.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ActivityLoginBinding
import com.isel.dam.a37726.pokedex.databinding.ActivityPokemonDetailBinding
import com.isel.dam.a37726.pokedex.model.data.PokemonRegion
import com.isel.dam.a37726.pokedex.ui.viewModels.LoginOrSignViewModel
import com.isel.dam.a37726.pokedex.ui.viewModels.PokemonListViewModel

class LoginActivity : AppCompatActivity() {

    val viewModel: LoginOrSignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.login.setOnClickListener {
            //viewModel.createAccount(binding.username.text.toString(), binding.password.text.toString())
            viewModel.signIn(binding.username.text.toString(), binding.password.text.toString())
        }

        viewModel.isSuccessful.observe(this) {
            if (it) {
                val intent = Intent(
                    this,
                    RegionsActivity::class.java
                )

                startActivity(
                    intent
                )
            }
        }
    }
}