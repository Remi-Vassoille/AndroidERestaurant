package fr.isen.vassoille.androiderestaurant
import com.google.gson.annotations.SerializedName
import com.google.gson.Gson
import java.io.Serializable

data class Category(
    @SerializedName("name_fr") val nameFr: String,
    @SerializedName("name_en") val nameEn: String,
    @SerializedName("items") val items: List<Dish>
)

data class Dish(
    @SerializedName("id") val id: String,
    @SerializedName("name_fr") val nameFr: String,
    @SerializedName("name_en") val nameEn: String,
    @SerializedName("id_category") val idCategory: String,
    @SerializedName("categ_name_fr") val categNameFr: String,
    @SerializedName("categ_name_en") val categNameEn: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("ingredients") val ingredients: List<Ingredient>,
    @SerializedName("prices") val prices: List<Price>
) : Serializable

data class Ingredient(
    @SerializedName("id") val id: String,
    @SerializedName("id_shop") val idShop: String,
    @SerializedName("name_fr") val nameFr: String,
    @SerializedName("name_en") val nameEn: String,
    @SerializedName("create_date") val createDate: String,
    @SerializedName("update_date") val updateDate: String,
    @SerializedName("id_pizza") val idPizza: String
)

data class Price(
    @SerializedName("id") val id: String,
    @SerializedName("id_pizza") val idPizza: String,
    @SerializedName("id_size") val idSize: String,
    @SerializedName("price") val price: String,
    @SerializedName("create_date") val createDate: String,
    @SerializedName("update_data") val updateDate: String,
    @SerializedName("size")val size: String
)

data class MenuResult(
    val data: List<Category>
)
