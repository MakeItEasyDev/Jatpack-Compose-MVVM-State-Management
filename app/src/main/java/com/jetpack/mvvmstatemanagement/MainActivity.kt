package com.jetpack.mvvmstatemanagement

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jetpack.mvvmstatemanagement.ui.theme.MVVMStateManagementTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMStateManagementTheme {
                val viewModel = hiltViewModel<StateManagementViewModel>()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val userState by viewModel.userState.collectAsState()
                    when (userState) {
                        is UserState.LoadingState -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is UserState.Success -> {
                            val users = (userState as UserState.Success).users
                            UserListView(users)
                        }
                        is UserState.Error -> {
                            val message = (userState as UserState.Error).errorMessage
                            Log.d("TAG", "onCreate: $message")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserListView(users: List<User>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MVVM State Management",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) {
        LazyColumn {
            items(users) { user ->
                UserItem(user = user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.substring(0, 1),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Column(
            modifier = Modifier.padding(start = 6.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = user.email,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}



















