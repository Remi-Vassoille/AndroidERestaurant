package fr.isen.vassoille.androiderestaurant


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import com.google.gson.reflect.TypeToken
import coil.compose.rememberImagePainter
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.GsonBuilder
import fr.isen.vassoille.androiderestaurant.ui.theme.DetailActivity
import java.io.Serializable


class SelectedCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category = intent.getStringExtra("category") ?: ""
        val dishType = when (category) {
            "STARTER" -> DishType.STARTER
            "MAIN" -> DishType.MAIN
            "DESSERT" -> DishType.DESSERT
            else -> DishType.STARTER
        }

        setContent {
            MenuView(type = dishType)
        }
        Log.d("lifeCycle", "Menu Activity - OnCreate")
    }

    private fun fetchMenuItems(selectedCategory: String, listener: (String) -> Unit, errorListener: () -> Unit) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val idShop = "id_shop"

        val params = JSONObject().apply {
            put("id_shop", idShop)
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, params,
            { response ->
                listener(response.toString())
            },
            {
                errorListener()
            })

        VolleyRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }



    private fun getDishesByCategory(selectedCategory: String, listener: (List<Dish>) -> Unit, errorListener: () -> Unit) {
        fetchMenuItems(selectedCategory,
            { jsonString ->
                val filteredDishes = parseJsonAndFilterCategory(jsonString, selectedCategory)
                listener(filteredDishes)
            },
            {
                errorListener()
            }
        )
    }

    private fun parseJsonAndFilterCategory(jsonString: String, selectedCategory: String): List<Dish> {
        val gson = Gson()
        val menuResultType = object : TypeToken<MenuResult>() {}.type
        val menuResult: MenuResult = gson.fromJson(jsonString, menuResultType)

        val filteredDishes = menuResult.data
            .find { category -> category.nameFr == selectedCategory }
            ?.items ?: emptyList()

        return filteredDishes
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MenuView(type: DishType) {
        val category = remember { mutableStateOf<Category?>(null) }
        val context = LocalContext.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar({
                Text(
                    text = type.title(),
                    onTextLayout = null
                )

            })
            LazyColumn() {
                category.value?.let {
                    items(it.items) {
                        DishRow(it)
                    }
                }
            }
        }
        PostData(type, category)
    }


    @Composable
    fun PostData(type: DishType, category: MutableState<Category?>) {
        val currentCategory = type.title()
        val context = LocalContext.current
        val queue = Volley.newRequestQueue(context)

        val params = JSONObject()
        params.put("id_shop", "1")

        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/menu",
            params,
            { response ->
                Log.d("request", response.toString(2))
                Log.d("Request", "Response: $response")
                val result = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                val filteredResult = result.data.firstOrNull { categroy -> categroy.nameFr == currentCategory }
                category.value = filteredResult
                Log.d("SelectedCategoryActivity", "Filtered dishes: $filteredResult")
            },
            {
                Log.e("request", it.toString())
            }
        )

        queue.add(request)
    }


    @Composable
    fun DishRow(dish: Dish) {
        val context = LocalContext.current
        Card(
            border = BorderStroke(1.dp, Color.Black),

            ) {
            Row() {
                Image(
                    painter = rememberImagePainter(
                        data = dish.images.first(),
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Column {
                    Text(dish.nameFr)
                    Text("${dish.prices.first().price} €")
                    Button(
                        onClick = {
                            val dish = intent.getSerializableExtra(DetailActivity.DISH_EXTRA_KEY) as? Dish
                            intent.putExtra(DetailActivity.DISH_EXTRA_KEY, dish as Serializable)
                            context.startActivity(intent)
                        },
                    ) {
                        Text("Voir les détails")
                    }
                }
            }

        }
    }
}

