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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                   val navController = rememberNavController()
                   App (
                       navController = navController
                   )
               }
            }
        }
    }
}

//Composable mirip Komponen di reactjs
@Composable
fun Home (
    navigateFromHomeToResult: (String) -> Unit
)
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
        },
        // Lambda function untuk navigate ke ResultContent composable dan pass listData sebagai parameter
        {navigateFromHomeToResult(listData.toList().toString()) }
    )

}

@Composable
// Composable HomeContent berfungsi untuk menampilkan isi dari Home Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
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
                // Tampill text UI elemet
                OnBackgroundTitleText(text = stringResource(
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

                // Panggil PrimaryTextButton UI Element
                PrimaryTextButton(text = stringResource(
                    id = R.string.button_click)
                ) {
                    onButtonClick()
                }

                // Bikin finish button buat pindah ke navigation to result
                PrimaryTextButton(text = stringResource(
                    id = R.string.button_navigate)
                ) {
                    navigateFromHomeToResult()
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
                OnBackgroundItemText(text = item.name)
            }

        }
    }
}

// Composable buat UI element buat nampilin title
@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(text = text, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun TitleText(text: String, color: Color) {
    // TitleLarge buat gedein textnya
    Text(text = text, style = MaterialTheme.typography.titleLarge, color = color)
}

// Composable buat UI elemet nampilin item text
@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(text = text, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun ItemText(text: String, color: Color) {
    Text(text = text, style = MaterialTheme.typography.bodySmall, color = color)
}

// Composable UI element buat nampilin button
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    TextButton(text = text, textColor = Color.White, onClick = onClick)
}

@Composable
fun TextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = textColor)

    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }
}

// Ini root composable dari app
@Composable
fun App(navController : NavHostController) {
    // Pake NavHost buat bikin navigation graph
    // Pass NavController ke parameter dan juga startDestination ke home (Pake home composable)
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            // Pass lambda function untuk navigate ke "resultContent" dan pass list data sebagai parameter
            Home { navController.navigate(
                "resultContent/?listData=$it")
            }
        }

        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") {
                type = NavType.StringType }
            )
        ) {
            // Pass value dari argumen ke ResultContent composable
            ResultContent(
                it.arguments?.getString("listData").orEmpty()
            )
        }
    }
}

@Composable
fun ResultContent(listData: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Panggil UI Element Background item text
        OnBackgroundItemText(text = listData)
    }
}

