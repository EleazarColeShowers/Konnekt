@file:Suppress("NAME_SHADOWING", "UNCHECKED_CAST")

package com.example.instachatcompose.ui.activities.konnekt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.instachatcompose.R
import com.example.instachatcompose.ui.activities.mainpage.MessageActivity
import com.example.instachatcompose.ui.theme.InstaChatComposeTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Konnekt: ComponentActivity() {
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
                        AddFriendsPage()
                    }
                }
            }
        }
    }
}

@Composable
fun AddFriendsPage()  {
    Box(modifier = Modifier.fillMaxSize()) {
        val activity = LocalContext.current as? ComponentActivity
        val username: String = activity?.intent?.getStringExtra("username") ?: "DefaultUsername"
        val bio: String = activity?.intent?.getStringExtra("bio") ?: "DefaultBio"
        val profilePic: Uri = Uri.parse(activity?.intent?.getStringExtra("profileUri") ?: "")

        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {

            UserAddFriends(username = username, profilePic = profilePic)
//            MessageFrag(username = username)

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Aligns the Box at the bottom
                .fillMaxWidth() // The BottomAppBar spans the full width
                .height(80.dp)  // Desired height
        ) {
            BottomAppBarKonnekt(username = username, profilePic = profilePic) // Place your bottom bar here
        }
    }
}



@Composable
fun UserAddFriends(username: String, profilePic: Uri) {
    val settingsIcon = painterResource(id = R.drawable.settings)
    val searchIcon = painterResource(id = R.drawable.searchicon)

    var searchResults by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    val database = FirebaseDatabase.getInstance().getReference("users")
    var search by remember { mutableStateOf("") }
    var searchPerformed by remember { mutableStateOf(false) }
    var allUsers by remember { mutableStateOf(listOf<Map<String, Any>>()) }


    fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            searchResults = allUsers.filter {
                val username = it["username"] as? String ?: ""
                username.contains(query, ignoreCase = true)
            }
            searchPerformed = true
        } else {
            searchResults = listOf()
            searchPerformed = false
        }
    }
    fun loadAllUsers() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.children.mapNotNull { snapshot ->
                    val userMap = snapshot.value as? Map<String, Any>
                    userMap?.let {
                        mapOf(
                            "username" to (it["username"] as? String ?: ""),
                            "email" to (it["email"] as? String ?: ""),
                            "profileImageUri" to (it["profileImageUri"] as? String ?: "")
                        )
                    }
                }
                allUsers = users
                performSearch(search) // Filter results initially based on the current search
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseSearch", "Error fetching data", databaseError.toException())
            }
        })
    }


    loadAllUsers()

    Column {
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                val imagePainter: AsyncImagePainter = rememberAsyncImagePainter(model = profilePic)
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

            Row {
                Image(
                    painter = settingsIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Settings",
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
            text = "Add Friends",
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = searchIcon,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 8.dp)
                )

                BasicTextField(
                    value = search,
                    onValueChange = {
                        search = it
                        performSearch(it)
                    },
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

                if (search.isEmpty()) {
                    Text(
                        text = "Find Friends",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        LazyColumn {
            if (!searchPerformed) {
                // Show nothing before search is performed
            } else if (searchResults.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No results found")
                    }
                }
            } else {
                items(searchResults) { result ->
                    val username = result["username"] as? String ?: ""
                    val email = result["email"] as? String ?: ""
                    val profileImageUri = result["profileImageUri"] as? String ?: ""

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                // Handle click (e.g., open profile, send friend request)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(Modifier.width(160.dp)) {


                            val painter = rememberAsyncImagePainter(model = profileImageUri)
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.background)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {
                                Text(text = username, fontWeight = FontWeight.Bold)
                                Text(text = email, color = Color.Gray)
                            }
                        }
                        val addFriend= painterResource(id = R.drawable.addfriends)
                        Row(
                            modifier = Modifier.width(110.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF2F9ECE), // You can change the color as needed
                                    shape = RoundedCornerShape(12.dp),
                                    // You can change the shape as needed
                                )
                                .padding(8.dp) // Optional padding inside the border
                        ){
                            Image(
                                painter = addFriend,
                                contentDescription = null,
                                modifier= Modifier.size(16.dp)
                                )

                            Text(
                                text = "Add Account",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF2F9ECE),
                                ),
                            )

                        }

                    }
                }
            }
        }
    }
}


enum class BottomAppBarItem {
    Messages,
    Calls,
    AddFriends
}

@Composable
fun BottomAppBarKonnekt(username: String,profilePic: Uri) {
    var activeItem by remember { mutableStateOf(BottomAppBarItem.AddFriends) }
    val context= LocalContext.current


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // Align icons and text vertically
    ) {
        BottomAppBarItemKonnekt(
            label = "Messages",
            isActive = activeItem == BottomAppBarItem.Messages,
            activeIcon = R.drawable.bottombar_activemessagespage,
            passiveIcon = R.drawable.bottombar_passivemessagespage,
            onClick = {
                activeItem = BottomAppBarItem.Messages
                val intent = Intent(context, MessageActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("profileUri", profilePic.toString())
                context.startActivity(intent)
            }
        )

        BottomAppBarItemKonnekt(
            label = "Call Logs",
            isActive = activeItem == BottomAppBarItem.Calls,
            activeIcon = R.drawable.bottombar_activecallspage,
            passiveIcon = R.drawable.bottombar_passivecallspage,
            onClick = { activeItem = BottomAppBarItem.Calls }
        )

        BottomAppBarItemKonnekt(
            label = "Konnekt",
            isActive = activeItem == BottomAppBarItem.AddFriends,
            activeIcon = R.drawable.bottombar_activeaddfriendspage,
            passiveIcon = R.drawable.bottombar_passiveaddfriendspage,
            onClick = {
                activeItem = BottomAppBarItem.AddFriends
                val intent = Intent(context, Konnekt::class.java)
                intent.putExtra("username", username)
                intent.putExtra("profileUri", profilePic.toString())
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun BottomAppBarItemKonnekt(
    label: String,
    isActive: Boolean,
    activeIcon: Int,
    passiveIcon: Int,
    onClick: () -> Unit
) {
    Log.d("BottomAppBarItem", "Rendering item: $label, isActive: $isActive")

    Column(
        modifier = Modifier
            .width(68.dp)
            .height(52.dp)
            .clickable(onClick = onClick),  // Make the item clickable
        horizontalAlignment = Alignment.CenterHorizontally // Align content in the center
    ) {
        Image(
            painter = painterResource(id = if (isActive) activeIcon else passiveIcon),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isActive) Color(0xFF2F9ECE) else Color(0xFF696969) // Change text color based on active/passive state
        )
    }
}
