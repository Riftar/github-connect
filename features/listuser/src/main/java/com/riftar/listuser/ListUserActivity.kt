package com.riftar.listuser

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.StickyNote2
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.riftar.common.ui.theme.GithubConnectTheme

class ListUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubConnectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListUserScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ListUserScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar()
        ListOutlet()
    }
}

@Composable
fun SearchBar() {
    var query by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            if (query.length > 3) {
                //TODO search
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show()
            }
        },
        label = { },
        placeholder = { Text(text = "Input 3 character to search", fontSize = 14.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "search icon") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(Icons.Filled.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .clickable {
                            query = ""
                        }
                        .size(16.dp)
                )
            }
        },
        singleLine = true
    )
}

@Composable
fun ListOutlet() {
    LazyColumn() {
        items(10) { index: Int ->
            when (index) {
                8 ->  LoadingItem()
                9 -> ErrorItem(message = "Unknown Error") {

                }
                else -> UserItem()
            }
        }

    }
}

@Composable
fun UserItem() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_test),
                contentDescription = "user image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "UserName", style = MaterialTheme.typography.titleMedium)
                Text(text = "details details", style = MaterialTheme.typography.bodySmall)
            }

            IconButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Icon(Icons.AutoMirrored.Outlined.StickyNote2, contentDescription = "note icon")
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
        Text(text = "Please wait...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ErrorItem(message: String, onRetryClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            Icons.Default.Report,
            contentDescription = "error icon",
            tint = MaterialTheme.colorScheme.error
        )
        Text(text = message, style = MaterialTheme.typography.bodyMedium)
        Button(
            onClick = { onRetryClick },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(text = "Retry", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showSystemUi = true, name = "lightMode")
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "darkMode")
@Composable
fun GreetingPreview() {
    GithubConnectTheme {
        ListUserScreen()
    }
}