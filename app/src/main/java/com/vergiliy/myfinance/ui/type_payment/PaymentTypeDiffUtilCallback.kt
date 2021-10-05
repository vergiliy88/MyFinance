package com.vergiliy.myfinance.ui.type_payment

import androidx.recyclerview.widget.DiffUtil
import com.vergiliy.myfinance.domain.models.PaymentType

class PaymentTypeDiffUtilCallback(private val oldList :List<PaymentType>,
                                  private val newList: List<PaymentType> ): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: PaymentType = oldList[oldItemPosition]
        val newProduct: PaymentType = newList[newItemPosition]
        return oldProduct.id === newProduct.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: PaymentType = oldList[oldItemPosition]
        val newProduct: PaymentType = newList[newItemPosition]
        return (oldProduct.name.equals(newProduct.name)
                && (oldProduct.sum === newProduct.sum) && (oldProduct.color === newProduct.color))
    }
}