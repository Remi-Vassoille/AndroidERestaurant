package fr.isen.vassoille.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.vassoille.androiderestaurant.ui.theme.AndroidERestaurantTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Menu()
                }
            }
        }
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Text(
            text = "Entrées",
            modifier = modifier.clickable {
                val toast = Toast.makeText(context, "Selected Entrées", Toast.LENGTH_SHORT)
                toast.show()
            }
        )
        Text(text = "Plats",
            modifier = modifier.clickable {
                val toast = Toast.makeText(context, "Selected Plats", Toast.LENGTH_SHORT)
                toast.show()
            }
        )
        Text(text = "Desserts",
            modifier = modifier.clickable {
                val toast = Toast.makeText(context, "Selected Desserts", Toast.LENGTH_SHORT)
                toast.show()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        Menu()
    }
}