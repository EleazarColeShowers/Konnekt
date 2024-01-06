package com.example.instachatcompose.ui.activities.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instachatcompose.R
import com.example.instachatcompose.ui.theme.InstaChatComposeTheme

class SignUpActivity: ComponentActivity() {
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
                        SignUpPage(onBackPressed = {
                            onBackPressed()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpPage(onBackPressed: () -> Unit){
    Column (
        modifier= Modifier.padding(horizontal = 15.dp)
            ){
        SignUpProgress(onBackPressed ={
             onBackPressed()
        } )
        Form()

    }
}

@Composable
fun SignUpProgress(onBackPressed: () -> Unit){
    val returnArrow= painterResource(id = R.drawable.returnarrow)
    val goodProgress= painterResource(id = R.drawable.bluerectangle)
    val noProgress= painterResource(id = R.drawable.whiterectangle)
    Spacer(modifier = Modifier.height(15.dp))
    Row(modifier= Modifier
        .fillMaxWidth()
       ) {

        Image(painter =returnArrow ,
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    onBackPressed()
                }
        )
        Spacer(modifier = Modifier.width(30.dp))
        Image(
            painter = goodProgress,
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(65.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = noProgress,
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(65.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = noProgress,
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(65.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = noProgress,
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(65.dp)
        )
        
    }
}

@Composable
fun Form(){
    val context = LocalContext.current
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val emailIcon= painterResource(id = R.drawable.emailicon)
    val passwordIcon= painterResource(id = R.drawable.passwordseen)
    Column() {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Create An Account",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF050907),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Let's help you create an account",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF696969),
                )
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Username",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF050907),
                    )
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x33333333),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .height(48.dp)
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = username,
                    onValueChange = { username = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 14.dp)
                )

                if (username.isEmpty()) {
                    Text(
                        text = "Enter your username here",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 24.dp,top=14.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF050907),
                )
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x33333333),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .height(48.dp)
                    .fillMaxWidth()
            ) {

                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 14.dp)
                )

                if (email.isEmpty()) {
                    Text(
                        text = "Enter your email address",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 24.dp,top=14.dp)
                    )
                }

                Image(
                    painter = emailIcon,
                    contentDescription = "Email",
                    modifier=Modifier.padding(start = 340.dp, top= 14.dp)

                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF050907),
                )
            )
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x33333333),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .height(48.dp)
                    .fillMaxWidth()
            ) {

                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 14.dp)
                )

                if (email.isEmpty()) {
                    Text(
                        text = "Enter your password",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 24.dp,top=14.dp)
                    )
                }

                Image(
                    painter = passwordIcon,
                    contentDescription = "Password",
                    modifier=Modifier.padding(start = 340.dp, top= 14.dp)

                )
            }
        }
       Spacer(modifier = Modifier.height(16.5.dp))
        Row {
            CustomCheckbox()
            Spacer(modifier = Modifier.width(6.dp))
            TermsAndConditionsText()
        }
        Spacer(modifier = Modifier.height(44.5.dp))
        ContinueBtn(onClick ={
                val intent = Intent(context, SignUpActivity::class.java)
                context.startActivity(intent)
            })

    }
}

@Composable
fun TermsAndConditionsText() {
    val context = LocalContext.current
    val termsAndConditions = "Terms & Conditions"
    val text = buildAnnotatedString {
        append("I agree to the ")
        pushStyle(
            style = SpanStyle(
                color = Color(android.graphics.Color.parseColor("#2F9ECE")), // Change color here
                textDecoration = TextDecoration.Underline
            )
        )
        append(termsAndConditions)
        pop()
        append(" of this App")
    }

    ClickableText(text = text, onClick = {
        val startIndex = text.indexOf(termsAndConditions)
        val endIndex = startIndex + termsAndConditions.length
        if (it in startIndex..endIndex) {
            // Handle click action here if needed
            val intent = Intent(context, TermsAndConditions::class.java)
            context.startActivity(intent)
            // For example, navigate to terms and conditions screen
        }
    })
}

@Composable
fun CustomCheckbox() {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { checked = !checked }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(20.dp)
                .border(
                    width = 1.dp,
                    color = Color(android.graphics.Color.parseColor("#2F9ECE")), // Change color here
                    shape = RoundedCornerShape(4.dp)
                )
                .background(
                    color = if (checked) Color(android.graphics.Color.parseColor("#2F9ECE")) else Color.White, // Change color on check
                    shape = RoundedCornerShape(4.dp)
                )
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ContinueBtn(onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(25.dp), // Adjust the corner radius as needed
        color = Color(0xFF2F9ECE), // Change the background color as needed
        modifier = Modifier
            .height(54.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Text(
                text = "Continue",
                color = Color(0xFFFFFFFF), // Change the text color as needed
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

