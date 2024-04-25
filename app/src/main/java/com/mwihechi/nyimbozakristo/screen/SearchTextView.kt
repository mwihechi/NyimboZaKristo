package com.mwihechi.nyimbozakristo.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mwihechi.nyimbozakristo.viewModel.SongsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewText(viewModel: SongsViewModel) {
    var query by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged ->
                query = onQueryChanged
                if (onQueryChanged.isNotEmpty()) {
                    viewModel.searchQuery.value = query
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = {
                        query = ""
                        viewModel.searchQuery.value = query
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "Clear icon"
                        )
                    }
                }
            },
            maxLines = 1,
            //colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            placeholder = { Text(text = "tafuta kwa maneno au namba") },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
