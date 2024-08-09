package com.riftar.listuser

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.riftar.common.constant.NavigationConstant.USERNAME_INTENT
import com.riftar.common.constant.NavigationConstant.USER_DETAIL_ACTIVITY
import com.riftar.common.constant.NavigationConstant.USER_ID_INTENT
import com.riftar.common.ui.theme.GithubConnectTheme
import com.riftar.domain.listuser.model.User
import com.valentinilk.shimmer.shimmer
import org.koin.androidx.compose.koinViewModel

class ListUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubConnectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(color = Color.White) {
                        ListUserScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListUserScreen(modifier: Modifier = Modifier) {
    val viewModel: ListUserViewModel = koinViewModel()
    val users = viewModel.getListUser().collectAsLazyPagingItems()

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(onSearchQueryChange = { viewModel.setSearchQuery(it) })
        ListUser(users)
    }
}

@Composable
fun SearchBar(onSearchQueryChange: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = query,
        onValueChange = { newQuery ->
            query = newQuery
            onSearchQueryChange.invoke(newQuery)
        },
        label = { },
        placeholder = { Text(text = "Search by username", fontSize = 16.sp) },
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
                            onSearchQueryChange.invoke("")
                        }
                        .size(16.dp)
                )
            }
        },
        singleLine = true
    )
}

@Composable
fun ListUser(users: LazyPagingItems<User>) {
    LazyColumn {
        items(
            count = users.itemCount,
            key = { index -> users[index]?.id ?: index }
        ) { index ->
            val outlet = users[index]
            outlet?.let {
                UserItem(it)
            }
        }
        users.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onRetryClick = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onRetryClick = { retry() }
                        )
                    }
                }

                loadState.refresh.endOfPaginationReached && users.itemCount == 0 -> {
                    item { EmptyView() }
                }

                loadState.append.endOfPaginationReached && users.itemCount == 0 -> {
                    item { EmptyView() }
                }
            }
        }
    }
}

@Composable
fun EmptyView() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .height(200.dp)
    ) {
        Text(
            text = "No user found",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun UserItem(user: User) {
    val context = LocalContext.current
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
            ), onClick = { navigateToUserDetail(context, user.id, user.userName) }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = user.avatarUrl,
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
                Text(text = user.userName, style = MaterialTheme.typography.titleMedium)
                Text(text = user.htmlUrl, style = MaterialTheme.typography.bodySmall)
            }

            if (user.hasNotes) {
                Icon(Icons.AutoMirrored.Outlined.StickyNote2, contentDescription = "note icon")
            }
        }
    }
}

fun navigateToUserDetail(context: Context, userId: Int, userName: String) {
    val intent = Intent(context, Class.forName(USER_DETAIL_ACTIVITY))
    intent.putExtra(USER_ID_INTENT, userId)
    intent.putExtra(USERNAME_INTENT, userName)
    context.startActivity(intent)
}

@Composable
fun LoadingItem() {
    repeat(5) {
        LoadingShimmer()
    }
}

@Composable
fun LoadingShimmer() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .shimmer()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(40.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .width(100.dp)
                        .height(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .height(14.dp)
                )
            }
        }
    }
}

@Composable
fun ErrorItem(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Report,
            contentDescription = "error icon",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Button(
            onClick = { onRetryClick.invoke() },
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