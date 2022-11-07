package com.hpk.fuelmap.features.main.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hpk.domain.models.fuel.FuelType
import com.hpk.domain.usecases.GetAllFuelTypesUseCase
import com.hpk.domain.usecases.SaveFuelTypeStateUseCase
import com.hpk.domain.usecases.base.ResultCallbacks
import com.hpk.fuelmap.common.ui.base.BaseViewModel

class MainSettingsVM(
    private val getAllFuelTypesUseCase: GetAllFuelTypesUseCase,
    private val saveFuelTypeStateUseCase: SaveFuelTypeStateUseCase,
) : BaseViewModel() {
    val fuelTypes = MutableLiveData<List<FuelType>?>()

    fun getAllFuelsTypes() {
        getAllFuelTypesUseCase(
            uiDispatcher = viewModelScope,
            result = ResultCallbacks(
                onSuccess = {
                    fuelTypes.value = it
                },
                onError = {
                    fuelTypes.value = null
                    timber.log.Timber.e(it)
                    errorMessage.value = it.apiError?.toString()
                },
                onConnectionError = {
                    timber.log.Timber.e(it)
                    onConnectionError { getAllFuelsTypes() }
                },
                onUnexpectedError = {
                    fuelTypes.value = null
                    timber.log.Timber.e(it)
                    errorMessage.value = it.localizedMessage
                },
                onLoading = {
                    isLoading.value = it
                }
            )
        )
    }

    fun saveFuelTypeState(fuelType: FuelType, isChecked: Boolean) {
        saveFuelTypeStateUseCase(
            uiDispatcher = viewModelScope,
            params = SaveFuelTypeStateUseCase.Params(fuelType = fuelType, isChecked = isChecked),
            result = ResultCallbacks(
                onSuccess = {
                    getAllFuelsTypes()
                },
                onError = {
                    timber.log.Timber.e(it)
                    errorMessage.value = it.apiError?.toString()
                },
                onConnectionError = {
                    timber.log.Timber.e(it)
                    onConnectionError { getAllFuelsTypes() }
                },
                onUnexpectedError = {
                    timber.log.Timber.e(it)
                    errorMessage.value = it.localizedMessage
                },
                onLoading = {
                    isLoading.value = it
                }
            )
        )
    }
}