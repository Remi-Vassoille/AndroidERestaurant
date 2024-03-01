package fr.isen.vassoille.androiderestaurant.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.vassoille.androiderestaurant.Dish
import kotlin.math.max

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Dish
        setContent {
            val context = LocalContext.current
            val count = remember {
                mutableIntStateOf(1)
            }
            val ingredient = dish?.ingredients?.map { it.nameFr }?.joinToString(", ") ?: ""
            val pagerState = rememberPagerState(pageCount = {
                dish?.images?.count() ?: 0
            })
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TopAppBar({
                    Text(dish?.nameFr ?: "")
                })
                Text(ingredient)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(onClick = {
                        count.value = max(1, count.value - 1)
                    }) {
                        Text("-")
                    }
                    Text(count.value.toString())
                    OutlinedButton(onClick = {
                        count.value = count.value + 1
                    }) {
                        Text("+")
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }

    companion object {
        val DISH_EXTRA_KEY = "DISH_EXTRA_KEY"
    }
}