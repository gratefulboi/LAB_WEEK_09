package com.example.lab_week_09

import android.R.attr.onClick
import android.R.id.input
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// Data class student (state)
data class Student(
    var name: String
)

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
                   Home()
               }
            }
        }
    }
}

//Composable mirip Komponen di reactjs
@Composable
fun Home ()
{
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // Mutable state of Student, buat dapetin value dari input field
    var inputField = remember { mutableStateOf(Student("")) }

    HomeContent(
        listData,
        inputField.value,
        // Lambda function untuk update value dari inputField
        { input -> inputField.value = inputField.value.copy(input) },
        // Lambda function untuk menambahkan value inputField ke listData
        {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        }
    )

}

@Composable
// Composable HomeContent berfungsi untuk menampilkan isi dari Home Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
    ) {
    // Pake lazy column untuk display list of item lazily (Mirip recyclerview)
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
                    // Atur valuenya berdasarkan inputfield.name
                    value = inputField.name,
                    // Setting supaya input ini hanya pakai keyboard number doang
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),

                    //Ketika value field nya berubah/diisi lakukan ini (lambda function parameter ke 3)
                    onValueChange = {
                        onInputValueChange(it)
                    }
                )

                Button(onClick = {
                    // Panggil function onButtonClick di lambda function parameter ke 4
                    onButtonClick()
                }) {
                    // Ganti button text dengan string yang di strings.xml
                    Text(text = stringResource(
                        id = R.string.button_click)
                    )
                }
            }
        }

        // Kita menggunakan item untuk display list itemnya di dalam LazyColumn (Seperti recycler view)
        // Kita pass list data sebagai parameter
        items(listData) { item ->
            Column(
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name)
            }

        }
    }
}

