package com.example.instachatcompose.ui.activities.mainpage

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.instachatcompose.R
import com.example.instachatcompose.ui.theme.InstaChatComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageActivity: ComponentActivity() {
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
                        MessagePage()
                    }
                }
            }
        }
    }
}

@Composable
fun MessagePage(){
    Column (
        modifier= Modifier.padding(horizontal = 15.dp, )
    ){
        val activity = LocalContext.current as? ComponentActivity
        val username: String = activity?.intent?.getStringExtra("username") ?: "DefaultUsername"
        val bio: String = activity?.intent?.getStringExtra("bio") ?: "DefaultBio"
        val profilePic: Uri = Uri.parse(activity?.intent?.getStringExtra("profileUri") ?: "")

        User(username = username, bio = bio, profilePic = profilePic)
        MessageFrag()
    }
}

@Composable
fun User(username: String, bio: String, profilePic: Uri){
    val addFriend= painterResource(id = R.drawable.addfriendicon)
    val searchIcon= painterResource(id = R.drawable.searchicon)
    val colorInt = 0x333333 shl 8 or 0x33

    var search by remember {
        mutableStateOf("")
    }

    Column() {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row{
            val imagePainter: ImagePainter = rememberImagePainter(data = profilePic)
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .size(31.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .scale(1.5f)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = username,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF696969),
                ),
            )
        }

        Row() {
            Image(
                painter = addFriend,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Add Friends",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF2F9ECE),
                ),
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text ="Messages",
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight(500),
            color = Color(0xFF000000),
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0x33333333),
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .height(48.dp)
                .width(444.dp)
        ) {

               Image(
                   painter = searchIcon,
                   contentDescription = "Search",
                   modifier = Modifier
                       .size(35.dp)
                       .padding(top = 15.dp),
               )
            Spacer(modifier = Modifier.width(6.dp))
               BasicTextField(
                   value = search,
                   onValueChange = { search = it },
                   textStyle = LocalTextStyle.current.copy(color = Color.Black),
                   singleLine = true,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(top = 14.dp, start = 27.dp)
               )

               if (search.isEmpty()) {
                   Text(
                       text = "Search",
                       color = Color.Gray,
                       modifier = Modifier.padding(start = 27.dp, top = 14.dp)
                   )
               }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                Text(
                    text = "Requests(10)",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2F9ECE),
                    ),
                )

                Text(
                    text = "Archives(1)",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2F9ECE),
                    ),
                )
        }
    }
}

@Composable
fun MessageFrag(){
    val phoneLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.phonetext
        )
    )

    val phoneProgress by animateLottieCompositionAsState(
        composition = phoneLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Hello")
        LottieAnimation(
            composition = phoneLottieComposition,
            progress = phoneProgress,
            modifier= Modifier
                .width(300.dp)
                .height(300.dp)
        )

    }

}

