package com.example.instachatcompose.ui.activities.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.instachatcompose.ui.activities.signup.Form
import com.example.instachatcompose.ui.activities.signup.SignUpPage
import com.example.instachatcompose.ui.activities.signup.SignUpProgress
import com.example.instachatcompose.ui.theme.InstaChatComposeTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstaChatComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier= Modifier.fillMaxSize()) {
                        LoginPage(onBackPressed = {
                            onBackPressed()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun LoginPage(onBackPressed: () -> Unit){
    var username by rememberSaveable { mutableStateOf("") }

    Column (
        modifier= Modifier.padding(horizontal = 15.dp)
    ){
        SignUpProgress(onBackPressed ={
            onBackPressed()
        } )
        Form(username = username){
            username = it
        }

    }
}
