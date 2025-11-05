package com.example.lab_week_09

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB_WEEK_09Theme {
                //Surface sebagai pembungkus/container aplikasi yang berfungsi
                // menampilkan isi
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               ) {
                   val list = listOf("Tanu", "Tina", "Tono")
                   Home(list)
               }
            }
        }
    }
}

//Composable mirip Komponen di reactjs
@Composable
fun Home(
    //Kita kasih parameter namanya items yang berisi List String
    items: List<String>,
) {
    LazyColumn {
        item {
            Column(
                // Mengedit supaya colum ini terdapat padding di seluruh sisi dan memenuhi container parentnya
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                // Mengatur supaya colum align horizontal
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(
                    id = R.string.enter_item)
                )

                // TextField buat display text input field
                TextField(
                    value = " ",
                    // Setting supaya input ini hanya pakai keyboard number doang
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    //Ketika value field nya berubah/diisi lakukan ini
                    onValueChange = {
                    }
                )

                Button(onClick = { }) {
                    // Atur text di buttonnya
                    Text(text = stringResource(
                        id = R.string.button_click)
                    )
                }
            }
        }

        // Kita menggunakan item untuk display list itemnya di dalam LazyColumn (Seperti recycler view)
        items(items) { item ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Home(listOf("Tanu", "Tina", "Tono"))
}