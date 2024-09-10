package com.awesome.foodrunner.android.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awesome.foodrunner.FoodRunnerSharedSDK
import com.awesome.foodrunner.android.R
import com.awesome.foodrunner.android.core.model.CloseOpenStatus
import com.awesome.foodrunner.android.core.model.FilterWithState
import com.awesome.foodrunner.android.core.model.RestaurantMediator
import com.awesome.foodrunner.model.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


enum class LoadingState {
    Idle,
    Loading,
    Done,
    Error
}

data class MainScreenViewData(
    val restaurants: List<Restaurant> = emptyList(),
    val filters: List<FilterWithState> = emptyList(),
    val loadingState: LoadingState = LoadingState.Idle,
    val loadingMessageRes: Int = R.string.label_loading_restaurants,
    val changedOn: Date = Date(),
    val focusedRestaurant: RestaurantMediator? = null
)

class MainScreenViewModel: ViewModel() {
    var viewData by mutableStateOf(MainScreenViewData())
    private set

    private val foodRunnerSdk = FoodRunnerSharedSDK()
    private val filters = mutableListOf<FilterWithState>()
    private val restaurants = mutableListOf<Restaurant>()

    //region API Integration
    fun getAllRestaurants() {
        viewModelScope.launch {
            loadingStarted(messageRes = R.string.label_loading_restaurants)
            kotlin.runCatching {
                fetchRestaurants()
                fetchFilters(restaurants)
                updateVisibleRestaurantsList()
                loadingCompleted()
            }.onFailure {
                loadingFailed()
            }
        }
    }

    private suspend fun fetchRestaurants() {
        restaurants.clear()
        restaurants.addAll(
            foodRunnerSdk.getRestaurants()
        )
    }

    private suspend fun fetchFilters(restaurants: List<Restaurant>) {
        val filterIds = restaurants.flatMap { it.filterIds } .toSet()
        val fetchedFilters = mutableListOf<FilterWithState>()
        loadingStarted(R.string.label_loading_filters)
        filterIds.forEach { filterId ->
            foodRunnerSdk.getFilter(filterId)?.let { resolvedFilter ->
                fetchedFilters.add(
                    FilterWithState.fromFilter(resolvedFilter)
                )
            }
        }

        filters.clear()
        filters.addAll(fetchedFilters)

        updateViewData(
            viewData.copy(
                filters = filters
            )
        )
    }

    private fun updateOpenStatus(restaurantId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                val status = CloseOpenStatus.from(value = foodRunnerSdk.getOpenStatus(restaurantId))
                val restaurant = viewData.focusedRestaurant?.restaurant ?: return@runCatching //There are no focused restaurant and the info about open status is not needed
                if (restaurant.id == restaurantId) {
                    updateViewData(
                        viewData.copy(
                            focusedRestaurant = RestaurantMediator(
                                restaurant = restaurant,
                                openStatus = status
                            )
                        )
                    )
                }
            }
        }
    }
    //endregion

    //region Update UI methods
    private fun updateViewData(viewData: MainScreenViewData) {
        this.viewData = viewData
    }

    private fun loadingCompleted() {
        updateViewData(
            viewData.copy(
                loadingState = LoadingState.Done,
                loadingMessageRes = R.string.empty
            )
        )
    }

    private fun loadingStarted(messageRes: Int? = null) {
        updateViewData(
            viewData.copy(
                loadingState = LoadingState.Loading,
                loadingMessageRes = messageRes ?: R.string.label_loading
            )
        )
    }

    private fun loadingFailed() {
        updateViewData(
            viewData.copy(
                loadingState = LoadingState.Error,
                loadingMessageRes = R.string.lable_cannot_load_the_data
            )
        )
    }

    fun toggleFilter(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                filters
                    .indexOfFirst { it.id == id }
                    .takeIf { it != -1 }
                    ?.let { index ->
                        filters[index] = filters[index].copy(
                            isActive = !filters[index].isActive
                        )
                    }

                println("new filters $filters")

                updateViewData(
                    viewData.copy(
                        filters = filters.toList(),
                        changedOn = Date()
                    )
                )

                updateVisibleRestaurantsList()
            }
        }
    }

    private fun updateVisibleRestaurantsList() {
        val activeFilters = filters
            .toList()
            .filter { it.isActive }
            .map {
                it.id
            }

        val visibleRestaurants = if (activeFilters.isEmpty()) {
            restaurants
        } else {
            restaurants.filter { restaurant ->
                restaurant.filterIds.any { it in activeFilters }
            }
        }
        updateViewData(
            viewData.copy(
                restaurants = visibleRestaurants
            )
        )
    }

    fun showRestaurantDetails(restaurant: Restaurant) {
        updateOpenStatus(restaurant.id)
        updateViewData(
            viewData.copy(
                focusedRestaurant = RestaurantMediator(restaurant = restaurant)
            )
        )
    }

    fun hideRestaurantDetails() {
        updateViewData(
            viewData.copy(
                focusedRestaurant = null
            )
        )
    }
    //endregion

    //region Helpers
    fun tagsAsStringsFor(restaurant: Restaurant): String =
        restaurant
            .filterIds
            .mapNotNull { filterId ->
                filters
                    .firstOrNull { filterId == it.id  }?.name
            }
            .joinToString(" • ")

    //endregion
}