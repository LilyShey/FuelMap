package com.hpk.fuelmap.features.main.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hpk.domain.models.fuel.FuelType
import com.hpk.fuelmap.databinding.ListItemFuelTypesBinding

class FuelTypesAdapter : RecyclerView.Adapter<FuelTypesAdapter.FuelTypesViewHolder>() {
    var fuelTypesList: List<FuelType>? = null
        get() = field
        set(value) {
            field = value
        }

    inner class FuelTypesViewHolder(val binding: ListItemFuelTypesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: FuelType) {
            binding.fuelNameTV.text = item.name
            binding.fuelSwitch.isChecked = item.isChecked ?: false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelTypesViewHolder {
        val binding = ListItemFuelTypesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return FuelTypesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FuelTypesViewHolder, position: Int) {
        fuelTypesList?.let {
            holder.onBind(it[position])
        }
    }

    override fun getItemCount() = fuelTypesList?.size ?: 0
}